package com.nsu.fit.leonova.model.bspline;

import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.observer.BSplineObservable;
import com.nsu.fit.leonova.observer.BSplineObserver;
import org.ejml.simple.SimpleMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BSpline implements BSplineObservable {
    private static final int radius = 3;
    private static final double STEP = 100.;
    private static final SimpleMatrix SPLINE_MATRIX = new SimpleMatrix(new double[][]{
            {-1, 3, -3, 1},
            {3, -6, 3, 0},
            {-3, 0, 3, 0},
            {1, 4, 1, 0}
    });
    private static final double SPLINE_MATRIX_COEFFICIENT = 1/6f;

    private List<Point> pivotPoints = new ArrayList<>();
    private SplineParameters parameters;
    private BufferedImage bspline;
    private double splineLength = 0.0;
    private List<BSplineObserver> observers = new ArrayList<>();
    private Point pressedPoint;

    private List<Point> pointsToRotate = new ArrayList<>();
    private boolean correctPointsToRotate = false;

    public BSpline(SplineParameters parameters) {
        this.parameters = parameters;
        addPoint(new Point(20, 100));
        addPoint(new Point(40, 150));
        addPoint(new Point(60, 170));
        addPoint(new Point(100, 170));
    }

    public void setParameters(SplineParameters parameters) {
        this.parameters = parameters;
        correctPointsToRotate = false;
        pointsToRotate.clear();
        createBSpline();
    }

    @Override
    public void addObserver(BSplineObserver obs) {
        observers.add(obs);
    }

    @Override
    public void removeObserver(BSplineObserver obs) {
        observers.remove(obs);
    }

    public void addPoint(Point point) {
        pivotPoints.add(point);
        correctPointsToRotate = false;
        pointsToRotate.clear();
        createBSpline();
    }

    public void removePoint(Point point) {
        for(Point p : pivotPoints){
            double distance = Math.sqrt(Math.pow(p.x - point.x, 2) + Math.pow(p.y - point.y, 2));
            if(distance <= radius){
                correctPointsToRotate = false;
                pointsToRotate.clear();
                pivotPoints.remove(p);
                break;
            }
        }
        createBSpline();
    }

    public void pressedPoint(Point point) {
        for(Point p : pivotPoints){
            double distance = Math.sqrt(Math.pow(p.x - point.x, 2) + Math.pow(p.y - point.y, 2));
            if(distance <= radius){
                pressedPoint = p;
                return;
            }
        }
        pressedPoint = null;
    }

    public void draggedPoint(Point point) {
        if(pressedPoint != null){
            pressedPoint.x = point.x;
            pressedPoint.y = point.y;
            correctPointsToRotate = false;
            pointsToRotate.clear();
            createBSpline();
        }
    }

    public List<Point> getPointsToRotate(){
        if(!correctPointsToRotate){
            pointsToRotate.clear();
            countPointsToRotate();
        }
        return pointsToRotate;
    }

    public void showBspline() {
        createBSpline();
        for (BSplineObserver observer : observers){
            observer.setBSplineParameters(parameters);
        }
    }

    private void countPointsToRotate(){
        double currentLength = 0.0;
        int oldX = 0;
        int oldY = 0;
        int lastIdx = 0;
        double step = (splineLength * (parameters.getB() - parameters.getA())) / (parameters.getN() * parameters.getK() - 1);
        boolean oldInited = false;
        Graphics2D graphics = bspline.createGraphics();
        graphics.setPaint(Color.GREEN);
        for(int i = 1; i < pivotPoints.size() - 2; ++i){
            SimpleMatrix Px = new SimpleMatrix(new double[][]{{pivotPoints.get(i -1).x}, {pivotPoints.get(i).x}, {pivotPoints.get(i + 1).x}, {pivotPoints.get(i + 2).x}});
            SimpleMatrix Py = new SimpleMatrix(new double[][]{{pivotPoints.get(i -1).y}, {pivotPoints.get(i).y}, {pivotPoints.get(i + 1).y}, {pivotPoints.get(i + 2).y}});
            for(double t = 0.0; t < 1.0; t += 1.0 / STEP){
                SimpleMatrix T = new SimpleMatrix(new double[][]{{t * t * t, t * t, t, 1}});
                int x = (int)Math.round(T.mult(SPLINE_MATRIX).mult(Px).get(0, 0) * SPLINE_MATRIX_COEFFICIENT);
                int y = (int)Math.round(T.mult(SPLINE_MATRIX).mult(Py).get(0, 0) * SPLINE_MATRIX_COEFFICIENT);
                if(oldInited){
                    currentLength += Math.sqrt(Math.pow(oldX - x, 2) + Math.pow(oldY - y, 2));
                        if(currentLength >= (parameters.getA() * splineLength + lastIdx * step) && lastIdx < parameters.getN() * parameters.getK()){
                            pointsToRotate.add(new Point(x, y));
                            ++lastIdx;
                            graphics.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
                        }
                    }
                else {
                    oldInited = true;
                }
                oldX = x;
                oldY = y;
            }
        }
//        pointsToRotate.add(new Point(oldX, oldY));
//        graphics.drawOval(oldX - radius, oldY - radius, 2 * radius, 2 * radius);
        correctPointsToRotate = true;
    }

    private void createBSpline(){
        bspline = new BufferedImage(Globals.BSPLINE_WIDTH, Globals.BSPLINE_HEIGH, BufferedImage.TYPE_3BYTE_BGR);
        drawPivotPoints();
        drawBSpline();
        countPointsToRotate();
        for(BSplineObserver obs : observers){
            obs.setBSpline(bspline);
        }
    }

    private void drawBSpline(){
        int oldX = 0;
        int oldY = 0;
        splineLength = 0.0;
        boolean oldInited = false;
        Graphics2D graphics = bspline.createGraphics();
        graphics.setPaint(Color.MAGENTA);
        for(int i = 1; i < pivotPoints.size() - 2; ++i){
            SimpleMatrix Px = new SimpleMatrix(new double[][]{{pivotPoints.get(i -1).x}, {pivotPoints.get(i).x}, {pivotPoints.get(i + 1).x}, {pivotPoints.get(i + 2).x}});
            SimpleMatrix Py = new SimpleMatrix(new double[][]{{pivotPoints.get(i -1).y}, {pivotPoints.get(i).y}, {pivotPoints.get(i + 1).y}, {pivotPoints.get(i + 2).y}});
            for(double t = 0.0; t < 1.0; t += 1 / STEP){
                SimpleMatrix T = new SimpleMatrix(new double[][]{{t * t * t, t * t, t, 1}});
                int x = (int)Math.round(T.mult(SPLINE_MATRIX).mult(Px).get(0, 0) * SPLINE_MATRIX_COEFFICIENT);
                int y = (int)Math.round(T.mult(SPLINE_MATRIX).mult(Py).get(0, 0) * SPLINE_MATRIX_COEFFICIENT);
                if(oldInited){
                    graphics.drawLine(oldX, oldY, x, y);
                    splineLength += Math.sqrt(Math.pow(oldX - x, 2) + Math.pow(oldY - y, 2));
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
}
