package com.nsu.fit.leonova.model.bspline;

import java.awt.*;

public class SplineParameters {
    private String splineName = "Figure with some number";
    private int n;
    private int m;
    private int k;

    private double a;
    private double b;
    private double c;
    private double d;

    private Color color;

    private int zf;
    private int zb;
    private int sw;
    private int sh;

    public SplineParameters(int n, int m, int k, double a, double b, double c, double d, Color color, int zf, int zb, int sw, int sh) {
        this.n = n;
        this.m = m;
        this.k = k;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.color = color;
        this.zf = zf;
        this.zb = zb;
        this.sw = sw;
        this.sh = sh;
    }

    public SplineParameters(String splineName, int n, int m, int k, double a, double b, double c, double d, Color color, int zf, int zb, int sw, int sh) {
        this(n, m, k, a, b, c, d, color, zf, zb, sw, sh);
        this.splineName = splineName;
    }

    public SplineParameters(SplineParameters p){
        splineName = p.splineName;
        a = p.a;
        b = p.b;
        c = p.c;
        d = p.d;

        n = p.n;
        m = p.m;
        k = p.k;

        color = p.color;

        zf = p.zf;
        zb = p.zb;
        sw = p.sw;
        sh = p.sh;
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

    public int getZf() {
        return zf;
    }

    public int getZb() {
        return zb;
    }

    public int getSw() {
        return sw;
    }

    public int getSh() {
        return sh;
    }
}
