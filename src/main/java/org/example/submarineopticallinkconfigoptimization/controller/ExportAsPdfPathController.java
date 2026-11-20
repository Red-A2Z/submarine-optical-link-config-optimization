package org.example.submarineopticallinkconfigoptimization.controller;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.StringField;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.submarineopticallinkconfigoptimization.dto.LinkResponseDTO;
import org.example.submarineopticallinkconfigoptimization.service.LinkService;
import org.example.submarineopticallinkconfigoptimization.validators.StringInputValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExportAsPdfPathController {


    @FXML
    private VBox vBox;

    private Form exportAsPdfForm;


    private final StringProperty pathString = new SimpleStringProperty("");
    private final LinkService linkService = new LinkService();

    private final LinkResponseDTO selectedLink;
    private final Stage saveAsPDFStage;

    public ExportAsPdfPathController(LinkResponseDTO selectedLink, Stage saveAsPDFStage){
        this.selectedLink=selectedLink;
        this.saveAsPDFStage=saveAsPDFStage;
    }


    public void initialize(){

        Field<StringField> linkNameField = Field.ofStringType(pathString)
                .label("Directory path:")
                .validate(new StringInputValidator(100,"Max 100 characters, with at least one non-whitespace character"));

        exportAsPdfForm = Form.of(Group.of(linkNameField));

        Button updateButton = new Button("Export As PDF");
        updateButton.setOnAction(event -> doesPathExist());


        vBox.getChildren().addAll(new FormRenderer(exportAsPdfForm),updateButton);


    }


    private void doesPathExist(){

        exportAsPdfForm.persist();
        Path folder = Paths.get(pathString.get());
        if(!Files.exists(folder)){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Problem");
            alert.setContentText("Couldn't find the provided path");
            alert.showAndWait();

        } else if (!Files.isDirectory(folder)) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Problem");
            alert.setContentText("The provided path does not correspond to a folder");
            alert.showAndWait();

        } else {

            Path file = folder.resolve(selectedLink.getLinkName().get()+"-report.pdf");
            saveAsPDF(file);

        }


    }



    private void saveAsPDF(Path file){

        try{

            linkService.saveAsPDF(selectedLink,file);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Success");
            alert.setContentText("Report has been exported to: "+file);
            alert.showAndWait();

            saveAsPDFStage.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Error");
            alert.setContentText("Error while attempting to perform the operation 'export as PDF'");
            alert.showAndWait();
        }
    }







}
