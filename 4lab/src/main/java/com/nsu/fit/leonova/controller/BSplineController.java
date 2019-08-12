package com.nsu.fit.leonova.controller;

import com.nsu.fit.leonova.model.bspline.SplineParameters;

import java.awt.*;

public interface BSplineController {
    void ImageLeftPressed(Point pressedPoint);
    void ImageLeftDragged(Point draggedPoint);
    void ImageLeftClicked(Point clickedPoint);
    void ImageRightClicked(Point clickedPoint);
    void apply(SplineParameters parameters);

    void addSpline();
    void showBSplineInfo(int index);
    void removeSpline(int index);

    void scale();
}
