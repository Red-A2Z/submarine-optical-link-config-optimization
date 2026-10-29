package org.example.submarineopticallinkconfigoptimization.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.PropertySheet;
import org.example.submarineopticallinkconfigoptimization.Application;

import java.io.IOException;

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
    @FXML
    private ListView<String> linksList;

    //Center
    @FXML
    private PropertySheet linkProperties;





    @FXML
    protected void mouseClickedOnListView(){

        System.out.println("Clicked !!!!!");

    }


    @FXML
    protected void openLinkForm() throws IOException {

        Stage linkFormStage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("link-form-view.fxml"));
        Scene linkFormScene = new Scene(fxmlLoader.load());
        linkFormStage.setScene(linkFormScene);
        linkFormStage.show();


    }
}
