package org.example.submarineopticallinkconfigoptimization.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.example.submarineopticallinkconfigoptimization.dto.LinkResponseDTO;

public class LinkDetailsController {

    @FXML
    VBox inputVbox;
    @FXML
    VBox outputVbox;

    LinkResponseDTO linkResponseDTO;


    public LinkDetailsController(LinkResponseDTO linkResponseDTO){
        this.linkResponseDTO = linkResponseDTO;

    }

    @FXML
    public void initialize(){



    }

}
