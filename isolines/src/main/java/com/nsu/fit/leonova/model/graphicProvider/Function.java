package com.nsu.fit.leonova.model.graphicProvider;

public class Function {
    public static double countValue(DoublePoint point){
        return Math.sin(point.getX()) + Math.cos(point.getY());
    }
}
