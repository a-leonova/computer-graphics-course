package com.leonova.fit.nsu.model;

public interface FieldModel {
    void pressedCell(Position position);
    void nextStep();
    void clearField();
    void displayImpact();
    void stopDisplayImpact();
}
