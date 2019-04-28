package com.nsu.fit.leonova.model;

import com.nsu.fit.leonova.globals.Globals;
import org.ejml.simple.SimpleMatrix;

public class MatrixGenerator {

    public static SimpleMatrix rotationMatrix3OX(double radianAngle){
        double cos = Math.cos(radianAngle);
        double sin = Math.sin(radianAngle);
        return new SimpleMatrix(new double[][]{
                {1, 0, 0},
                {0, cos, -sin},
                {0, sin, cos}});
    }

    public static SimpleMatrix rotationMatrix3OY(double radianAngle){
        double cos = Math.cos(radianAngle);
        double sin = Math.sin(radianAngle);
        return new SimpleMatrix(new double[][]{
                {cos, 0, sin},
                {0, 1, 0},
                {-sin, 0, cos}});
    }

    public static SimpleMatrix rotationMatrix3OZ(double radianAngle){
        double cos = Math.cos(radianAngle);
        double sin = Math.sin(radianAngle);
        return new SimpleMatrix(new double[][]{
                {cos, -sin, 0},
                {sin, cos, 0},
                {0, 0, 1}});
    }

    public static SimpleMatrix rotationMatrix4OX(double radianAngle){
        double cos = Math.cos(radianAngle);
        double sin = Math.sin(radianAngle);
        return new SimpleMatrix(new double[][]{
                {1, 0, 0, 0},
                {0, cos, -sin, 0},
                {0, sin, cos, 0},
                {0, 0, 0, 1}});
    }

    public static SimpleMatrix rotationMatrix4OY(double radianAngle){
        double cos = Math.cos(radianAngle);
        double sin = Math.sin(radianAngle);
        return new SimpleMatrix(new double[][]{
                {cos, 0, sin, 0},
                {0, 1, 0, 0},
                {-sin, 0, cos, 0},
                {0, 0, 0, 1}});
    }

    public static SimpleMatrix rotationMatrix4OZ(double radianAngle){
        double cos = Math.cos(radianAngle);
        double sin = Math.sin(radianAngle);
        return new SimpleMatrix(new double[][]{
                {cos, -sin, 0, 0},
                {sin, cos, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}});
    }

    public static SimpleMatrix scaleMatrix(double x, double y, double z){
        return new SimpleMatrix(new double[][]{
                {x, 0, 0, 0},
                {0, y, 0, 0},
                {0, 0, z, 0},
                {0, 0, 0, 1}});
    }

    public static SimpleMatrix shiftMatrix(int x, int y, int z){
        return new SimpleMatrix(new double[][]{
                {1, 0, 0, x},
                {0, 1, 0, y},
                {0, 0, 1, z},
                {0, 0, 0, 1}});
    }

    public static SimpleMatrix projectionMatrix(double zf){
//        return new SimpleMatrix(new double[][]{
//                {1, 0, 0, 0},
//                {0, 1, 0, 0},
//                {0, 0, 0, 0},
//                {0, 0, 0, 1}});
        return new SimpleMatrix(new double[][]{
                {zf, 0, 0, 0},
                {0, zf, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 1, 0}});
    }

    public static SimpleMatrix identity4(){
        return new SimpleMatrix(new double[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}});
    }


}
