package com.nsu.fit.leonova.controller;

import com.nsu.fit.leonova.model.BSplineCreator;
import com.nsu.fit.leonova.model.World3D;

import java.awt.*;

public class Controller implements BSplineController {
    private BSplineCreator bSplineCreator;
    private World3D world3D;

    public void setbSplineCreator(BSplineCreator bSplineCreator) {
        this.bSplineCreator = bSplineCreator;
    }

    public void setWorld3D(World3D world3D) {
        this.world3D = world3D;
    }

    @Override
    public void ImageLeftPressed(Point pressedPoint) {
        bSplineCreator.pressedPoint(pressedPoint);
    }

    @Override
    public void ImageLeftDragged(Point draggedPoint) {
        bSplineCreator.draggedPoint(draggedPoint);
    }

    @Override
    public void ImageLeftClicked(Point clickedPoint) {
        bSplineCreator.addPoint(clickedPoint);
    }

    @Override
    public void ImageRightClicked(Point clickedPoint) {
        bSplineCreator.removePoint(clickedPoint);
    }

    @Override
    public void apply() {
        world3D.showSpline3D();
        //world3D.drawAxis();
    }
}
