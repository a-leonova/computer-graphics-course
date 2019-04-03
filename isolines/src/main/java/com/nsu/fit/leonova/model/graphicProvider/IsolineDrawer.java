package com.nsu.fit.leonova.model.graphicProvider;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class IsolineDrawer extends GraphicProvider {
    private double dx;
    private double dy;

    private int k = 100;
    private int m = 100;

    public void setWidthInSquares(int widthInSquares) {
        k = widthInSquares;
    }

    public void setHeightInSquares(int heightInSquares) {
        m = heightInSquares;
    }

    public void drawIsoline(BufferedImage image, double z) {
        dx = image.getWidth() / (double)k;
        dy = image.getHeight()/ (double)m;
        for(int y = 0; y + dy < image.getHeight(); y += dy){
            for(int x = 0; x + dx < image.getWidth(); x += dx){
                drawLine(image, x, y, dx, dy, z);
            }
        }
    }

    public void drawOneIsoline(BufferedImage image, java.awt.Point pressedPixel){
        dx = image.getWidth() / (double)k;
        dy = image.getHeight()/ (double)m;

        double x0 = pressedPixel.getX() / (double)image.getWidth() * definitionAreaWidth + minX;
        double y0 = pressedPixel.getY() / (double)image.getHeight() * definitionAreaHeight + minY;
        double z = Function.countValue(x0, y0);

        for(int y = 0; y + dy < image.getHeight(); y += dy){
            for(int x = 0; x + dx < image.getWidth(); x += dx){
                drawLine(image, x, y, dx, dy, z);
            }
        }

    }

    private void drawLine(BufferedImage image, double x, double y, double dx, double dy, double z){
        double y0 = y / (double)image.getHeight() * definitionAreaHeight + minY;
        double x0 = x / (double)image.getWidth() * definitionAreaWidth + minX;
        double x1 = (x + dx) / image.getWidth() * definitionAreaWidth + minX;
        double y1 = (y + dy) / image.getHeight() * definitionAreaHeight + minY;

        double f1 = Function.countValue(x0, y0);
        double f2 =Function.countValue(x1, y0);
        double f3 = Function.countValue(x0, y1);
        double f4 = Function.countValue(x1,y1);

        ArrayList<Point> points = new ArrayList<>(4);
        if((z > f1 || z > f2) && (z < f1 || z < f2)){
            double p1 = x + ((z - f1) / (f2 - f1) * dx);
            points.add(new Point(p1, y));
        }
        if((z > f3 || z > f4) && (z < f3 || z < f4)){
            double p1 = x + ((z - f3) / (f4 - f3) * dx);
            points.add(new Point(p1, y + dy));
        }
        if((z > f1 || z > f3) && (z < f1 || z < f3)){
            double p1 = y + ((z - f1) / (f3 - f1) * dy);
            points.add(new Point(x, p1));
        }
        if((z > f4 || z > f2) && (z < f4 || z < f2)){
            double p1 = y + ((z - f2) / (f4 - f2) * dy);
            points.add(new Point(x + dx, p1));
        }

        if(points.size() < 2){
            return;
        }
        if(points.size() == 2){
            Graphics2D graphics2D = image.createGraphics();
            int resX1 = (int)Math.round((points.get(1).getX()));
            int resX0 = (int)Math.round((points.get(0).getX()));
            int resY0 = (int)Math.round((points.get(0).getY()));
            int resY1 = (int)Math.round((points.get(1).getY()));

            graphics2D.drawLine(resX0, resY0, resX1, resY1);
            graphics2D.drawLine(0, 0, 10, 10);
            return;
        }

        if(points.size() == 3){
            drawLine(image, x, y, dx, dy, z - 0.00001);
            return;
        }
        if(points.size() == 4){
            drawLine(image, x, y, dx/2, dy/2, z);
            drawLine(image, x + dx/2, y, dx/2, dy/2, z);
            drawLine(image, x, y + dy/2, dx/2, dy/2, z);
            drawLine(image, x + dx/2, y + dy/2, dx/2, dy/2, z);
            return;
        }

    }
}
