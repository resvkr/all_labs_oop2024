package com.ebabak.artserver.entity;

import java.util.Objects;

public class User {
    private long time;
    private final int token;
    private boolean canDraw = true;


    public User() {
        this.time = System.currentTimeMillis();
        token = Math.abs(hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return time == user.time;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(time);
    }

    public long getTime() {
        return time;
    }

    public int getToken() {
        return token;
    }

    public void updateTimeAndStatus() {
        this.time = System.currentTimeMillis();
        canDraw = true;
    }

    @Override
    public String toString() {
        return "User{" +
                "time=" + time +
                ", token=" + token +
                ", canDraw=" + canDraw +
                '}';
    }

    public boolean canDraw() {
        return canDraw;
    }

    public void setCanDraw(boolean canDraw) {
        this.canDraw = canDraw;
    }
}
