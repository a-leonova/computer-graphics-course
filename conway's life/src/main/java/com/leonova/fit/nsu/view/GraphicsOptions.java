package com.leonova.fit.nsu.view;

public class GraphicsOptions {

    private int cellsInRow = 10;
    private int cellsInColumn = 10;

    private int lineWidth = 1;
    private int cellEdge = 10;

    public GraphicsOptions(){

    }

    public GraphicsOptions(int cellsInRow, int cellsInColumn, int lineWidth, int cellEdge) {
        this.cellsInRow = cellsInRow;
        this.cellsInColumn = cellsInColumn;
        this.lineWidth = lineWidth;
        this.cellEdge = cellEdge;
    }

    public int getCellsInRow() {
        return cellsInRow;
    }

    public void setCellsInRow(int cellsInRow) {
        this.cellsInRow = cellsInRow;
    }

    public int getCellsInColumn() {
        return cellsInColumn;
    }

    public void setCellsInColumn(int cellsInColumn) {
        this.cellsInColumn = cellsInColumn;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public int getCellEdge() {
        return cellEdge;
    }

    public void setCellEdge(int cellEdge) {
        this.cellEdge = cellEdge;
    }
}
