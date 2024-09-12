module com.ebabak.alculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.ebabak.alculator to javafx.fxml;
    exports com.ebabak.alculator;
}