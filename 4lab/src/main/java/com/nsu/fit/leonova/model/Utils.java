package com.nsu.fit.leonova.model;

import org.ejml.simple.SimpleMatrix;

public class Utils {
    private static final double EPS = 0.000001;

    private static final int INSIDE = 0;
    private static final int LEFT = 1;
    private static final int RIGHT = 1 << 1;
    private static final int TOP = 1 << 2;
    private static final int BOTTOM = 1 << 3;
    private static final int NEAR = 1 << 4;
    private static final int FAR = 1 << 5;

    private static int checkBounds(Vector3D point, double xMin, double xMax,
                                   double yMin, double yMax,
                                   double zMin, double zMax){
        int bounds = INSIDE;
        if (point.get(0) + EPS < xMin)
            bounds |= LEFT;
        if (point.get(0) - EPS > xMax)
            bounds |= RIGHT;
        if (point.get(1) + EPS< yMin)
            bounds |= BOTTOM;
        if (point.get(1) - EPS> yMax)
            bounds |= TOP;
        if (point.get(2) + EPS< zMin)
            bounds |= NEAR;
        if (point.get(2) - EPS> zMax)
            bounds |= FAR;
        return bounds;
    }

    public static boolean clipLineInCube(Vector3D point1, Vector3D point2,
                                         double minX, double maxX,
                                         double minY, double maxY,
                                         double minZ, double maxZ){
        int bound1 = checkBounds(point1, minX, maxX, minY, maxY, minZ, maxZ);
        int bound2 = checkBounds(point2, minX, maxX, minY, maxY, minZ, maxZ);

        double normF = (point2.minus(point1)).normF();
        Vector3D delta = (point2.minus(point1)).divide(normF);

        while (true){
            if((bound1 | bound2) == 0){
                return true;
            }
            if ((bound1 & bound2) != 0){
                return false;
            }
            int bound = bound1 != 0 ? bound1 : bound2;
            double shift = 0;

            boolean invert = false;
            if((bound & TOP) != 0){
                shift = (maxY - point1.get(1))/delta.get(1);
                if (point1.get(1) > maxY){
                    invert = true;
                }
            }
            else if ((bound & BOTTOM) != 0){
                shift = (minY - point1.get(1))/delta.get(1);
                if (point1.get(1) < minY){
                    invert = true;
                }
            }
            else if ((bound & RIGHT) != 0){
                shift = (maxX -  point1.get(0))/delta.get(0);
                if (point1.get(0) > maxX) {
                    invert = true;
                }
            }
            else if ((bound & LEFT) != 0){
                shift = (minX - point1.get(0))/delta.get(0);
                if (point1.get(0) < minX) {
                    invert = true;
                }
            } else if ((bound & FAR) != 0){
                shift = (maxZ - point1.get(2))/delta.get(2);
                if (point1.get(2) > maxZ){
                    invert = true;
                }
            } else if ((bound & NEAR) != 0){
                shift = (minZ - point1.get(2))/delta.get(2);
                if (point1.get(2) < minZ)
                    invert = true;
            }

            if (invert){
                point1.set(point1.plus(delta.divide(1 / shift)));
                bound1 = Utils.checkBounds(point1, minX, maxX, minY, maxY, minZ, maxZ);
            } else{
                point2.set(point1.plus(delta.divide(1 / shift)));
                bound2 = Utils.checkBounds(point2, minX, maxX, minY, maxY, minZ, maxZ);
            }
        }
    }
}
