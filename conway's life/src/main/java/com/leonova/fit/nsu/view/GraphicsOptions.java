package com.leonova.fit.nsu.view;

public class GraphicsOptions {

    private int rows = 10;
    private int columns = 10;

    private int lineWidth = 1;
    private int cellEdge = 10;
    private boolean showImpact = false;

    public GraphicsOptions(){

    }

    public GraphicsOptions(int rows, int columns, int lineWidth, int cellEdge) {
        this.rows = rows;
        this.columns = columns;
        this.lineWidth = lineWidth;
        this.cellEdge = cellEdge;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
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

    public boolean isShowImpact() {
        return showImpact;
    }

    public void setShowImpact(boolean showImpact) {
        this.showImpact = showImpact;
    }
}
