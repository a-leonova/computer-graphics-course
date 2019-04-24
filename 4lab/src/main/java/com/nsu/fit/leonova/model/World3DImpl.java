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

    private double rotationAngleRadOX = Math.toRadians(45.0);
    private double rotationAngleRadOY = Math.toRadians(60.0);
    private double rotationAngleRadOZ = Math.toRadians(85.0);

    public void setbSplineProvider(BSplineProvider bSplineProvider) {
        this.bSplineProvider = bSplineProvider;
    }

    @Override
    public void showSpline3D() {
        BufferedImage image = new BufferedImage(Globals.IMAGE_WIDTH, Globals.IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = image.createGraphics();
        List<Point> points = bSplineProvider.getPointsToRotate();
        Point allPoints[][] = new Point[points.size()][Globals.M];
        double step = 2 * Math.PI / (Globals.M - 1);
        for(int k = 0; k < points.size(); ++k){
            for(int v = 0; v < Globals.M; v++){
                Point p = points.get(k);
                int z = 0;
                SimpleMatrix vector1 = new SimpleMatrix(new double[][]{{p.x}, {p.y}, {z}});
                SimpleMatrix rotationMtx = MatrixGenerator.rotationMatrix3OX(v * step);
                SimpleMatrix result1 = rotationMtx.mult(vector1);
                SimpleMatrix vector2 = new SimpleMatrix(new double[][]{
                        {result1.get(0, 0)}, {result1.get(1, 0)}, {result1.get(2,0)}, {1}
                });
                //SimpleMatrix result2 = MatrixGenerator.shiftMatrix(-200, -100, 0).mult(vector2);
                SimpleMatrix rotationOZ = MatrixGenerator.rotationMatrix4OZ(rotationAngleRadOZ);
                SimpleMatrix rotationOX = MatrixGenerator.rotationMatrix4OX(rotationAngleRadOX);
                SimpleMatrix rotationOY = MatrixGenerator.rotationMatrix4OY(rotationAngleRadOY);
                SimpleMatrix result2 =  rotationOX.mult(rotationOY).mult(rotationOZ).mult(vector2);
                result2 = MatrixGenerator.projectionMatrix().mult(result2);
                result2 = result2.divide(result2.get(3, 0));
                allPoints[k][v] = new Point((int)Math.round(result2.get(0,0)) + image.getWidth() / 2 , (int)Math.round(result2.get(1, 0)) + image.getHeight()/2);
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
            //graphics.drawLine(allPoints[i][0].x, allPoints[i][0].y, allPoints[i][Globals.M - 1].x, allPoints[i][Globals.M - 1].y);
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
