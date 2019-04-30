package com.nsu.fit.leonova.model;

import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.model.bspline.SplineParameters;
import com.nsu.fit.leonova.observer.BSplineObservable;
import com.nsu.fit.leonova.observer.BSplineObserver;
import com.nsu.fit.leonova.observer.WorldObservable;
import com.nsu.fit.leonova.observer.WorldObserver;
import org.ejml.simple.SimpleMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class World3DImpl implements World3D, WorldObservable, BSplineObservable {
    private List<WorldObserver> worldObservers = new ArrayList<>();
    private List<BSplineObserver> bSplineObservers = new ArrayList<>();
    private List<Figure> figures = new ArrayList<>();
    private Figure currentWorkingFigure;

    private WorldParameters worldParameters = new WorldParameters();

    @Override
    public void showSpline3D() {
        BufferedImage image = new BufferedImage(Globals.IMAGE_WIDTH, Globals.IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = image.createGraphics();

        for(Figure figure : figures){
            Point3D[][] transformedPoints3D = figure.getTransformedPoints3D();

            List<ConnectedPoints2D> connectedPoints2D =  clipAndPerspective(transformedPoints3D, figure.getParameters().getM(),
                    figure.getParameters().getK(), figure.getPointsToRotateCount());

            graphics.setPaint(figure.getParameters().getColor());

//            for(int i = 1; i < figure.getPointsToRotateCount(); ++i){
//                for(int j = 0; j < figure.getParameters().getM(); j++){
//                    try{
//                      //  graphics.drawLine(connectedPoints2D[i - 1][j].x, transformedPoints3D[i - 1][j].y, transformedPoints3D[i][j].x, transformedPoints3D[i][j].y);
//                        graphics.drawLine(connectedPoints2D.);
//                    }
//                    catch (ArrayIndexOutOfBoundsException e){
//                        System.out.println("!!!");
//                        throw e;
//                    }
//                }
//            }
//
//            for(int i = 0; i < figure.getPointsToRotateCount(); i += figure.getParameters().getK()){
//                for(int j = 1; j < figure.getParameters().getM(); j++){
//                    if(transformedPoints3D[i][j - 1] != null && transformedPoints3D[i][j] != null){
//                        graphics.drawLine(transformedPoints3D[i][j - 1].x, transformedPoints3D[i][j - 1].y, transformedPoints3D[i][j].x, transformedPoints3D[i][j].y);
//                    }
//                }
//            }
            for(ConnectedPoints2D pair : connectedPoints2D){
                graphics.drawLine(pair.getA().x, pair.getA().y, pair.getB().x, pair.getB().y);
            }
        }

        for(WorldObserver worldObserver : worldObservers){
            worldObserver.setMainImage(image);
        }
    }

    @Override
    public void rotationForOX(int shift) {
        double d = Math.toRadians(shift * 0.5);
        currentWorkingFigure.rotateForOX(d);
        showSpline3D();
    }

    @Override
    public void rotationForOY(int shift) {
        double d = Math.toRadians(shift * 0.5);
        currentWorkingFigure.rotateForOY(d);
        showSpline3D();
    }

    @Override
    public void addPointToCurrentBSpline(Point point) {
        currentWorkingFigure.addPoint(point);
    }

    @Override
    public void removePointFromCurrentBSpline(Point point) {
        currentWorkingFigure.removePoint(point);
    }

    @Override
    public void pressedPointOnCurrentBSpline(Point point) {
        currentWorkingFigure.pressedPoint(point);
    }

    @Override
    public void draggedPointOnCurrentBSpline(Point point) {
        currentWorkingFigure.draggedPoint(point);
    }

    @Override
    public void showBSplineInfo(int index) {
        if (figures.size() <= index){
            throw new IllegalArgumentException("Impossible! Size: " + figures.size() + " Index: " + index);
        }
        currentWorkingFigure = figures.get(index);
        figures.get(index).showBspline();
    }

    @Override
    public void addSpline() {
        Figure figure = new Figure(bSplineObservers, figures.size());
        figures.add(figure);
        figure.showBspline();
        currentWorkingFigure = figure;
        for(BSplineObserver obs : bSplineObservers){
            obs.addSpline(figure.getParameters().getSplineName());
        }
        for(WorldObserver obs : worldObservers){
            obs.addFigure(figure.getParameters().getSplineName());
        }
    }

    @Override
    public void removeSpline(int index) {
        figures.remove(index);
        for(BSplineObserver obs : bSplineObservers){
            obs.removeSpline(index);
        }
        for(WorldObserver obs : worldObservers){
            obs.removeFigure(index);
        }
        showBSplineInfo(figures.size() - 1);
    }

    @Override
    public void setSplineParameters(SplineParameters parameters) {
        currentWorkingFigure.setBsplineParameters(parameters);
        for(BSplineObserver obs : bSplineObservers){
            obs.changeFigureName(parameters.getSplineName(), figures.indexOf(currentWorkingFigure));
        }
        for(WorldObserver obs : worldObservers){
            obs.renameFigure(parameters.getSplineName(), figures.indexOf(currentWorkingFigure));
        }
    }

    @Override
    public void setFigureCenter(Point3D figureCenter) {
        currentWorkingFigure.shift(figureCenter);
        showSpline3D();
    }

    @Override
    public void setSelectedFigure(int index) {
        currentWorkingFigure = figures.get(index);
        Point3D center = new Point3D(currentWorkingFigure.getShiftMatrix().get(0, 3),
                currentWorkingFigure.getShiftMatrix().get(1, 3),
                currentWorkingFigure.getShiftMatrix().get(2, 3));
        for(WorldObserver obs : worldObservers){
            obs.setInfo(center);
        }
    }

    @Override
    public void setWorldParameters(WorldParameters wp) {
        worldParameters = wp;
//        for(Figure figure : figures){
//            /figure.setWorldParameters(wp);
//        }
        showSpline3D();
    }

    @Override
    public void scale(double ds) {
        for(Figure figure : figures){
            figure.changeScale(ds);
        }
    }

    @Override
    public void addObserver(WorldObserver obs) {
        worldObservers.add(obs);
    }

    @Override
    public void removeObserver(WorldObserver obs) {
        worldObservers.remove(obs);
    }

    @Override
    public void addObserver(BSplineObserver obs) {
        bSplineObservers.add(obs);
        for(Figure figure : figures){
            figure.addObserver(obs);
        }
    }

    @Override
    public void removeObserver(BSplineObserver obs) {
        bSplineObservers.remove(obs);
        for(Figure figure : figures){
            figure.removeObserver(obs);
        }
    }

    private List<ConnectedPoints2D> clipAndPerspective(Point3D[][] points, int m, int k, int pointToRotateSize){
        List<ConnectedPoints2D> connectedPoints2D = new ArrayList<>();
        List<ConnectedPoints3D> connectedPoints3D = new ArrayList<>();

        for(int i = 1; i < pointToRotateSize; ++i){
            for(int j = 0; j < m; j++){
                connectedPoints3D.add(new ConnectedPoints3D(points[i - 1][j], points[i][j]));
            }
        }

        for(int i = 0; i < pointToRotateSize; i += k){
            for(int j = 1; j < m; j++){
                connectedPoints3D.add(new ConnectedPoints3D(points[i][j - 1], points[i][j]));
            }
        }


        Iterator<ConnectedPoints3D> it = connectedPoints3D.iterator();
        while(it.hasNext()){
            ConnectedPoints3D c = it.next();
            boolean behindZfA = c.getA().getZ() > worldParameters.getZf();
            boolean behindZfB = c.getB().getZ() > worldParameters.getZf();
            if(behindZfA && !behindZfB){
                double dz = c.getB().getZ() - c.getA().getZ();
                double coefficient = (worldParameters.getZf() - c.getA().getZ())/dz;
                double newX = (c.getA().getX() + coefficient * (c.getB().getX() - c.getA().getX()));
                double newY = (c.getA().getY() + coefficient * (c.getB().getY() - c.getA().getY()));
                c.setB(new Point3D(newX, newY, worldParameters.getZf()));
            }
            if(!behindZfA && behindZfB){
                double dz = c.getA().getZ() - c.getB().getZ();
                double coefficient = (worldParameters.getZf() - c.getB().getZ())/dz;
                double newX = (c.getB().getX() + coefficient * (c.getA().getX() - c.getB().getX()));
                double newY = (c.getB().getY() + coefficient * (c.getA().getY() - c.getB().getY()));
                c.setA(new Point3D(newX, newY, worldParameters.getZf()));
            }
            if(!behindZfA && !behindZfB){
                it.remove();
            }
        }


        SimpleMatrix perspectiveMtx = MatrixGenerator.projectionMatrix(worldParameters.getZf());
        for(ConnectedPoints3D c : connectedPoints3D){
            SimpleMatrix coordinatesA = new SimpleMatrix(new double[][] {{c.getA().getX()},
                    {c.getA().getY()},
                    {c.getA().getZ()},
                    {1}});
            SimpleMatrix coordinatesB = new SimpleMatrix(new double[][] {{c.getB().getX()},
                    {c.getB().getY()},
                    {c.getB().getZ()},
                    {1}});
            coordinatesA = perspectiveMtx.mult(coordinatesA);
            coordinatesB = perspectiveMtx.mult(coordinatesB);
            coordinatesA = coordinatesA.divide(coordinatesA.get(3, 0));
            coordinatesB = coordinatesB.divide(coordinatesB.get(3, 0));

            connectedPoints2D.add(
                    new ConnectedPoints2D(
                            new Point((int)Math.round(coordinatesA.get(0, 0)) + Globals.IMAGE_WIDTH/2, (int)Math.round(coordinatesA.get(1,0)) + Globals.IMAGE_HEIGHT/2),
                            new Point((int)Math.round(coordinatesB.get(0,0)) + Globals.IMAGE_WIDTH/2, (int)Math.round(coordinatesB.get(1, 0)) + Globals.IMAGE_HEIGHT/2)));
        }
        return connectedPoints2D;
    }
}
