package com.ebabak.demo;

import javafx.scene.paint.Color;

public class Strokes {
    private Color color;
    private double point1;
    private double point2;
    private double point3;
    private double point4;

    public Strokes(Color color, double point1, double point2, double point3, double point4) {
        this.color = color;
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.point4 = point4;
    }

    public Color getColor() {
        return color;
    }

    public double getPoint1() {
        return point1;
    }

    public double getPoint2() {
        return point2;
    }

    public double getPoint3() {
        return point3;
    }

    public double getPoint4() {
        return point4;
    }
}
