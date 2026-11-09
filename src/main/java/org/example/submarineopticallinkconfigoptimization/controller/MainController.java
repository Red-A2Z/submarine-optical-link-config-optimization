package org.example.submarineopticallinkconfigoptimization.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.util.List;

public class MainController {

    //Menu (Top)
    @FXML
    private MenuItem exportAsPdfMenuItem;
    @FXML
    private MenuItem aboutMenuItem;

    //Left side
    @FXML
    private Button newLinkButton;
    @FXML
    private TextField searchTextField;

    //Center
    @FXML
    private ScrollPane centerScrollPane;

    private final ObservableList<LinkResponseDTO> links = FXCollections.observableArrayList();
    private final FilteredList<LinkResponseDTO> filteredList = new FilteredList<>(links, link -> true);

    @FXML
    private ListView<LinkResponseDTO> linksList;



    private LinkService linkService = new LinkService();



    @FXML
    public void initialize(){

        try{
            noLinkIsSelected();
        }catch (IOException e){
            System.out.println("Error while running noLinkIsSelected()");
            System.out.println(e.getMessage());

        };


        linksList.setItems(filteredList);
        searchTextField.textProperty().addListener((obs,oldValue,newValue)->
                filteredList.setPredicate(link -> link.getLinkName().toLowerCase().contains(searchTextField.getText().toLowerCase()))
        );

        try{// Populate "links" with DB records
            List<LinkResponseDTO> recordsListResponseDTO = linkService.getAllRecords();
            links.addAll(recordsListResponseDTO);
        }catch(RepositoryException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Database Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }





    @FXML
    protected void mouseClickedOnListView(){


        LinkResponseDTO selectedLink = linksList.getSelectionModel().getSelectedItem();
        if (selectedLink != null){
            try{
                aLinkIsSelected(selectedLink);
            }catch (IOException e){
                System.out.println("Error while running aLinkIsSelected()");
                System.out.println(e.getMessage());
            };

        }

    }


    @FXML
    protected void openLinkForm() throws IOException {

        Stage linkFormStage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("link-form-view.fxml"));
        fxmlLoader.setController(new LinkFormController(linkFormStage,links));
        Scene linkFormScene = new Scene(fxmlLoader.load());
        linkFormStage.setScene(linkFormScene);
        linkFormStage.show();


    }


    private void noLinkIsSelected() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("select-a-link-view.fxml"));
        centerScrollPane.setContent(fxmlLoader.load());

    }


    private void aLinkIsSelected(LinkResponseDTO selectedLink) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("actions-on-link-view.fxml"));
        fxmlLoader.setController(new ActionsOnLinkController(selectedLink));
        centerScrollPane.setContent(fxmlLoader.load());
    }





}
