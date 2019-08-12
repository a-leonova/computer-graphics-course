package com.nsu.fit.leonova.controller;

import com.nsu.fit.leonova.model.Point3D;
import com.nsu.fit.leonova.model.world.WorldParameters;

import java.io.File;

public interface WorldController {
    void shiftX(int dx);
    void shiftY(int dy);

    void settingsButtonPressed();

    void setFigureCenter(Point3D figureCenter);
    void setSelectedFigure(int index);
    void setWorldParameters(WorldParameters wp);

    void openButtonPressed(File file);
    void saveButtonPressed(File file);

    void resetAngles();
}
