package com.nsu.fit.leonova.controller;

import com.nsu.fit.leonova.model.Point3D;

public interface WorldController {
    void shiftX(int dx);
    void shiftY(int dy);

    void settingsButtonPressed();

    void setFigureCenter(Point3D figureCenter);
}
