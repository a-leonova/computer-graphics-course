package com.nsu.fit.leonova.model.memento;


import java.awt.Point;
import java.util.List;

public class BSplineMemento {
    private final List<Point> pivotPoints;

    public BSplineMemento(List<Point> pivotPoints) {
        this.pivotPoints = pivotPoints;
    }

    public List<Point> getPivotPoints() {
        return pivotPoints;
    }
}
