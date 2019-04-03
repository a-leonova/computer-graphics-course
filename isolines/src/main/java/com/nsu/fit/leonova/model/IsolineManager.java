package com.nsu.fit.leonova.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface IsolineManager {
    void drawAllLevelIsolines();
    void drawOneIsolines(Point pressedPixel);
    void setDefinitionArea(double minX, double minY, double maxX, double maxY);
    void setWidthInSquares(int width);
    void setHeightInSquares(int height);
}
