package com.nsu.fit.leonova.model;

import org.ejml.simple.SimpleMatrix;

public class Vector3D {
    private SimpleMatrix vector;

    public Vector3D(double x, double y, double z) {
        vector = new SimpleMatrix(new double[][]{{x}, {y}, {z}, {1}});
    }

    public SimpleMatrix get(){
        return vector;
    }

    public double get(int row){
        return vector.get(row, 0);
    }

    public Vector3D minus(Vector3D vector3D){
        SimpleMatrix result = vector.minus(vector3D.get());
        return new Vector3D(result.get(0, 0), result.get(1, 0), result.get(2, 0));
    }

    public Vector3D plus(Vector3D vector3D){
        SimpleMatrix result = vector.plus(vector3D.get());
        return new Vector3D(result.get(0, 0), result.get(1, 0), result.get(2, 0));
    }

    public double normF(){
        return vector.normF();
    }

    public Vector3D divide(double value){
        SimpleMatrix result = vector.divide(value);
        return new Vector3D(result.get(0, 0), result.get(1, 0), result.get(2, 0));
    }

    public void set(Vector3D vector3D){
        vector = vector3D.get();
    }



}
