package org.example.submarineopticallinkconfigoptimization.controller;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.web.WebView;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class AboutController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() throws IOException {


        String mdString = Files.readString(Path.of("README.md"));

        Parser parser = Parser.builder().build();
        HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();

        String html = htmlRenderer.render(parser.parse(mdString));

        WebView webView = new WebView();
        webView.getEngine().loadContent(html);


        scrollPane.setContent(webView);

        webView.minWidthProperty().bind(
                scrollPane.viewportBoundsProperty().map(Bounds::getWidth)
        );
        webView.minHeightProperty().bind(
                scrollPane.viewportBoundsProperty().map(Bounds::getHeight)
        );


    }

}
