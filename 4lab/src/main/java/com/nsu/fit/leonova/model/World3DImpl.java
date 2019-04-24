package com.nsu.fit.leonova.model;

import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.observer.Observable;
import com.nsu.fit.leonova.observer.Observer;
import org.ejml.simple.SimpleMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class World3DImpl implements World3D, Observable {
    private BSplineProvider bSplineProvider;
    private List<Observer> observers = new ArrayList<>();

    private double rotationAngleRadOX = 0.0;
    private double rotationAngleRadOY = 0.0;
    private double rotationAngleRadOZ = 0.0;

    public void setbSplineProvider(BSplineProvider bSplineProvider) {
        this.bSplineProvider = bSplineProvider;
    }

    @Override
    public void showSpline3D() {
        BufferedImage image = new BufferedImage(Globals.IMAGE_WIDTH, Globals.IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = image.createGraphics();
        List<Point> points = bSplineProvider.getPointsToRotate();
        Point allPoints[][] = new Point[points.size()][Globals.M];
        double step = 2 * Math.PI / Globals.M;
        for(int k = 0; k < points.size(); ++k){
            for(int v = 0; v < Globals.M; v++){
                Point p = points.get(k);
                int z = 0;
                SimpleMatrix vectorBeg = new SimpleMatrix(new double[][]{{p.x, p.y, z}});
                SimpleMatrix rotationMtx = MatrixGenerator.generateMatrixForRotationAroundOX(v * step);
                SimpleMatrix result = vectorBeg.mult(rotationMtx);
//                int x = (int)Math.round(points.get(k).y * Math.cos(v * step));
//                int z = (int)Math.round(points.get(k).y * Math.sin(v * step));
//                int y = points.get(k).x;
                allPoints[k][v] = new Point((int)Math.round(result.get(0,0)) + image.getWidth() / 2 , (int)Math.round(result.get(0, 1)) + image.getHeight()/2);
            }
        }

        for(int i = 1; i < points.size(); ++i){
            for(int j = 0; j < Globals.M; j++){
                graphics.drawLine(allPoints[i - 1][j].x, allPoints[i - 1][j].y, allPoints[i][j].x, allPoints[i][j].y);
            }
        }

        for(int i = 0; i < points.size(); i += Globals.K){
            for(int j = 1; j < Globals.M; j++){
                graphics.drawLine(allPoints[i][j - 1].x, allPoints[i][j - 1].y, allPoints[i][j].x, allPoints[i][j].y);
            }
        }
        for(Observer observer : observers){
            observer.setMainImage(image);
        }
    }

    @Override
    public void drawAxis(){
        BufferedImage image = new BufferedImage(Globals.IMAGE_WIDTH, Globals.IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = image.createGraphics();
        int centerX = Globals.IMAGE_WIDTH / 2;
        int centerY = Globals.IMAGE_HEIGHT / 2;
        graphics.setPaint(Color.BLUE);
        graphics.drawLine(centerX, centerY, Globals.IMAGE_WIDTH - 1, centerY );
        graphics.setPaint(Color.RED);
        graphics.drawLine(centerX, centerY, centerX, 0 );
        for(Observer observer : observers){
            observer.setMainImage(image);
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
