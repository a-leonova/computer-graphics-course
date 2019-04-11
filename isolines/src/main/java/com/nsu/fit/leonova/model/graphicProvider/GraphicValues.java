package com.nsu.fit.leonova.model.graphicProvider;

public final class GraphicValues {
    private double minX, minY, maxX, maxY;

    private double definitionAreaWidth;
    private double definitionAreaHeight;

    public GraphicValues(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;

        definitionAreaWidth = maxX - minX;
        definitionAreaHeight = maxY - minY



















        ;
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getDefinitionAreaWidth() {
        return definitionAreaWidth;
    }

    public double getDefinitionAreaHeight() {
        return definitionAreaHeight;
    }

    public DoublePoint pixelToGraphicCoord(DoublePoint pixel, int imageWidth, int imageHeight){
        double x0 = pixel.getX() / imageWidth * definitionAreaWidth + minX;
        double y0 = pixel.getY() / imageHeight * definitionAreaHeight + minY;
        return new DoublePoint(x0, y0);
    }
}
