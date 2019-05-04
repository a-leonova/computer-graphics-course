package com.nsu.fit.leonova.observer;

import com.nsu.fit.leonova.model.Point3D;
import com.nsu.fit.leonova.model.world.WorldParameters;

import java.awt.image.BufferedImage;

public interface WorldObserver {
    void setMainImage(BufferedImage image);

    void addFigure(String name);
    void removeFigure(int index);
    void renameFigure(String name, int index);
    void setInfo(Point3D figureCenter);
    void updateWorldParameters(WorldParameters worldParameters);
    void showError(String message);
}
