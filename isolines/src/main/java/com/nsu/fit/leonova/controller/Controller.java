package com.nsu.fit.leonova.controller;

import com.nsu.fit.leonova.model.Model;

import java.awt.*;

public class Controller implements LogicController {
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
    public void drawOneIsolines(Point pressedPixel) {
        model.drawOneIsolines(pressedPixel);
    }
}
