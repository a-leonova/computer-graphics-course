package com.nsu.fit.leonova.model.memento;

import java.awt.*;

public class FigureMemento {
    private final BSplineMemento bSplineMemento;
    private final Color color;
    private final int[] shift;
    private final double[][] rotation;

    public FigureMemento(BSplineMemento bSplineMemento, Color color, int[] shift, double[][] rotation) {
        this.bSplineMemento = bSplineMemento;
        this.color = color;
        this.shift = shift;
        this.rotation = rotation;
    }

    public BSplineMemento getbSplineMemento() {
        return bSplineMemento;
    }

    public Color getColor() {
        return color;
    }

    public int[] getShift() {
        return shift;
    }

    public double[][] getRotation() {
        return rotation;
    }
}
