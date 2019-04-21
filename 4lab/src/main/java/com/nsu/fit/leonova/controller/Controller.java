package com.nsu.fit.leonova.controller;

import com.nsu.fit.leonova.model.BSpline;

import java.awt.*;

public class Controller implements BSplineController {
    private BSpline bSpline;

    public void setbSpline(BSpline bSpline) {
        this.bSpline = bSpline;
    }

    @Override
    public void ImageLeftPressed(Point pressedPoint) {

    }

    @Override
    public void ImageLeftDragged(Point draggedPoint) {

    }

    @Override
    public void ImageLeftClicked(Point clickedPoint) {
        bSpline.addPoint(clickedPoint);
    }

    @Override
    public void ImageRightClicked(Point clickedPoint) {
        bSpline.removePoint(clickedPoint);
    }
}
