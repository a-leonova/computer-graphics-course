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

    private Point3D splinePoints3D[][];
    private Point splinePoints2D[][];
    private boolean isActualSplinePoints3D = false;

    public void setbSplineProvider(BSplineProvider bSplineProvider) {
        this.bSplineProvider = bSplineProvider;
    }

    @Override
    public void showSpline3D() {
        BufferedImage image = new BufferedImage(Globals.IMAGE_WIDTH, Globals.IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = image.createGraphics();
        if(!isActualSplinePoints3D){
            //Point allPoints[][] = new Point[points.size()][Globals.M];
            List<Point> points = bSplineProvider.getPointsToRotate();
            splinePoints3D = new Point3D[points.size()][Globals.M];
            double step = 2 * Math.PI / (Globals.M - 1);
            for(int k = 0; k < points.size(); ++k){
                for(int v = 0; v < Globals.M; v++){
                    Point p = points.get(k);
                    int z = 0;
                    SimpleMatrix coordinates = new SimpleMatrix(new double[][]{{p.x}, {p.y}, {z}});
                    SimpleMatrix rotationMtx = MatrixGenerator.rotationMatrix3OX(v * step);
                    SimpleMatrix result = rotationMtx.mult(coordinates);
                    splinePoints3D[k][v] = new Point3D(result.get(0, 0), result.get(1, 0), result.get(2, 0));
                    //allPoints[k][v] = new Point((int)Math.round(result2.get(0,0)) + image.getWidth() / 2 , (int)Math.round(result2.get(1, 0)) + image.getHeight()/2);
                }
            }
            isActualSplinePoints3D = true;
        }
        //create transformation
        transform();
        for(int i = 1; i < bSplineProvider.getPointsToRotate().size(); ++i){
            for(int j = 0; j < Globals.M; j++){
                graphics.drawLine(splinePoints2D[i - 1][j].x, splinePoints2D[i - 1][j].y, splinePoints2D[i][j].x, splinePoints2D[i][j].y);
            }
        }

        for(int i = 0; i < bSplineProvider.getPointsToRotate().size(); i += Globals.K){
            for(int j = 1; j < Globals.M; j++){
                graphics.drawLine(splinePoints2D[i][j - 1].x, splinePoints2D[i][j - 1].y, splinePoints2D[i][j].x, splinePoints2D[i][j].y);
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
    public void newBspline() {
        isActualSplinePoints3D = false;
    }

    @Override
    public void rotationForOX(int shift) {
        rotationAngleRadOX += Math.toRadians(shift * 0.01);
        if(rotationAngleRadOX > 2 * Math.PI){
            rotationAngleRadOX = 0.0;
        }
        showSpline3D();
    }

    @Override
    public void rotationForOY(int shift) {
        rotationAngleRadOY += Math.toRadians(shift * 0.01);
        if(rotationAngleRadOY > 2 * Math.PI){
            rotationAngleRadOY = 0.0;
        }
        showSpline3D();
    }

    @Override
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void deleteObserver(Observer obs) {
        observers.remove(obs);
    }

    private void transform(){
        SimpleMatrix rotationOZ = MatrixGenerator.rotationMatrix4OZ(rotationAngleRadOZ);
        SimpleMatrix rotationOX = MatrixGenerator.rotationMatrix4OX(rotationAngleRadOX);
        SimpleMatrix rotationOY = MatrixGenerator.rotationMatrix4OY(rotationAngleRadOY);
        splinePoints2D = new Point[bSplineProvider.getPointsToRotate().size()][Globals.M];
        for(int k = 0; k < bSplineProvider.getPointsToRotate().size(); ++k){
            for(int v = 0; v < Globals.M; v++){
                SimpleMatrix coordinates = new SimpleMatrix(new double[][] {{splinePoints3D[k][v].getX()},
                        {splinePoints3D[k][v].getY()},
                        {splinePoints3D[k][v].getZ()},
                        {1}});
                coordinates = rotationOX.mult(rotationOY).mult(rotationOZ).mult(coordinates);
                coordinates = MatrixGenerator.projectionMatrix().mult(coordinates);
                coordinates = coordinates.divide(coordinates.get(3, 0));
                splinePoints2D[k][v] = new Point((int)Math.round(coordinates.get(0, 0) + Globals.IMAGE_WIDTH / 2),
                        (int)Math.round(coordinates.get(1, 0) + Globals.IMAGE_HEIGHT /2));
            }
        }
    }
}
