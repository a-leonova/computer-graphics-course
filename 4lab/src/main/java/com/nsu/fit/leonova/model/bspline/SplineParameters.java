package com.nsu.fit.leonova.model.bspline;

import java.awt.*;

public class SplineParameters {
    private String splineName = "Figure with some number";
    private int n = 5;
    private int m = 50;
    private int k = 2;

    private double a = 0.0;
    private double b = 1.0;
    private double c = 0.0;
    private double d = 6.28;

    private Color color = new Color(255, 255, 0);

    public SplineParameters(String name){this.splineName = name;}

    public SplineParameters(String name, int n, int m, int k, double a, double b, double c, double d, Color color) {
        this.splineName = name;
        this.n = n;
        this.m = m;
        this.k = k;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.color = color;
    }

    public void setSplineName(String splineName) {
        this.splineName = splineName;
    }

    public String getSplineName() {
        return splineName;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public int getK() {
        return k;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getD() {
        return d;
    }

    public Color getColor() {
        return color;
    }
}
