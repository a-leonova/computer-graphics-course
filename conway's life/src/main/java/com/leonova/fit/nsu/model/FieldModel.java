package com.leonova.fit.nsu.model;

import com.leonova.fit.nsu.view.GraphicsOptions;

public interface FieldModel {
    void pressedCell(Position position);
    void nextStep();
    void clearField();
    void impactPressed();

    void newOptions(GameOptions gameOptions, GraphicsOptions graphicsOptions);
}
