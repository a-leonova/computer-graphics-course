package com.nsu.fit.leonova.controller;


import java.awt.*;

public interface LogicController {
    void createGraphic();
    void createLegend();
    void gradientWasPressed();
    void drawAllLevelIsolines();
    void drawOneIsolines(Point pressedPixel);
}
