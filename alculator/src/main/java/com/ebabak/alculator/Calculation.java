package com.ebabak.alculator;

@FunctionalInterface
public interface Calculation {
    double calculate(double left, double right, Operator operator);
}
