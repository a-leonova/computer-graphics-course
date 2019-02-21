package com.leonova.fit.nsu.view;

import com.leonova.fit.nsu.Utils;
import com.leonova.fit.nsu.draft.Globals;
import com.leonova.fit.nsu.draft.model.Position;

public class CellsManager {
    private GraphiscOptions options;

    public CellsManager(GraphiscOptions options) {
        this.options = options;
    }

    public void setOptions(GraphiscOptions options) {
        this.options = options;
    }


    //TODO: check border!!!
    public Position getSelectedCell(Position position){
        double gridWidth = Utils.insideRadius(options.getCellEdge()) * 2;
        double halfWidth = gridWidth / 2;
        double gridHeight = 1.5 * options.getCellEdge();

        int row = (int) (position.getY() / gridWidth);
        int column;

        boolean rowIsOdd = row % 2 == 1;

        // Is the row an odd number?

        // Yes: Offset x to match the indent of the row
        if (rowIsOdd){
            column = (int) ((position.getX() - halfWidth) / gridWidth);
        }
        // No: Calculate normally
        else{
            column = (int) (position.getX() / gridWidth);
        }

        double relY = position.getY() - (row * gridHeight);
        double relX;

        if (rowIsOdd){
            relX = (position.getX() - (column * gridWidth)) - halfWidth;
        }
        else{
            relX = position.getX() - (column * gridWidth);
        }

        double c = Globals.EDGE - Globals.EDGE * Math.sin(Math.PI/6);
        double m = c / halfWidth;

        // LEFT edge
        if (relY < (-m * relX) + c){
            row--;
            if (!rowIsOdd)
                column--;
        }
        // RIGHT edge
        else if (relY < (m * relX) - c){
            row--;
            if (rowIsOdd)
                column++;
        }

        return new Position(column, row);
    }

}
