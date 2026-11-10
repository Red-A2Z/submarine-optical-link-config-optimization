package org.example.submarineopticallinkconfigoptimization.controller;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.model.validators.CustomValidator;
import com.dlsc.formsfx.model.validators.RegexValidator;
import com.dlsc.formsfx.model.validators.StringLengthValidator;
import com.dlsc.formsfx.view.renderer.FormRenderer;
// You must have a license before using Gurobi
//// import com.gurobi.gurobi.GRBException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.submarineopticallinkconfigoptimization.Application;
import org.example.submarineopticallinkconfigoptimization.dto.LinkRequestDTO;
import org.example.submarineopticallinkconfigoptimization.dto.LinkResponseDTO;
import org.example.submarineopticallinkconfigoptimization.exception.RepositoryException;
import org.example.submarineopticallinkconfigoptimization.service.LinkService;
import org.example.submarineopticallinkconfigoptimization.service.OjAlgoCalculationsService;
import org.example.submarineopticallinkconfigoptimization.validators.StringInputValidator;

import java.io.IOException;
import java.util.List;

public class LinkFormController {

    @FXML
    private VBox vBox;

    private Button calculateButton;
    private Form linkForm;

    private LinkRequestDTO linkRequestDTO;

    private final MainController mainController = new MainController();
    private final LinkService linkService = new LinkService();
    private final OjAlgoCalculationsService ojAlgoCalculationsService= new OjAlgoCalculationsService();
    // You must have a license before using Gurobi
    //// private final GurobiCalculationsService gurobiCalculationsService = new GurobiCalculationsService();

    private Stage linkFormStage;
    private ObservableList<LinkResponseDTO> links;


    public LinkFormController(Stage linkFormStage, ObservableList<LinkResponseDTO> links){
        this.linkFormStage = linkFormStage;
        this.links = links;
    }



    @FXML
    public void initialize(){

        // Create a LinkRequestDTO Object
        linkRequestDTO = new LinkRequestDTO();


        // Create the Fields of the Form

        Field<StringField> field_1 = Field.ofStringType(linkRequestDTO.getLinkName())
                .label("Link name:")
                .validate(new StringInputValidator(100,"Max 100 characters, with at least one non-whitespace character"));

        Field<StringField> field_2 = Field.ofStringType(linkRequestDTO.getLinkDescription())
                .label("Link description:")
                .validate(new StringInputValidator(500,"Max 500 characters, with at least one non-whitespace character"));

        Field<DoubleField> field_3 = Field.ofDoubleType(linkRequestDTO.getLinkLength())
                .label("Length of the link (km):");

        Field<DoubleField> field_4 = Field.ofDoubleType(linkRequestDTO.getLinkAttenuation())
                .label("Optical fiber attenuation (dB/km):");

        Field<DoubleField> field_5 = Field.ofDoubleType(linkRequestDTO.getTransmitterOutputPower())
                .label("Transmitter output power (dBm):");

        Field<DoubleField> field_6 = Field.ofDoubleType(linkRequestDTO.getMinReceivedPower())
                .label("Minimum required received optical power (dBm):");

        Field<DoubleField> field_7 = Field.ofDoubleType(linkRequestDTO.getMinAmplifierGain())
                .label("Minimum permissible gain per amplifier (dB):");

        Field<DoubleField> field_8 = Field.ofDoubleType(linkRequestDTO.getMaxAmplifierGain())
                .label("Maximum permissible gain per amplifier (dB):");

        Field<DoubleField> field_9 = Field.ofDoubleType(linkRequestDTO.getMinAmplifierInputPower())
                .label("Minimum input power for each amplifier (dBm):");

        Field<DoubleField> field_10 = Field.ofDoubleType(linkRequestDTO.getMaxAmplifierOutputPower())
                .label("Maximum output power of each amplifiers (dBm):");

        Field<IntegerField> field_11 = Field.ofIntegerType(linkRequestDTO.getMaxNumberOfAmplifiers())
                .label("Maximum number of amplifiers:");


        // Create the Form
        linkForm = Form.of(
                Group.of(
                        field_1,
                        field_2,
                        field_3,
                        field_4,
                        field_5,
                        field_6,
                        field_7,
                        field_8,
                        field_9,
                        field_10,
                        field_11
                )
        ).title("New Link Form");


        calculateButton =  new Button("Calculate");
        calculateButton.setOnAction(e->performCalculations());

        vBox.getChildren().addAll(new FormRenderer(linkForm),calculateButton);


    }



    private void performCalculations(){

        if(linkForm.isValid()){

            linkForm.persist();

            // call verification function from service
            List<String> listOfMessages= linkService.verifyFormInput(linkRequestDTO);

            // if input is invalid :  alert
            if(!listOfMessages.isEmpty()){

                StringBuilder alertContent = new StringBuilder();
                for (String message : listOfMessages){
                    alertContent.append(message).append("\n").append("\n");
                }

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Invalid input");
                alert.setContentText(alertContent.toString());
                alert.showAndWait();

            }else{

                System.out.println("Input is valid");

                // You must have a license before using Gurobi
                // If you want to use Gurobi, wrap the code in a try-catch block to handle the GRBException
                //// LinkResponseDTO linkResponseDTO = gurobiCalculationsService.calculate(linkRequestDTO);


                try{

                    LinkResponseDTO linkResponseDTO = ojAlgoCalculationsService.calculate(linkRequestDTO);

                    if(linkResponseDTO == null){ // No solution found

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("No solution found");
                        alert.setContentText("We couldn't find a solution based on the input you provided.");
                        alert.showAndWait();

                    }else{//Positive case


                        System.out.println("d = "+linkResponseDTO.getDistanceBetweenAmplifiers());
                        System.out.println("n = "+linkResponseDTO.getNumberOfAmplifiers());

                        try{
                            openLinkDetails(linkResponseDTO);
                        }catch (IOException e){
                            System.out.println("Error while trying to show Link Details");
                        }

                        links.add(linkResponseDTO);
                        linkFormStage.close();


                    }

                }catch (RepositoryException e) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Repository error");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();

                }

            }

        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Invalid input");
            alert.setContentText("Input data is invalid");
            alert.showAndWait();
        }


    }




    void openLinkDetails(LinkResponseDTO linkResponseDTO) throws IOException {

        Stage linkDetailsStage =  new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("link-details-view.fxml"));
        fxmlLoader.setController(new LinkDetailsController(linkResponseDTO));

        Scene linkFormScene =  new Scene(fxmlLoader.load());

        linkDetailsStage.setScene(linkFormScene);
        linkDetailsStage.show();


    }

}
