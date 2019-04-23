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

    public void setbSplineProvider(BSplineProvider bSplineProvider) {
        this.bSplineProvider = bSplineProvider;
    }

    @Override
    public void showSpline3D() {
        BufferedImage image = new BufferedImage(Globals.IMAGE_WIDTH, Globals.IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = image.createGraphics();
        List<Point> points = bSplineProvider.getPointsToRotate();
        Point allPoints[][] = new Point[points.size()][Globals.V];
        double step = 2 * Math.PI / Globals.V;
        for(int k = 0; k < Globals.K * Globals.U; ++k){
            for(int v = 0; v < Globals.V; v++){
                Point p = points.get(k);
                int z = 0;
                SimpleMatrix vectorBeg = new SimpleMatrix(new double[][]{{p.x, p.y, z}});
                SimpleMatrix rotationMtx = MatrixGenerator.generateMatrixForRotationAroundOX(v * step);
                SimpleMatrix result = vectorBeg.mult(rotationMtx);
//                int x = (int)Math.round(points.get(k).y * Math.cos(v * step));
//                int z = (int)Math.round(points.get(k).y * Math.sin(v * step));
//                int y = points.get(k).x;
                allPoints[k][v] = new Point((int)Math.round(result.get(0,0)), (int)Math.round(result.get(0, 1)));
            }
        }

        for(int i = 1; i < Globals.K * Globals.U; ++i){
            for(int j = 0; j < Globals.V; j++){
                graphics.drawLine(allPoints[i - 1][j].x, allPoints[i - 1][j].y, allPoints[i][j].x, allPoints[i][j].y);
            }
        }

        for(int i = 0; i < Globals.K * Globals.U; ++i){
            for(int j = 1; j < Globals.V; j++){
                graphics.drawLine(allPoints[i][j - 1].x, allPoints[i][j - 1].y, allPoints[i][j].x, allPoints[i][j].y);
            }
        }
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
