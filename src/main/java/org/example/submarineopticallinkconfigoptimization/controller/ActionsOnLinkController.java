package org.example.submarineopticallinkconfigoptimization.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.example.submarineopticallinkconfigoptimization.dto.LinkResponseDTO;

public class ActionsOnLinkController {

    @FXML
    private Label linkNameLabel;
    @FXML
    private TextArea linkDescriptionTextArea;

    private final LinkResponseDTO linkResponseDTO;

    public ActionsOnLinkController(LinkResponseDTO linkResponseDTO) {
        this.linkResponseDTO = linkResponseDTO;
    }

    public void initialize(){

        linkNameLabel.setText(linkResponseDTO.getLinkName());
        linkDescriptionTextArea.setText(linkResponseDTO.getLinkDescription());

    }




}
