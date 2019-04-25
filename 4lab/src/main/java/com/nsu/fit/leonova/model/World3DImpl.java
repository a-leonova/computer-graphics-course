package com.nsu.fit.leonova.model;

import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.model.bspline.BSplineCreator;
import com.nsu.fit.leonova.observer.Observable;
import com.nsu.fit.leonova.observer.Observer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class World3DImpl implements World3D, BSplineCreator, Observable {
    private List<Observer> observers = new ArrayList<>();
    private List<Figure> figures = new ArrayList<>();
    private Figure currentWorkingFigure;

    @Override
    public void showSpline3D() {
        BufferedImage image = new BufferedImage(Globals.IMAGE_WIDTH, Globals.IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = image.createGraphics();

        for(Figure figure : figures){
            Point[][] splinePoints2D = figure.getSplinePoints2D();
            graphics.setPaint(figure.getParameters().getColor());

            for(int i = 1; i < figure.getPointsToRotateCount(); ++i){
                for(int j = 0; j < figure.getParameters().getM(); j++){
                    graphics.drawLine(splinePoints2D[i - 1][j].x, splinePoints2D[i - 1][j].y, splinePoints2D[i][j].x, splinePoints2D[i][j].y);
                }
            }

            for(int i = 0; i < figure.getPointsToRotateCount(); i += Globals.K){
                for(int j = 1; j < figure.getParameters().getM(); j++){
                    graphics.drawLine(splinePoints2D[i][j - 1].x, splinePoints2D[i][j - 1].y, splinePoints2D[i][j].x, splinePoints2D[i][j].y);
                }
            }
        }

        for(Observer observer : observers){
            observer.setMainImage(image);
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
    public void addObserver(Observer obs) {
        observers.add(obs);
        for(Figure figure : figures){
            figure.addObserver(obs);
        }
    }

    @Override
    public void deleteObserver(Observer obs) {
        observers.remove(obs);
        for(Figure figure : figures){
            figure.deleteObserver(obs);
        }
    }

    @Override
    public void addPoint(Point point) {
        currentWorkingFigure.addPoint(point);
    }

    @Override
    public void removePoint(Point point) {
        currentWorkingFigure.removePoint(point);
    }

    @Override
    public void pressedPoint(Point point) {
        currentWorkingFigure.pressedPoint(point);
    }

    @Override
    public void draggedPoint(Point point) {
        currentWorkingFigure.draggedPoint(point);
    }
}
