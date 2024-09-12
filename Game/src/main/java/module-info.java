module com.ebabak.game {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.ebabak.game to javafx.fxml;
    exports com.ebabak.game;
}