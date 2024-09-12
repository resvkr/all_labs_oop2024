package com.ebabak.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawApplication extends Application {


    // Глобальні змінні для малювання на Canvas
    private static Canvas canvas;
    private static GraphicsContext gc;
    // Словник для зберігання кольорів для кожного клієнта

    private static Map<Client, Color> clientColor = new HashMap<>();
    private static List<Strokes> strokes = new ArrayList<>();

    // Зміщення для малювання
    private double offsetX = 0;
    private double offsetY = 0;

    // Мітка для відображення зміщення
    private Label offsetLabel = new Label();

    @Override
    public void start(Stage stage) throws IOException {

        // Ініціалізація Canvas та GraphicsContext
        canvas = new Canvas(500, 500);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);// Заповнюємо Canvas білим кольором
        gc.fillRect(0,0,500,500);

        updateOffsetLabel();// Оновлюємо мітку зміщення

        // Створення кореневого контейнера та додавання Canvas та мітки
        VBox root = new VBox();
        root.getChildren().addAll(offsetLabel, canvas);

        // Налаштування сцени та прив'язування обробника подій клавіатури
        Scene scene = new Scene(root, 500, 500);
       scene.setOnKeyPressed(this::keyPress);

        // Налаштування вікна програми
        stage.setScene(scene);
        stage.show();

        // Запуск сервера на окремому потоці
        new Thread(new Server(8082)).start();
    }

    // Обробка натискань клавіш для зміщення малюнка
    private void keyPress(KeyEvent e) {
        switch (e.getCode()){
            case UP:    offsetY -= 10; break; // Переміщення вверх зменшує Y
            case DOWN:  offsetY += 10; break; // Переміщення вниз збільшує Y
            case LEFT:  offsetX -= 10; break; // Переміщення вліво зменшує X
            case RIGHT: offsetX += 10; break; // Переміщення вправо збільшує X
        }

        updateOffsetLabel(); // Оновлюємо мітку зі зміщенням
        redraw();// Перемальовуємо Canvas
    }

    // Оновлення тексту на мітці для відображення зміщення
    private void updateOffsetLabel() {
        offsetLabel.setText("Offset: (" + offsetX + ", " + offsetY + ")");
    }

    // Метод для перемальовування Canvas з урахуванням зміщення
    public void redraw(){
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 500, 500);

        if (strokes!=null){
            for (Strokes stroke: strokes){
                gc.setStroke(stroke.getColor());
                gc.setLineWidth(2.0);
                gc.strokeLine(
                        stroke.getPoint1() - offsetX,
                        stroke.getPoint2() - offsetY,
                        stroke.getPoint3() - offsetX,
                        stroke.getPoint4() - offsetY

                );
            }
        }
    }


    // Метод для встановлення кольору для клієнта
    public static void setColor(Client client, Color color) {
        clientColor.put(client, color);
    }



    // Метод для малювання ліній на Canvas
    public static void drawLines(String points, Client client){
        String[] pointsArray = points.split(" ");
        double[] pointsArrayDouble = new double[pointsArray.length];
        for(int i = 0; i < pointsArray.length; i++){
            pointsArrayDouble[i] = Double.parseDouble(pointsArray[i]);
        }
        Color color = clientColor.getOrDefault(client, Color.BLACK);

        gc.setStroke(color);
        gc.setLineWidth(2.0);
        gc.strokeLine(pointsArrayDouble[0], pointsArrayDouble[1], pointsArrayDouble[2], pointsArrayDouble[3]);

        strokes.add(new Strokes(color, pointsArrayDouble[0], pointsArrayDouble[1], pointsArrayDouble[2], pointsArrayDouble[3]));
    }

    public static void main(String[] args) {
        launch(); // Запуск програми
    }

}