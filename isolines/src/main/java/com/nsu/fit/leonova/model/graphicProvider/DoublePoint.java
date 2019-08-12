package com.nsu.fit.leonova.model.graphicProvider;

import java.awt.*;

public class DoublePoint {
    private final double x;
    private final double y;

    public DoublePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public DoublePoint(Point point){
        x = point.x;
        y = point.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
