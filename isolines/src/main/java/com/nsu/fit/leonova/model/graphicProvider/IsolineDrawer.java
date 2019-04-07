package com.nsu.fit.leonova.model.graphicProvider;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class IsolineDrawer {
    private double dx;
    private double dy;

    private int k;
    private int m;

    public IsolineDrawer(int k, int m) {
        this.k = k;
        this.m = m;
    }

    private GraphicValues graphicValues;

    public void setGraphicValues(GraphicValues graphicValues) {
        this.graphicValues = graphicValues;
    }

    public void setNet(int k, int m){
        this.k = k;
        this.m = m;
    }

    public void drawIsoline(BufferedImage image, double z) {
        dx = image.getWidth() / (double)k;
        dy = image.getHeight()/ (double)m;
        for(int y = 0; y + dy < image.getHeight(); y += dy){
            for(int x = 0; x + dx < image.getWidth(); x += dx){
                drawLine(image, new DoublePoint(x, y), dx, dy, z);
            }
        }
    }

    private void drawLine(BufferedImage image, DoublePoint point, double dx, double dy, double z){
        DoublePoint leftTop = graphicValues.pixelToGraphicCoord(point, image.getWidth(), image.getHeight());
        DoublePoint pixelRightBottom = new DoublePoint(point.getX() + dx, point.getY() + dy);
        DoublePoint rightBottom = graphicValues.pixelToGraphicCoord(pixelRightBottom, image.getWidth(), image.getHeight());

        double f1 = Function.countValue(leftTop);
        double f2 =Function.countValue(new DoublePoint(rightBottom.getX(), leftTop.getY()));
        double f3 = Function.countValue(new DoublePoint(leftTop.getX(), rightBottom.getY()));
        double f4 = Function.countValue(rightBottom);

        ArrayList<DoublePoint> doublePoints = new ArrayList<>(4);
        if((z > f1 || z > f2) && (z < f1 || z < f2)){
            double p1 = point.getX() + ((z - f1) / (f2 - f1) * dx);
            doublePoints.add(new DoublePoint(p1, point.getY()));
        }
        if((z > f3 || z > f4) && (z < f3 || z < f4)){
            double p1 = point.getX() + ((z - f3) / (f4 - f3) * dx);
            doublePoints.add(new DoublePoint(p1, point.getY() + dy));
        }
        if((z > f1 || z > f3) && (z < f1 || z < f3)){
            double p1 = point.getY() + ((z - f1) / (f3 - f1) * dy);
            doublePoints.add(new DoublePoint(point.getX(), p1));
        }
        if((z > f4 || z > f2) && (z < f4 || z < f2)){
            double p1 = point.getY() + ((z - f2) / (f4 - f2) * dy);
            doublePoints.add(new DoublePoint(point.getX() + dx, p1));
        }

        if(doublePoints.size() < 2){
            return;
        }
        if(doublePoints.size() == 2){
            Graphics2D graphics2D = image.createGraphics();
            int resX1 = (int)Math.round((doublePoints.get(1).getX()));
            int resX0 = (int)Math.round((doublePoints.get(0).getX()));
            int resY0 = (int)Math.round((doublePoints.get(0).getY()));
            int resY1 = (int)Math.round((doublePoints.get(1).getY()));

            graphics2D.drawLine(resX0, resY0, resX1, resY1);
            graphics2D.drawLine(0, 0, 10, 10);
            return;
        }

        if(doublePoints.size() == 3){
            drawLine(image, point, dx, dy, z - 0.00001);
            return;
        }
        if(doublePoints.size() == 4){
            drawLine(image, point, dx/2, dy/2, z);
            drawLine(image,  new DoublePoint(point.getX() + dx/2, point.getY()) , dx/2, dy/2, z);
            drawLine(image, new DoublePoint(point.getX(), point.getY() + dy/2), dx/2, dy/2, z);
            drawLine(image, new DoublePoint(point.getX() + dx/2, point.getY() + dy/2), dx/2, dy/2, z);
            return;
        }

    }
}
