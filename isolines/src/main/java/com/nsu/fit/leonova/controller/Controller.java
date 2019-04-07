package com.nsu.fit.leonova.controller;

import com.nsu.fit.leonova.model.Model;

import java.awt.*;

public class Controller implements LogicController, ImageController {
    private Model model;
    private boolean gradient = false;

    public void setModel(Model model) {
        this.model = model;
    }

    @Override
    public void createGraphic() {
        model.createGraphic(gradient);
    }

    @Override
    public void createLegend() {
        model.createLegend(gradient);
    }

    @Override
    public void gradientWasPressed() {
        gradient = !gradient;
        model.createGraphic(gradient);
        model.createLegend(gradient);
    }

    @Override
    public void drawAllLevelIsolines() {
        model.drawAllLevelIsolines();
    }

    @Override
    public void eraseIsolines() {
        model.removeIsolines();
        model.createGraphic(gradient);
    }

    @Override
    public void drawNet() {
        model.drawNet();
    }

    @Override
    public void pivotPoints() {
        model.pivotPoints();
    }

    @Override
    public void imageWasClicked(Point pressedPixel) {
        model.clickedIsoline(pressedPixel);
    }

    @Override
    public void imageWasDragged(Point draggedPixel) {
        model.draggedIsoline(draggedPixel);
    }
}
