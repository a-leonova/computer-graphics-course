package com.nsu.fit.leonova.controller;

import java.awt.*;

public interface BSplineController {
    void ImageLeftPressed(Point pressedPoint);
    void ImageLeftDragged(Point draggedPoint);
    void ImageLeftClicked(Point clickedPoint);
    void ImageRightClicked(Point clickedPoint);
    void apply();

    void addSpline();
    void removeSpline(int index);
}
