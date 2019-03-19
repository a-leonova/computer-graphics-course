package com.nsu.fit.leonova.model.volumeRendering;

public class Charge {
    private double x;
    private double y;
    private double z;
    private double q;

    public Charge(double x, double y, double z, double q) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.q = q;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getQ() {
        return q;
    }
}
