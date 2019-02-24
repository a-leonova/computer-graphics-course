package com.leonova.fit.nsu.observer;

import com.leonova.fit.nsu.model.Cell;
import java.util.HashSet;

public interface Observer {
    void updateGraphicField(HashSet<Cell> changedCells);
    void stopDisplayImpact(HashSet<Cell> cells);
    void displayImpact(HashSet<Cell> cells);
}
