module org.example.submarineopticallinkconfigoptimization {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;

    opens org.example.submarineopticallinkconfigoptimization to javafx.fxml;
    exports org.example.submarineopticallinkconfigoptimization;
    exports org.example.submarineopticallinkconfigoptimization.controller;
    opens org.example.submarineopticallinkconfigoptimization.controller to javafx.fxml;
}