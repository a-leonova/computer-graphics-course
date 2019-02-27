package com.leonova.fit.nsu.model;

import com.leonova.fit.nsu.view.GraphicsOptions;

import java.util.ArrayList;
import java.util.HashSet;

public interface FieldModel {
    void pressedCell(Position position);
    void nextStep();
    void clearField();
    void impactPressed();
    void newOptions(GameOptions gameOptions, GraphicsOptions graphicsOptions);

    void newField(GraphicsOptions graphicsOptions, ArrayList<Position> aliveCells);

    HashSet<Cell> getAliveCells();
}
