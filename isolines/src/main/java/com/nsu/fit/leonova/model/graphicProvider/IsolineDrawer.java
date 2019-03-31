package com.nsu.fit.leonova.model.graphicProvider;

import java.awt.image.BufferedImage;

public class IsolineDrawer extends GraphicProvider {

    private double dx;
    private double dy;

    private int k;
    private int m;

    public void setWidthInSquares(int widthInSquares) {
        k = widthInSquares;
    }

    public void setHeightInSquares(int heightInSquares) {
        m = heightInSquares;
    }

    public BufferedImage drawIsoline(BufferedImage image) {
        dx = image.getWidth() / (double)k;
        dy = image.getHeight() / (double)m;
        for(int y = 0; y + dy < image.getHeight(); ++y){
            for(int x = 0; x + dx < image.getWidth(); ++x){
                double y0 = y / (double)image.getHeight() * definitionAreaHeight + minY;
                double x0 = x / (double)image.getWidth() * definitionAreaWidth + minX;
                double x1 = (x + dx) / image.getWidth() * definitionAreaWidth + minX;
                double y1 = (y + dy) / image.getHeight() * definitionAreaHeight + minY;

                double f1 = Function.countValue(x0, y0);
                double f2 =Function.countValue(x1, y0);
                double f3 = Function.countValue(x0, y1);
                double f4 = Function.countValue(x1,y1);


            }
        }
        return null;
    }
}
