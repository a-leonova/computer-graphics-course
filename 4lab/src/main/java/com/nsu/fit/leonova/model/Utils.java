package com.nsu.fit.leonova.model;

import org.ejml.simple.SimpleMatrix;

public class Utils {
    private static final double EPS = 0.000001;

    public static final int INSIDE = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 1 << 1;
    public static final int TOP = 1 << 2;
    public static final int BOTTOM = 1 << 3;
    public static final int NEAR = 1 << 4;
    public static final int FAR = 1 << 5;

    public static int checkBounds(SimpleMatrix p, double xMin, double xMax,
                    double yMin, double yMax,
                    double zMin, double zMax){
        int bounds = INSIDE;

        if (p.get(0, 0) + EPS < xMin)
            bounds |= LEFT;
        if (p.get(0, 0) - EPS > xMax)
            bounds |= RIGHT;
        if (p.get(1, 0) + EPS< yMin)
            bounds |= BOTTOM;
        if (p.get(1, 0) - EPS> yMax)
            bounds |= TOP;
        if (p.get(2, 0) + EPS< zMin)
            bounds |= NEAR;
        if (p.get(2, 0) - EPS> zMax)
            bounds |= FAR;
        return bounds;
    }
}
