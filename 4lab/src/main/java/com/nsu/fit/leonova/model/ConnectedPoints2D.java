package com.nsu.fit.leonova.model;

import java.awt.*;

public class ConnectedPoints2D {
    private Point a;
    private Point b;

    public ConnectedPoints2D(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }
}
