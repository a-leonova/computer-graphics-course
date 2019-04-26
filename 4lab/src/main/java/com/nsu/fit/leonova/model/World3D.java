package com.nsu.fit.leonova.model;

import java.awt.*;

public interface World3D {
    void showSpline3D();
    void rotationForOX(int shift);
    void rotationForOY(int shift);

    void addPointToCurrentBSpline(Point point);
    void removePointFromCurrentBSpline(Point point);
    void pressedPointOnCurrentBSpline(Point point);
    void draggedPointOnCurrentBSpline(Point point);

    void showBSplineInfo(int index);
    void addSpline();
    void removeSpline(int index);
}
