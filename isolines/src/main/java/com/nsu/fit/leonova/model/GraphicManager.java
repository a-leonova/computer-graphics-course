package com.nsu.fit.leonova.model;

import java.awt.*;

public interface GraphicManager {
    void createGraphic(boolean gradient);
    void createLegend(boolean gradient);
    void setColorsRGB(SafeColor[] colorsRGB);
    void setDefinitionArea(double minX, double minY, double maxX, double maxY);
    void drawNet();
    void pivotPoints();
    void pixelToCoordinate(Point pixel);
}
