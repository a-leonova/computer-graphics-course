package com.leonova.fit.nsu.controller;

import com.leonova.fit.nsu.model.Position;

public interface GameController {
    void clearField();
    void nextStep();
    void run();
    void setXor();
    void setReplace();
    void displayImpact();
    void pressCell(Position position);

}
