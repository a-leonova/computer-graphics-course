package com.nsu.fit.leonova.controller;

import com.nsu.fit.leonova.model.Point3D;
import com.nsu.fit.leonova.model.World3D;
import com.nsu.fit.leonova.model.bspline.SplineParameters;

import java.awt.*;

public class Controller implements BSplineController, WorldController{
    private World3D world3D;

    public Controller(World3D world3D) {
        this.world3D = world3D;
    }

    @Override
    public void ImageLeftPressed(Point pressedPoint) {
        world3D.pressedPointOnCurrentBSpline(pressedPoint);
    }

    @Override
    public void ImageLeftDragged(Point draggedPoint) {
        world3D.draggedPointOnCurrentBSpline(draggedPoint);
    }

    @Override
    public void ImageLeftClicked(Point clickedPoint) {
        world3D.addPointToCurrentBSpline(clickedPoint);
    }

    @Override
    public void ImageRightClicked(Point clickedPoint) {
        world3D.removePointFromCurrentBSpline(clickedPoint);
    }

    @Override
    public void apply(SplineParameters parameters) {
        world3D.setSplineParameters(parameters);
        world3D.showSpline3D();
    }

    @Override
    public void addSpline() {
        world3D.addSpline();
    }

    @Override
    public void removeSpline(int index) {
        world3D.removeSpline(index);
    }

    @Override
    public void shiftX(int dx) {
        world3D.rotationForOY(dx);
    }

    @Override
    public void shiftY(int dy) {
        world3D.rotationForOX(dy);
    }

    @Override
    public void settingsButtonPressed() {
        world3D.showBSplineInfo(0);
    }

    @Override
    public void setFigureCenter(Point3D figureCenter) {
        world3D.setFigureCenter(figureCenter);
    }

    @Override
    public void showBSplineInfo(int index) {
        world3D.showBSplineInfo(index);
    }
}
