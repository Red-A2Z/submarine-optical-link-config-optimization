package org.example.submarineopticallinkconfigoptimization.controller;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.StringField;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.submarineopticallinkconfigoptimization.dto.LinkResponseDTO;
import org.example.submarineopticallinkconfigoptimization.dto.LinkUpdateRequestDTO;
import org.example.submarineopticallinkconfigoptimization.exception.RepositoryException;
import org.example.submarineopticallinkconfigoptimization.service.LinkService;
import org.example.submarineopticallinkconfigoptimization.validators.StringInputValidator;

public class UpdateLinkNameDescriptionController {


    @FXML
    private VBox vBox;

    private Form updateForm;

    private final LinkUpdateRequestDTO linkUpdateRequestDTO = new LinkUpdateRequestDTO();
    private final LinkService linkService = new LinkService();

    private final LinkResponseDTO selectedLink;
    private final Stage updateStage;

    public UpdateLinkNameDescriptionController(LinkResponseDTO selectedLink, Stage updateStage){
        this.selectedLink=selectedLink;
        this.updateStage=updateStage;

        linkUpdateRequestDTO.setId(selectedLink.getId());
        linkUpdateRequestDTO.setLinkName(new SimpleStringProperty(selectedLink.getLinkName().get()));
        linkUpdateRequestDTO.setLinkDescription(new SimpleStringProperty(selectedLink.getLinkDescription().get()));

    }


    public void initialize(){

        Field<StringField> linkNameField = Field.ofStringType(linkUpdateRequestDTO.getLinkName())
                .label("Link name:")
                .validate(new StringInputValidator(100,"Max 100 characters, with at least one non-whitespace character"));
        Field<StringField> linkDescriptionField = Field.ofStringType(linkUpdateRequestDTO.getLinkDescription())
                .label("Link description:")
                .validate(new StringInputValidator(500,"Max 500 characters, with at least one non-whitespace character"));


        updateForm = Form.of(Group.of(linkNameField,linkDescriptionField));

        Button updateButton = new Button("Update");
        updateButton.setFont(new Font(20));
        updateButton.setOnAction(event -> update());


        vBox.getChildren().addAll(new FormRenderer(updateForm),updateButton);


    }





    private void update(){

        if(updateForm.isValid()){

            String oldName = linkUpdateRequestDTO.getLinkName().get();
            String oldDescription = linkUpdateRequestDTO.getLinkDescription().get();

            updateForm.persist();

            String newName = linkUpdateRequestDTO.getLinkName().get();
            String newDescription = linkUpdateRequestDTO.getLinkDescription().get();

            boolean shouldUpdateName = false;
            boolean shouldUpdateDescription = false;

            if(!newName.equals(oldName)){
                shouldUpdateName=true;
            }
            if(!newDescription.equals(oldDescription)){
                shouldUpdateDescription=true;
            }


            try{
                linkService.updateLinkNameDescription(linkUpdateRequestDTO,shouldUpdateName,shouldUpdateDescription);


                selectedLink.getLinkName().set(newName);
                selectedLink.getLinkDescription().set(newDescription);

                updateStage.close();

            }catch(RepositoryException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Repository error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }


        }else{

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Invalid input");
            alert.setContentText("Input data is invalid");
            alert.showAndWait();

        }



    }



}
