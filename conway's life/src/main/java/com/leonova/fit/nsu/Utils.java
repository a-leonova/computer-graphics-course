package com.leonova.fit.nsu;

public class Utils {

    public static final double angle = Math.PI / 6;

    public static double insideRadius(int k){
        return k * Math.cos(angle);
    }

    public static double getLengthFromCenterYToRight(int k){
        return k * Math.sin(angle);
    }

    public static double getNextHeightY(int k){
        return 1.5 * k;
    }
}
