package org.example.submarineopticallinkconfigoptimization.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.submarineopticallinkconfigoptimization.Application;
import org.example.submarineopticallinkconfigoptimization.dto.LinkResponseDTO;
import org.example.submarineopticallinkconfigoptimization.exception.RepositoryException;
import org.example.submarineopticallinkconfigoptimization.service.LinkService;

import java.io.IOException;
import java.util.Optional;

public class ActionsOnLinkController {

    @FXML
    private Label linkNameLabel;
    @FXML
    private TextArea linkDescriptionTextArea;

    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;

    private final ScrollPane centerScrollPane;

    private final LinkResponseDTO selectedLink;

    private final LinkService linkService = new LinkService();


    public ActionsOnLinkController(LinkResponseDTO selectedLink,ScrollPane centerScrollPane) {
        this.selectedLink = selectedLink;
        this.centerScrollPane=centerScrollPane;
    }

    public void initialize(){

        linkNameLabel.textProperty().bind(selectedLink.getLinkName());
        linkDescriptionTextArea.textProperty().bind(selectedLink.getLinkDescription());

        updateButton.setOnAction(event -> updateButtonClicked());
        deleteButton.setOnAction(event -> deleteButtonClicked());

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



    private void deleteButtonClicked(){

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Are you sure?");
        confirmationAlert.setContentText("Do you want to delete this link?");

        Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();

        if(confirmationResult.isPresent() && confirmationResult.get()==ButtonType.OK){

            try {

                linkService.deleteLink(selectedLink.getId());


                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("select-a-link-view.fxml"));
                    centerScrollPane.setContent(fxmlLoader.load());
                    selectedLink.getLinkName().set(""); // this line is to trigger the links list refresh
                }catch (IOException e){
                    System.out.println("Error while trying to change centerScrollPane");
                    System.out.println(e.getMessage());
                }


            }catch(RepositoryException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Repository error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

        }


    }




}
