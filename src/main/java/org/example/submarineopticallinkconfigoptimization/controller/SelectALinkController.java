package org.example.submarineopticallinkconfigoptimization.controller;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class SelectALinkController {

    @FXML
    private VBox vBox;

    private final ScrollPane centerScrollPane;

    public SelectALinkController(ScrollPane centerScrollPane) {
        this.centerScrollPane = centerScrollPane;
    }

    public void initialize(){
        vBox.minHeightProperty().bind(
                centerScrollPane.viewportBoundsProperty().map(Bounds::getHeight)
        );

        vBox.minWidthProperty().bind(
                centerScrollPane.viewportBoundsProperty().map(Bounds::getWidth)
        );
    }

}
