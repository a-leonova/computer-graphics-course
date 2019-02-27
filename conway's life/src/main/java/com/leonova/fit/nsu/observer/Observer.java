package com.leonova.fit.nsu.observer;

import com.leonova.fit.nsu.model.Cell;
import com.leonova.fit.nsu.view.GraphicsOptions;

import java.util.HashSet;

public interface Observer {
    void updateGraphicField(HashSet<Cell> changedCells);
    void displayImpact(HashSet<Cell> cells);

    void repaintAll(HashSet<Cell> cells, GraphicsOptions graphicsOptions);
}
