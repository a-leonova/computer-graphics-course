package com.nsu.fit.leonova.model;

import java.awt.image.BufferedImage;

public interface IsolineManager {
    void drawIsolines();
    void setWidthInSquares(int width);
    void setHeightInSquares(int height);
    void setDefinitionArea(double minX, double minY, double maxX, double maxY);
}
