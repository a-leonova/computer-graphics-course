package com.nsu.fit.leonova.controller;

import com.nsu.fit.leonova.model.graphicProvider.GraphicValues;

public interface LogicController {
    void createGraphic(int width, int height);
    void createLegend(int width, int height);
    void gradientWasPressed();
    void drawAllLevelIsolines();
    void eraseIsolines();
    void drawNet();
    void pivotPoints();
    void setParameters(GraphicValues graphicValues, int k, int m);
    void resizeImage(int width, int height);
}
