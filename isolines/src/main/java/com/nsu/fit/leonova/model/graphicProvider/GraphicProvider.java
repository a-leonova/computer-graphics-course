package com.nsu.fit.leonova.model.graphicProvider;

public class GraphicProvider {
    protected double minX, minY, maxX, maxY;

    protected double definitionAreaWidth;
    protected double definitionAreaHeight;

    public void setDefinitionArea(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;

        definitionAreaWidth = Math.abs(minX) + Math.abs(maxX);
        definitionAreaHeight = Math.abs(minY) + Math.abs(maxY);
    }


}
