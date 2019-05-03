package com.nsu.fit.leonova.model;

import com.nsu.fit.leonova.model.bspline.SplineParameters;
import com.nsu.fit.leonova.model.memento.WorldMemento;
import org.ejml.simple.SimpleMatrix;

import java.awt.*;

public interface World3D {
    void showSpline3D();
    void rotationForOX(int shift);
    void rotationForOY(int shift);

    void addPointToCurrentBSpline(Point point);
    void removePointFromCurrentBSpline(Point point);
    void pressedPointOnCurrentBSpline(Point point);
    void draggedPointOnCurrentBSpline(Point point);

    void setFigureRotation(SimpleMatrix rotationMatrix);

    void showBSplineInfo(int index);
    void addSpline();
    void removeSpline(int index);
    void removeAllSplines();

    void setSplineParameters(SplineParameters parameters);

    void setFigureCenter(Point3D figureCenter);

    void setSelectedFigure(int index);
    void setWorldParameters(WorldParameters wp);

    void scale(double ds);

    void settingsButtonPressed();

    void updateWorldParameters();

    WorldMemento getWorldMemento();

    void resetAngles();

    void setBackgroundColor(Color backgroundColor);
    void setWorldRotation(SimpleMatrix worldRotation);
}
