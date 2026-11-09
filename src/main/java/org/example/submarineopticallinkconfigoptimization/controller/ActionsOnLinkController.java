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

    private final LinkResponseDTO selectedLink;

    public ActionsOnLinkController(LinkResponseDTO selectedLink) {
        this.selectedLink = selectedLink;
    }

    public void initialize(){

        linkNameLabel.setText(selectedLink.getLinkName());
        linkDescriptionTextArea.setText(selectedLink.getLinkDescription());

    }




}
