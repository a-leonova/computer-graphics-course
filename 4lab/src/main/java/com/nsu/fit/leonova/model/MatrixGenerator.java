package com.nsu.fit.leonova.model;

import org.ejml.simple.SimpleMatrix;

public class MatrixGenerator {

    public static SimpleMatrix generateMatrixForRotationAroundOX(double radianAngle){
        double cos = Math.cos(radianAngle);
        double sin = Math.sin(radianAngle);
        return new SimpleMatrix(new double[][]{
                {1, 0, 0},
                {0, cos, -sin},
                {0, sin, cos}});
    }

    public static SimpleMatrix generateMatrixForRotationAroundOY(double radianAngle){
        double cos = Math.cos(radianAngle);
        double sin = Math.sin(radianAngle);
        return new SimpleMatrix(new double[][]{
                {cos, 0, sin},
                {0, 1, 0},
                {-sin, 0, cos}});
    }

    public static SimpleMatrix generateMatrixForRotationAroundOZ(double radianAngle){
        double cos = Math.cos(radianAngle);
        double sin = Math.sin(radianAngle);
        return new SimpleMatrix(new double[][]{
                {cos, -sin, 0},
                {sin, cos, 0},
                {0, 0, 1}});
    }

}
