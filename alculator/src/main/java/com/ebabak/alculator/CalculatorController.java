package com.ebabak.alculator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class CalculatorController {
    @FXML
    private ChoiceBox<Operator> choiseBox;

    @FXML
    private Button button;

    @FXML
    private TextField leftFeild;

    @FXML
    private TextField resultFeild;

    @FXML
    private TextField rightFeild;


    public void setCalculation(Calculation calculation) {
    }

    // Ініціалізує контролер після завантаження FXML
    @FXML
    private void initialize() {
        // Додаємо всі можливі операції (значення елементів enum Operator) в ChoiceBox
        choiseBox.getItems().addAll(Operator.values());
        choiseBox.setValue(Operator.ADDITION);
    }

    @FXML
    private void solve() {
        // Отримуємо значення з текстових полів і перетворюємо їх у числа
        double left = Double.parseDouble(leftFeild.getText());
        double right = Double.parseDouble(rightFeild.getText());
        // Отримуємо обраний оператор з ChoiceBox
        Operator operator = choiseBox.getValue();
        double result = calculate(left, right, operator);
        // Встановлюємо результат у поле для відображення результату
        resultFeild.setText(Double.toString(result));
    }

    private double calculate(Double left, Double right, Operator operator) {
        double result = 0;
        switch (operator) {
            case ADDITION -> {
                result = left + right;
            }
            case SUBTRACTION -> {
                result = left - right;
            }
            case MULTIPLICATION -> {
                result = left * right;
            }
            case DIVISION -> {
                if (!(right == 0)) {
                    result = left / right;
                } else {
                    throw new ArithmeticException("Division by zero");
                }
            }
        }
        return result;
    }


}