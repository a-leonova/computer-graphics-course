package com.nsu.fit.leonova.model.bspline;

import com.nsu.fit.leonova.model.Point2D;
import com.nsu.fit.leonova.observer.BSplineObservable;
import com.nsu.fit.leonova.observer.BSplineObserver;
import org.ejml.simple.SimpleMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BSpline implements BSplineObservable {
    private static final int WIDTH = 500;
    private static final int HEIGH = 200;
    private static final int RADIUS = 3;
    private static final double STEP = 100.;
    private static final SimpleMatrix SPLINE_MATRIX = new SimpleMatrix(new double[][]{
            {-1, 3, -3, 1},
            {3, -6, 3, 0},
            {-3, 0, 3, 0},
            {1, 4, 1, 0}
    });
    private static final double SPLINE_MATRIX_COEFFICIENT = 1/6f;

    private double scale = 1.0;
    private double scaledRadius = RADIUS * scale;

    private List<Point2D> pivotPoints = new ArrayList<>();
    private SplineParameters parameters;
    private BufferedImage bspline;
    private double splineLength = 0.0;
    private List<BSplineObserver> observers = new ArrayList<>();
    private Point2D pressedPoint;

    private List<Point2D> pointsToRotate = new ArrayList<>();
    private boolean correctPointsToRotate = false;

    public BSpline(List<Point> pivots, SplineParameters parameters){
        this.parameters = parameters;
        for(Point p : pivots){
            pivotPoints.add(intPointToDoublePoint(p));
        }
        createBSpline();
    }

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
        createBSpline();
    }

    public void changeScale(double ds){
        scale += ds;
        scaledRadius = RADIUS * scale;
        correctPointsToRotate = false;
        showBspline();
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
        pivotPoints.add(intPointToDoublePoint(point));
        correctPointsToRotate = false;
        createBSpline();
    }

    public void removePoint(Point point) {
        Point2D point2D = intPointToDoublePoint(point);
        for(Point2D p : pivotPoints){
            double distance = Math.sqrt(Math.pow(p.getX() - point2D.getX(), 2) + Math.pow(p.getY() - point2D.getY(), 2));
            if(distance <= scaledRadius){
                correctPointsToRotate = false;
                pivotPoints.remove(p);
                break;
            }
        }
        createBSpline();
    }

    public void pressedPoint(Point point) {
        Point2D point2D = intPointToDoublePoint(point);
        for(Point2D p : pivotPoints){
            double distance = Math.sqrt(Math.pow(p.getX() - point2D.getX(), 2) + Math.pow(p.getY() - point2D.getY(), 2));
            if(distance <= scaledRadius){
                pressedPoint = p;
                return;
            }
        }
        pressedPoint = null;
    }

    public void draggedPoint(Point point) {
        Point2D np = intPointToDoublePoint(point);
        if(pressedPoint != null){
            pressedPoint.setX(np.getX());
            pressedPoint.setY(np.getY());
            correctPointsToRotate = false;
            createBSpline();
        }
    }

    public List<Point2D> getPointsToRotate(){
        if(!correctPointsToRotate){
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
        pointsToRotate.clear();
        double currentLength = 0.0;
        Point oldPoint = null;
        int lastIdx = 0;
        double step = (splineLength * (parameters.getB() - parameters.getA())) / (parameters.getN() * parameters.getK());
        Graphics2D graphics = bspline.createGraphics();
        graphics.setPaint(Color.GREEN);
        boolean stopOuterLoop = false;
        for(int i = 1; i < pivotPoints.size() - 2; ++i){
            SimpleMatrix Px = new SimpleMatrix(new double[][]{{pivotPoints.get(i -1).getX()}, {pivotPoints.get(i).getX()}, {pivotPoints.get(i + 1).getX()}, {pivotPoints.get(i + 2).getX()}});
            SimpleMatrix Py = new SimpleMatrix(new double[][]{{pivotPoints.get(i -1).getY()}, {pivotPoints.get(i).getY()}, {pivotPoints.get(i + 1).getY()}, {pivotPoints.get(i + 2).getY()}});
            for(double t = 0.0; t < 1.0; t += 1.0 / STEP){
                SimpleMatrix T = new SimpleMatrix(new double[][]{{t * t * t, t * t, t, 1}});
                double x = T.mult(SPLINE_MATRIX).mult(Px).get(0, 0) * SPLINE_MATRIX_COEFFICIENT;
                double y = T.mult(SPLINE_MATRIX).mult(Py).get(0, 0) * SPLINE_MATRIX_COEFFICIENT;
                Point newPoint = doublePointToIntPoint(new Point2D(x, y));
                if(oldPoint != null){
                    currentLength += Math.sqrt(Math.pow(oldPoint.x - newPoint.x, 2) + Math.pow(oldPoint.y - newPoint.y, 2));
                        if(currentLength >= (parameters.getA() * splineLength + lastIdx * step) && lastIdx < parameters.getN() * parameters.getK()){
                            pointsToRotate.add(new Point2D(x, y));
                            ++lastIdx;
                            graphics.drawOval(newPoint.x - RADIUS, newPoint.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
                        }
                    }
                oldPoint = newPoint;
                if(currentLength >= (parameters.getA() * splineLength + lastIdx * step) && lastIdx >= parameters.getN() * parameters.getK()){
                    stopOuterLoop = true;
                    break;
                }
            }
            if(stopOuterLoop){
                break;
            }
        }
        if(oldPoint != null){
            pointsToRotate.add(intPointToDoublePoint(oldPoint));
            graphics.drawOval(oldPoint.x - RADIUS, oldPoint.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
        }
        correctPointsToRotate = true;
    }

    private void createBSpline(){
        bspline = new BufferedImage(WIDTH, HEIGH, BufferedImage.TYPE_3BYTE_BGR);
        drawAxis();
        drawPivotPoints();
        drawBSpline();
        countPointsToRotate();
        for(BSplineObserver obs : observers){
            obs.setBSpline(bspline);
        }
    }

    private void drawBSpline(){
        Point oldPoint = null;
        splineLength = 0.0;
        Graphics2D graphics = bspline.createGraphics();
        graphics.setPaint(Color.MAGENTA);
        for(int i = 1; i < pivotPoints.size() - 2; ++i){
            SimpleMatrix Px = new SimpleMatrix(new double[][]{{pivotPoints.get(i -1).getX()}, {pivotPoints.get(i).getX()}, {pivotPoints.get(i + 1).getX()}, {pivotPoints.get(i + 2).getX()}});
            SimpleMatrix Py = new SimpleMatrix(new double[][]{{pivotPoints.get(i -1).getY()}, {pivotPoints.get(i).getY()}, {pivotPoints.get(i + 1).getY()}, {pivotPoints.get(i + 2).getY()}});
            for(double t = 0.0; t < 1.0; t += 1 / STEP){
                SimpleMatrix T = new SimpleMatrix(new double[][]{{t * t * t, t * t, t, 1}});
                double x = T.mult(SPLINE_MATRIX).mult(Px).get(0, 0) * SPLINE_MATRIX_COEFFICIENT;
                double y = T.mult(SPLINE_MATRIX).mult(Py).get(0, 0) * SPLINE_MATRIX_COEFFICIENT;
                Point newPoint = doublePointToIntPoint(new Point2D(x, y));
                if(oldPoint != null){
                    graphics.drawLine(oldPoint.x, oldPoint.y, newPoint.x, newPoint.y);
                    splineLength += Math.sqrt(Math.pow(oldPoint.x - newPoint.x, 2) + Math.pow(oldPoint.y - newPoint.y, 2));
                }
                oldPoint = newPoint;
            }
        }
    }

    private void drawPivotPoints(){
        Graphics2D graphics = bspline.createGraphics();
        graphics.setPaint(Color.WHITE);
        for(int i = 0; i < pivotPoints.size(); ++i){
            Point pivot = doublePointToIntPoint(pivotPoints.get(i));
            graphics.drawOval(pivot.x - RADIUS, pivot.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
            if(i != pivotPoints.size() - 1){
                Point nextPoint = doublePointToIntPoint(pivotPoints.get(i + 1));
                graphics.drawLine(pivot.x, pivot.y, nextPoint.x, nextPoint.y);
            }
        }
    }

    private void drawAxis(){
        Graphics2D graphics = bspline.createGraphics();
        graphics.setPaint(Color.RED);
        graphics.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGH);
        graphics.setPaint(Color.BLUE);
        graphics.drawLine(0, HEIGH/2, WIDTH, HEIGH/2);
    }

    private Point2D intPointToDoublePoint(Point point){
        double x = (point.x - WIDTH/2.0) * scale;
        double y = (HEIGH / 2.0 - point.y) * scale;
        return new Point2D(x, y);
    }

    private Point doublePointToIntPoint(Point2D point2D){
        int x = (int)Math.round(point2D.getX() / scale + WIDTH / 2.0);
        int y = (int)Math.round(-point2D.getY() / scale + HEIGH / 2.0);
        return new Point(x, y);
    }
}
