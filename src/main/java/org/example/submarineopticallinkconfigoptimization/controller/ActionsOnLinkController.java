package org.example.submarineopticallinkconfigoptimization.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.example.submarineopticallinkconfigoptimization.Application;
import org.example.submarineopticallinkconfigoptimization.dto.LinkResponseDTO;

import java.io.IOException;

public class ActionsOnLinkController {

    @FXML
    private Label linkNameLabel;
    @FXML
    private TextArea linkDescriptionTextArea;

    @FXML
    private Button updateButton;


    private final LinkResponseDTO selectedLink;

    public ActionsOnLinkController(LinkResponseDTO selectedLink) {
        this.selectedLink = selectedLink;
    }

    public void initialize(){

        linkNameLabel.textProperty().bind(selectedLink.getLinkName());
        linkDescriptionTextArea.textProperty().bind(selectedLink.getLinkDescription());

        updateButton.setOnAction(event -> updateButtonClicked());

    }



    private void updateButtonClicked(){

        try{

            Stage updateStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("update-link-name-description-view.fxml"));
            fxmlLoader.setController(new UpdateLinkNameDescriptionController(selectedLink,updateStage));
            Scene updateScene = new Scene(fxmlLoader.load());
            updateStage.setScene(updateScene);
            updateStage.show();

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            System.out.println("Error while attempting to open the update form");
            System.out.println(e.getMessage());
        }

    }




}
