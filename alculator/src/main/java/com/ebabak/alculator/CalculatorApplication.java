package com.ebabak.alculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CalculatorApplication extends Application {

    // Метод для запуску JavaFX додатку
    @Override
    public void start(Stage stage) throws IOException {
        // Завантажуємо FXML-файл, який містить опис графічного інтерфейсу
        FXMLLoader fxmlLoader = new FXMLLoader(CalculatorApplication.class.getResource("calculator.fxml"));
        // Створюємо сцену з завантаженого FXML-файлу
        Scene scene = new Scene(fxmlLoader.load());

        // Отримуємо контролер з завантаженого FXML
        CalculatorController controller = fxmlLoader.getController();

        // Встановлюємо сцену для основного вікна програми
        stage.setScene(scene);
        // Показуємо вікно програми
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}