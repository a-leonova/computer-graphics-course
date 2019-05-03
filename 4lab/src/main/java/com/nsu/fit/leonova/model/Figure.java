package com.nsu.fit.leonova.model;

import com.nsu.fit.leonova.model.bspline.BSpline;
import com.nsu.fit.leonova.model.bspline.SplineParameters;
import com.nsu.fit.leonova.model.memento.BSplineMemento;
import com.nsu.fit.leonova.model.memento.FigureMemento;
import com.nsu.fit.leonova.observer.BSplineObservable;
import com.nsu.fit.leonova.observer.BSplineObserver;
import org.ejml.simple.SimpleMatrix;

import java.awt.*;
import java.util.List;

public class Figure implements BSplineObservable {
    private BSpline bSpline;
    private SplineParameters parameters = new SplineParameters("Some name");
    private SimpleMatrix rotationMatrix = MatrixGenerator.identity4();
    private SimpleMatrix shiftMatrix = MatrixGenerator.shiftMatrix(0, 0, 0);

    private Point3D splinePoints3D[][];
    private boolean isActualSplinePoints3D = false;
    private Point3D transformedPoints3D[][];
    private boolean isActualTransformedPoints3D = false;

    public Figure(List<BSplineObserver> obs, int index) {
        parameters.setSplineName("Figure #" + index);
        bSpline = new BSpline(this.parameters);
        for(BSplineObserver o : obs){
            bSpline.addObserver(o);
        }
    }

    public Point3D[][] getTransformedPoints3D(){
        if(!isActualTransformedPoints3D){
            if(!isActualSplinePoints3D){
                countBSpline3D();
            }
            useTransformation();
        }
        return transformedPoints3D;
    }

    public void changeScale(double ds){
        bSpline.changeScale(ds);
    }

    public void showBspline() {
        bSpline.showBspline();
    }

    public void addPoint(Point point) {
        bSpline.addPoint(point);
        isActualTransformedPoints3D = isActualSplinePoints3D = false;
    }

    public void removePoint(Point point) {
        bSpline.removePoint(point);
        isActualTransformedPoints3D = isActualSplinePoints3D = false;
    }

    public void pressedPoint(Point point) {
        bSpline.pressedPoint(point);
        isActualTransformedPoints3D = isActualSplinePoints3D = false;
    }

    public void draggedPoint(Point point) {
        bSpline.draggedPoint(point);
        isActualTransformedPoints3D = isActualSplinePoints3D = false;
    }

    public void setBsplineParameters(SplineParameters parameters) {
        this.parameters = parameters;
        bSpline.setParameters(parameters);
        isActualTransformedPoints3D =  isActualSplinePoints3D = false;
    }

    public void shift(Point3D center){
        isActualTransformedPoints3D = false;
        shiftMatrix = MatrixGenerator.shiftMatrix((int)center.getX(), (int)center.getY(), (int)center.getZ());
    }

    public SplineParameters getParameters() {
        return parameters;
    }

    public SimpleMatrix getRotationMatrix() {
        return rotationMatrix;
    }

    public SimpleMatrix getShiftMatrix() {
        return shiftMatrix;
    }

    public int getPointsToRotateCount(){
        return bSpline.getPointsToRotate().size();
    }

    public void setRotation(SimpleMatrix rotationMatrix){
        this.rotationMatrix = rotationMatrix;
        isActualTransformedPoints3D = false;
    }

    public void rotateForOX(double angle){
        rotationMatrix = MatrixGenerator.rotationMatrix4OX(angle).mult(rotationMatrix);
        isActualTransformedPoints3D = false;
    }

    public void rotateForOY(double angle){
        rotationMatrix = MatrixGenerator.rotationMatrix4OY(angle).mult(rotationMatrix);
        isActualTransformedPoints3D = false;
    }

    public FigureMemento getMemento(){
        int[] shift = new int[]{(int)(shiftMatrix.get(0, 3)), (int)(shiftMatrix.get(1, 3)), (int)(shiftMatrix.get(2, 3))};
        BSplineMemento bSplineMemento = bSpline.getMemento();
        double[][] rotation = new double[][]{
                {rotationMatrix.get(0, 0), rotationMatrix.get(0, 1), rotationMatrix.get(0, 2)},
                {rotationMatrix.get(1, 0), rotationMatrix.get(1, 1), rotationMatrix.get(1, 2)},
                {rotationMatrix.get(2, 0), rotationMatrix.get(2, 1), rotationMatrix.get(2, 2)}};
        return new FigureMemento(bSplineMemento, parameters.getColor(), shift, rotation);

    }

    public void resetAngles(){
        rotationMatrix = MatrixGenerator.identity4();
        isActualTransformedPoints3D = false;
    }

    private void countBSpline3D(){
        List<Point2D> points = bSpline.getPointsToRotate();
        splinePoints3D = new Point3D[points.size()][parameters.getM()];
        double step = (parameters.getD() - parameters.getC()) / (parameters.getM() - 1);
        for(int k = 0; k < points.size(); ++k){
            for(int v = 0; v < parameters.getM(); v++){
                Point2D p = points.get(k);
                int z = 0;
                SimpleMatrix coordinates = new SimpleMatrix(new double[][]{{p.getX()}, {p.getY()}, {z}});
                SimpleMatrix rotationMtx = MatrixGenerator.rotationMatrix3OX(v * step);
                SimpleMatrix result = rotationMtx.mult(coordinates);
                splinePoints3D[k][v] = new Point3D(result.get(0, 0), result.get(1, 0), result.get(2, 0));
            }
        }
        isActualSplinePoints3D = true;
    }

    private void useTransformation(){
        transformedPoints3D = new Point3D[bSpline.getPointsToRotate().size()][parameters.getM()];
        for(int k = 0; k < bSpline.getPointsToRotate().size(); ++k){
            for(int v = 0; v < parameters.getM(); v++){
                SimpleMatrix coordinates = new SimpleMatrix(new double[][] {{splinePoints3D[k][v].getX()},
                        {splinePoints3D[k][v].getY()},
                        {splinePoints3D[k][v].getZ()},
                        {1}});
                coordinates = rotationMatrix.mult(coordinates);
                coordinates = shiftMatrix.mult(coordinates);
                transformedPoints3D[k][v] = new Point3D((int)Math.round(coordinates.get(0,0)),
                        (int)Math.round(coordinates.get(1,0)),
                                (int)Math.round(coordinates.get(2,0)));
            }
        }
        isActualTransformedPoints3D = true;
    }

    @Override
    public void addObserver(BSplineObserver obs) {
        bSpline.addObserver(obs);
    }

    @Override
    public void removeObserver(BSplineObserver obs) {
        bSpline.removeObserver(obs);
    }
}
