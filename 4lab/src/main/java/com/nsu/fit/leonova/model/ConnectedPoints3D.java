package com.nsu.fit.leonova.model;

public class ConnectedPoints3D {
    private Point3D a;
    private Point3D b;

    public ConnectedPoints3D(Point3D a, Point3D b) {
        this.a = a;
        this.b = b;
    }

    public Point3D getA() {
        return a;
    }

    public Point3D getB() {
        return b;
    }

    public void setA(Point3D a) {
        this.a = a;
    }

    public void setB(Point3D b) {
        this.b = b;
    }
}
