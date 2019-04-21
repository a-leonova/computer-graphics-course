package com.nsu.fit.leonova.model;

import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.observer.Observable;
import com.nsu.fit.leonova.observer.Observer;
import org.ejml.simple.SimpleMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BSplineImpl implements BSpline, Observable {
    private static final int radius = 3;
    private static final double STEP = 100.;
    private static final SimpleMatrix MATRIX = new SimpleMatrix(new double[][]{
            {-1, 3, -3, 1},
            {3, -6, 3, 0},
            {-3, 0, 3, 0},
            {1, 4, 1, 0}
    });
    private static final double COEFFICIENT = 1/6f;
    private List<Point> pivotPoints = new ArrayList<>();
    private BufferedImage bspline;
    private List<Observer> observers = new ArrayList<>();

    public BSplineImpl() {
        addPoint(new Point(20, 100));
        addPoint(new Point(40, 150));
    }

    @Override
    public void addPoint(Point point) {
        pivotPoints.add(point);
        createBSpline();
    }

    @Override
    public void removePoint(Point point) {
        for(Point p : pivotPoints){
            double distance = Math.sqrt(Math.pow(p.x - point.x, 2) + Math.pow(p.y - point.y, 2));
            if(distance <= radius){
                pivotPoints.remove(p);
                break;
            }
        }
        createBSpline();
    }

    private void createBSpline(){
        bspline = new BufferedImage(Globals.BSPLINE_WIDTH, Globals.BSPLINE_HEIGH, BufferedImage.TYPE_3BYTE_BGR);
        drawPivotPoints();
        drawBSpline();
        for(Observer obs : observers){
            obs.setBSpline(bspline);
        }
    }

    private void drawBSpline(){
        int oldX = 0;
        int oldY = 0;
        boolean oldInited = false;
        Graphics2D graphics = bspline.createGraphics();
        graphics.setPaint(Color.MAGENTA);
        for(int i = 1; i < pivotPoints.size() - 2; ++i){
            SimpleMatrix Px = new SimpleMatrix(new double[][]{{pivotPoints.get(i -1).x}, {pivotPoints.get(i).x}, {pivotPoints.get(i + 1).x}, {pivotPoints.get(i + 2).x}});
            SimpleMatrix Py = new SimpleMatrix(new double[][]{{pivotPoints.get(i -1).y}, {pivotPoints.get(i).y}, {pivotPoints.get(i + 1).y}, {pivotPoints.get(i + 2).y}});
//            oldInited = false;
            for(double t = 0.0; t < 1.0; t += 1 / STEP){
                SimpleMatrix T = new SimpleMatrix(new double[][]{{t * t * t, t * t, t, 1}});
                int x = (int)Math.round(T.mult(MATRIX).mult(Px).get(0, 0) * COEFFICIENT);
                int y = (int)Math.round(T.mult(MATRIX).mult(Py).get(0, 0) * COEFFICIENT);
                if(oldInited){
                    graphics.drawLine(oldX, oldY, x, y);
                }
                else {
                    oldInited = true;
                }
                oldX = x;
                oldY = y;
            }
        }
    }

    private void drawPivotPoints(){
        Graphics2D graphics = bspline.createGraphics();
        graphics.setPaint(Color.RED);
        for(int i = 0; i < pivotPoints.size(); ++i){
            Point pivot = pivotPoints.get(i);
            graphics.drawOval(pivot.x - radius, pivot.y - radius, 2 * radius, 2 * radius);
            if(i != pivotPoints.size() - 1){
                Point nextPoint = pivotPoints.get(i + 1);
                graphics.drawLine(pivot.x, pivot.y, nextPoint.x, nextPoint.y);
            }
        }
    }

    @Override
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void deleteObserver(Observer obs) {
        observers.remove(obs);
    }
}
