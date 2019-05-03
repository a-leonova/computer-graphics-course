package com.nsu.fit.leonova.model.memento;

import com.nsu.fit.leonova.model.WorldParameters;
import com.nsu.fit.leonova.model.bspline.SplineParameters;

import java.awt.*;
import java.util.List;


public class WorldMemento {
    private final SplineParameters splineParameters;
    private final WorldParameters worldParameters;
    private final double[][] rotation;
    private final Color color;
    private final List<FigureMemento> figureMemento;

    public WorldMemento(SplineParameters splineParameters, WorldParameters worldParameters, double[][] rotation, Color color, List<FigureMemento> figureMemento) {
        this.splineParameters = splineParameters;
        this.worldParameters = worldParameters;
        this.rotation = rotation;
        this.color = color;
        this.figureMemento = figureMemento;
    }

    public SplineParameters getSplineParameters() {
        return splineParameters;
    }

    public WorldParameters getWorldParameters() {
        return worldParameters;
    }

    public double[][] getRotation() {
        return rotation;
    }

    public Color getColor() {
        return color;
    }

    public List<FigureMemento> getFigureMemento() {
        return figureMemento;
    }
}
