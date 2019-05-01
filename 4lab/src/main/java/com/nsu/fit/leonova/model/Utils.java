package com.nsu.fit.leonova.model;

import org.ejml.simple.SimpleMatrix;

public class Utils {
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

        if (p.get(0, 0) < xMin)
            bounds |= LEFT;
        if (p.get(0, 0) > xMax)
            bounds |= RIGHT;
        if (p.get(1, 0) < yMin)
            bounds |= BOTTOM;
        if (p.get(1, 0) > yMax)
            bounds |= TOP;
        if (p.get(2, 0) < zMin)
            bounds |= NEAR;
        if (p.get(2, 0) > zMax)
            bounds |= FAR;
        return bounds;
    }
}
