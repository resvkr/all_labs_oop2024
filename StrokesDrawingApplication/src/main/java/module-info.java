module com.ebabak.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.ebabak.demo to javafx.fxml;
    exports com.ebabak.demo;
}