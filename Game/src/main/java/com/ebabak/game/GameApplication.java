package com.ebabak.game;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {

    private Canvas canvas;// Полотно для малювання на сцені
    private GraphicsContext gc;// Контекст для малювання на Canvas

    private static final int rectangleSize = 20;  // Розмір прямокутника (гравця і супротивника)
    private static final int step = 2;  // Крок, на який рухаються об'єкти
    private double playerX = 50;  // Початкова координата гравця по X
    private double playerY = 50;  // Початкова координата гравця по Y

    private double enemyX = 200;  // Початкова координата супротивника по X
    private double enemyY = 200;  // Початкова координата супротивника по Y

    // Логічні змінні для відслідковування натиснутих клавіш
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    @Override
    public void start(Stage stage) throws IOException {
        canvas = new Canvas(800, 600); // Створюємо полотно розміром 800x600 пікселів
        gc = canvas.getGraphicsContext2D();// Отримуємо контекст для малювання
        draw();// Малюємо початковий стан

        movingRectangle();  // Налаштовуємо рух гравця
        automaticEnemyMoving();  // Налаштовуємо автоматичний рух супротивника


        Pane root = new Pane();  // Створюємо контейнер для розміщення полотна
        root.getChildren().add(canvas);  // Додаємо полотно до контейнера

        Scene scene = new Scene(root);// Створюємо сцену з кореневим елементом

        // Додаємо обробку натискань і відпускання клавіш
        scene.setOnKeyPressed(event -> handleKeyEvent(event.getCode(), true));
        scene.setOnKeyReleased(event -> handleKeyEvent(event.getCode(), false));


        stage.setScene(scene);  // Встановлюємо сцену на сцену
        stage.show();  // Відображаємо сцену

        canvas.requestFocus();  // Фокус на Canvas для обробки подій з клавіатури

    }

    private void draw() {
        // Очищаємо все полотно перед малюванням
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Малюємо гравця як синій прямокутник
        gc.setFill(Color.BLUE);
        gc.fillRect(playerX, playerY, rectangleSize, rectangleSize);

        // Малюємо супротивника як темно-червоний прямокутник
        gc.setFill(Color.DARKRED);
        gc.fillRect(enemyX, enemyY, rectangleSize, rectangleSize);
    }

    private void movingRectangle() {

        // Додаємо обробку натискань клавіш для руху гравця
        canvas.setOnKeyPressed(event -> {

            // Отримуємо натиснуту клавішу
            KeyCode key = event.getCode();
            switch (key) {
                case UP -> movePlayer(0, -step);
                case DOWN -> movePlayer(0, step);
                case LEFT -> movePlayer(-step, 0);
                case RIGHT -> movePlayer(step, 0);
            }
            draw();  // Оновлюємо відображення після руху
        });
    }


    private void movePlayer(double x, double y) {
        // Обчислюємо нові координати гравця
        double newX = playerX + x;
        double newY = playerY + y;

        // Перевіряємо межі руху гравця по X, щоб він не виходив за межі полотна
        if (newX >= 0 && newX <= canvas.getWidth() - rectangleSize) {
            playerX = newX;  // Оновлюємо координату по X
        }

        // Перевіряємо межі руху гравця по Y, щоб він не виходив за межі полотна
        if (newY >= 0 && newY <= canvas.getHeight() - rectangleSize) {
            playerY = newY;  // Оновлюємо координату по Y
        }
    }

    private void movePlayer() {
        double x = 0;  // Зміщення по X
        double y = 0;  // Зміщення по Y

        // Змінюємо зміщення в залежності від того, яка клавіша натиснута
        if (upPressed) y -= step;  // Якщо натиснуто UP, рух вгору
        if (downPressed) y += step;  // Якщо натиснуто DOWN, рух вниз
        if (leftPressed) x -= step;  // Якщо натиснуто LEFT, рух вліво
        if (rightPressed) x += step;  // Якщо натиснуто RIGHT, рух вправо

        // Обчислюємо нові координати гравця
        double newX = playerX + x;
        double newY = playerY + y;

        // Перевіряємо межі руху по X і Y
        if (newX >= 0 && newX <= canvas.getWidth() - rectangleSize)
            playerX = newX;
        if (newY >= 0 && newY <= canvas.getHeight() - rectangleSize)
            playerY = newY;

    }


    private void automaticEnemyMoving() {

        // Створюємо анімаційний таймер для постійного оновлення положення супротивника
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                chasePlayer();// Викликаємо логіку переслідування гравця
                draw();// Оновлюємо графіку
            }
        };

        timer.start();// Запускаємо таймер
    }

    private void chasePlayer() {
        double x = playerX - enemyX;
        double y = playerY - enemyY;

        double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        if (distance > 0) {
            x = x / distance;
            y = y / distance;
            enemyX = enemyX + (x * step);
            enemyY = enemyY + (y * step);
        }
    }

    private void handleKeyEvent(KeyCode key, boolean pressed) {
        // Встановлюємо прапор для кожної клавіші залежно від її стану (натиснута/відпущена)
        switch (key) {
            case UP -> upPressed = pressed;
            case DOWN -> downPressed = pressed;
            case LEFT -> leftPressed = pressed;
            case RIGHT -> rightPressed = pressed;
        }
    }

}