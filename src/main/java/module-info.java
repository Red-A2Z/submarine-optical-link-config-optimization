module org.example.submarineopticallinkconfigoptimization {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires static lombok;
    requires gurobi;
    requires ojalgo;
    requires java.sql;
    requires org.commonmark;
    requires javafx.web;
    requires openhtmltopdf.pdfbox;

    opens org.example.submarineopticallinkconfigoptimization to javafx.fxml;
    exports org.example.submarineopticallinkconfigoptimization;
    exports org.example.submarineopticallinkconfigoptimization.controller;
    opens org.example.submarineopticallinkconfigoptimization.controller to javafx.fxml;
}