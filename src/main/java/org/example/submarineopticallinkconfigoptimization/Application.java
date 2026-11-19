package org.example.submarineopticallinkconfigoptimization;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("fxml/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 752, 450);
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();
    }
}
