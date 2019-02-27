package com.leonova.fit.nsu.controller;

import com.leonova.fit.nsu.model.GameOptions;
import com.leonova.fit.nsu.model.Position;
import com.leonova.fit.nsu.view.GraphicsOptions;

public interface GameController {
    void clearField();
    void nextStep();
    void run();
    void setXor();
    void setReplace();
    void displayImpact();
    void pressCell(Position position);

    void newOptions(GameOptions gameOptions, GraphicsOptions graphicsOptions);

    void createNewField(GameOptions gameOptions, GraphicsOptions graphicsOptions);

}
