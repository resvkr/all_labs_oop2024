package com.ebabak.artserver.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "pixel_request")
public final class PixelRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int x;
    private int y;
    private String color;
    private int token;

    public PixelRequest() {
    }

    public PixelRequest(int x, int y, String color, int token) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PixelRequest that = (PixelRequest) o;
        return x == that.x && y == that.y && token == that.token && Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, color, token);
    }

    @Override
    public String toString() {
        return "PixelRequest{" +
                "x=" + x +
                ", y=" + y +
                ", color='" + color + '\'' +
                ", token=" + token +
                '}';
    }
}
