package org.example.submarineopticallinkconfigoptimization.controller;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.example.submarineopticallinkconfigoptimization.dto.LinkRequestDTO;

public class LinkFormController {

    @FXML
    private VBox vBox;

    private Button calculateButton;

    private Form linkForm;

    private LinkRequestDTO linkRequestDTO;



    @FXML
    public void initialize(){

        // Create a LinkRequestDTO Object
        linkRequestDTO = new LinkRequestDTO();


        // Create the Fields of the Form

        Field<StringField> field_1 = Field.ofStringType(linkRequestDTO.getLinkName())
                .label("Link name:");

        Field<StringField> field_2 = Field.ofStringType(linkRequestDTO.getLinkDescription())
                .label("Link description:");

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

        Field<DoubleField> field_9 = Field.ofDoubleType(linkRequestDTO.getMaxAmplifierOutputPower())
                .label("Maximum output power of the amplifiers (dBm):");


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
                        field_9
                )
        ).title("New Link Form");


        calculateButton =  new Button("Calculate");
        calculateButton.setOnAction(e->performCalculations());

        vBox.getChildren().addAll(new FormRenderer(linkForm),calculateButton);


    }


    @FXML
    protected void performCalculations(){

        linkForm.persist();
        System.out.println(linkRequestDTO.getLinkName().get());

    }

}
