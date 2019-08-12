package com.nsu.fit.leonova.model.graphicProvider;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class IsolineDrawer {
    private double dx;
    private double dy;

    private int k;
    private int m;

    private Color color = Color.WHITE;

    public IsolineDrawer(int k, int m) {
        this.k = k;
        this.m = m;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private GraphicValues graphicValues;

    public void setGraphicValues(GraphicValues graphicValues) {
        this.graphicValues = graphicValues;
    }

    public void setNet(int k, int m){
        this.k = k;
        this.m = m;
    }

    public List<Point> drawIsoline(BufferedImage image, double z) {
        dx = image.getWidth() / (double)k;
        dy = image.getHeight()/ (double)m;
        ArrayList<Point> points = new ArrayList<>();
        for(int y = 0; y < image.getHeight(); y += dy){
            for(int x = 0; x < image.getWidth(); x += dx){
                points.addAll(drawLine(image, new DoublePoint(x, y), dx, dy, z));
            }
        }
        return points;
    }

    private ArrayList<Point> drawLine(BufferedImage image, DoublePoint point, double dx, double dy, double z){
        DoublePoint leftTop = graphicValues.pixelToGraphicCoord(point, image.getWidth(), image.getHeight());
        DoublePoint pixelRightBottom = new DoublePoint(point.getX() + dx, point.getY() + dy);
        DoublePoint rightBottom = graphicValues.pixelToGraphicCoord(pixelRightBottom, image.getWidth(), image.getHeight());

        double f1 = Function.countValue(leftTop);
        double f2 =Function.countValue(new DoublePoint(rightBottom.getX(), leftTop.getY()));
        double f3 = Function.countValue(new DoublePoint(leftTop.getX(), rightBottom.getY()));
        double f4 = Function.countValue(rightBottom);

        f1 = f1 == z ? f1 + Math.ulp(f1) : f1;
        f2 = f2 == z ? f2 + Math.ulp(f2) : f2;
        f3 = f3 == z ? f3 + Math.ulp(f3) : f3;
        f4 = f4 == z ? f4 + Math.ulp(f4) : f4;

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

        ArrayList<Point> pivots = new ArrayList<>();
        if(doublePoints.size() == 0){
            return pivots;
        }
        if(doublePoints.size() == 2){
            Graphics2D graphics2D = image.createGraphics();
            graphics2D.setPaint(color);
            int resX1 = (int)Math.round((doublePoints.get(1).getX()));
            int resX0 = (int)Math.round((doublePoints.get(0).getX()));
            int resY0 = (int)Math.round((doublePoints.get(0).getY()));
            int resY1 = (int)Math.round((doublePoints.get(1).getY()));

            graphics2D.drawLine(resX0, resY0, resX1, resY1);
            for(DoublePoint doublePoint : doublePoints){
                pivots.add(new Point((int)Math.floor(doublePoint.getX()), (int)Math.floor(doublePoint.getY())));
            }
            return pivots;
        }
        else if(doublePoints.size() == 4){

            DoublePoint center = new DoublePoint(rightBottom.getX() + dx/2, rightBottom.getY() - dy/2);
            double valueInCenter = Function.countValue(center);

            Graphics2D graphics2D = image.createGraphics();
            graphics2D.setPaint(color);

            int resX0 = (int)Math.round((doublePoints.get(0).getX()));
            int resY0 = (int)Math.round((doublePoints.get(0).getY()));
            int resX3 = (int)Math.round((doublePoints.get(3).getX()));
            int resY3 = (int)Math.round((doublePoints.get(3).getY()));

            int resX1 = (int)Math.round((doublePoints.get(1).getX()));
            int resY1 = (int)Math.round((doublePoints.get(1).getY()));
            int resX2 = (int)Math.round((doublePoints.get(2).getX()));
            int resY2 = (int)Math.round((doublePoints.get(2).getY()));

            if(valueInCenter < z){
                graphics2D.drawLine(resX0, resY0, resX3, resY3);
                graphics2D.drawLine(resX2, resY2, resX1, resY1);
            }
            else{
                graphics2D.drawLine(resX0, resY0, resX2, resY2);
                graphics2D.drawLine(resX3, resY3, resX1, resY1);
            }
            for(DoublePoint doublePoint : doublePoints){
                pivots.add(new Point((int)Math.floor(doublePoint.getX()), (int)Math.floor(doublePoint.getY())));
            }
            return pivots;
        }
        else{
            System.out.println(pivots.size());
        }
        return pivots;
    }
}
