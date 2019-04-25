package com.nsu.fit.leonova.model.bspline;

import java.awt.*;

public class SplineParameters {
    private String splineName = "Figure with some number";
    private int n;
    private int m;
    private int k;

    private int a;
    private int b;
    private int c;
    private int d;

    private Color color;

    private int zn;
    private int zf;
    private int sw;
    private int sh;

    public SplineParameters(int n, int m, int k, int a, int b, int c, int d, Color color, int zn, int zf, int sw, int sh) {
        this.n = n;
        this.m = m;
        this.k = k;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.color = color;
        this.zn = zn;
        this.zf = zf;
        this.sw = sw;
        this.sh = sh;
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

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getC() {
        return c;
    }

    public int getD() {
        return d;
    }

    public Color getColor() {
        return color;
    }

    public int getZn() {
        return zn;
    }

    public int getZf() {
        return zf;
    }

    public int getSw() {
        return sw;
    }

    public int getSh() {
        return sh;
    }
}
