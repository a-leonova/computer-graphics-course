package com.nsu.fit.leonova.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface IsolineManager {
    void drawAllLevelIsolines();
    void clickedIsoline(Point pressedPixel);
    void draggedIsoline(Point pressedPixel);
    void removeIsolines();
    void setNet(int k, int m);
    void setIsolineColor(SafeColor color);
}
