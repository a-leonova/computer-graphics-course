package com.nsu.fit.leonova.controller;

import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.model.*;
import com.nsu.fit.leonova.model.bspline.SplineParameters;
import com.nsu.fit.leonova.model.memento.BSplineMemento;
import com.nsu.fit.leonova.model.memento.FigureMemento;
import com.nsu.fit.leonova.model.memento.WorldMemento;
import org.ejml.simple.SimpleMatrix;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {

    private double[] commonParametersArray;
    private World3D world3D;

    public FileManager(World3D world3D) {
        this.world3D = world3D;
    }

    public void openFile(File file) {
        try (Scanner scanner = new Scanner(file)) {
            commonParametersArray = readCommonParameters(scanner);
            int[] worldParametersArray = readWorldParameters(scanner);
            SimpleMatrix worldRotation = readRotationMatrix(scanner);
            Color backgroundColor = readNextColor(scanner);

            int k = readFiguresQuantity(scanner);
            for (int i = 0; i < k; ++i) {
                readNextFigure(scanner, i);
            }

            WorldParameters worldParameters = new WorldParameters(worldParametersArray[0], worldParametersArray[1], worldParametersArray[2], worldParametersArray[3]);
            world3D.setWorldParameters(worldParameters);
            world3D.setBackgroundColor(backgroundColor);
            world3D.setWorldRotation(worldRotation);

        } catch (FileNotFoundException | IllegalArgumentException e) {
//            infoManager.showError(e.getMessage());
        }
    }

    public void save(File file) {
        try (PrintWriter write = new PrintWriter(new FileOutputStream(file))) {
            WorldMemento worldMemento = world3D.getWorldMemento();
            write.println(worldMemento.getSplineParameters().getN() + " " + worldMemento.getSplineParameters().getM() + " "
                    + worldMemento.getSplineParameters().getK() + " " + worldMemento.getSplineParameters().getA() + " "
                    + worldMemento.getSplineParameters().getB() + " " + worldMemento.getSplineParameters().getC() + " "
                    + worldMemento.getSplineParameters().getD());
            write.println(worldMemento.getWorldParameters().getZf() + " " + worldMemento.getWorldParameters().getZb() + " "
                    + worldMemento.getWorldParameters().getSw() + " " + worldMemento.getWorldParameters().getSh());

            double[][] rotation = worldMemento.getRotation();
            write.println(rotation[0][0] + " " + rotation[0][1] + " " + rotation[0][2]);
            write.println(rotation[1][0] + " " + rotation[1][1] + " " + rotation[1][2]);
            write.println(rotation[2][0] + " " + rotation[2][1] + " " + rotation[2][2]);

            Color backgroundColor = worldMemento.getColor();
            write.println(backgroundColor.getRed() + " " + backgroundColor.getGreen() + " " + backgroundColor.getBlue());

            List<FigureMemento> figures = worldMemento.getFigureMemento();
            write.println(figures.size());
            for (FigureMemento figure : figures) {
                Color color = figure.getColor();
                write.println(color.getRed() + " " + color.getGreen() + " " + color.getBlue());
                int[] shift = figure.getShift();
                write.println(shift[0] + " " + shift[1] + " " + shift[2]);
                double[][] figureRotation = figure.getRotation();
                write.println(figureRotation[0][0] + " " + figureRotation[0][1] + " " + figureRotation[0][2]);
                write.println(figureRotation[1][0] + " " + figureRotation[1][1] + " " + figureRotation[1][2]);
                write.println(figureRotation[2][0] + " " + figureRotation[2][1] + " " + figureRotation[2][2]);

                BSplineMemento bSplineMemento = figure.getbSplineMemento();
                List<Point> points = bSplineMemento.getPivotPoints();
                write.println(points.size());
                for(Point point : points){
                    write.println(point.x + " " + point.y);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void readNextFigure(Scanner scanner, int idx) {
        Color figureColor = readNextColor(scanner);
        int[] figureShift = readFigureShift(scanner);
        SimpleMatrix figureRotation = readRotationMatrix(scanner);
        List<Point> points = readBSplinePoints(scanner);
        SplineParameters sp = new SplineParameters("Figure #" + idx, (int) Math.round(commonParametersArray[0]),
                (int) Math.round(commonParametersArray[1]),
                (int) Math.round(commonParametersArray[2]),
                commonParametersArray[3],
                commonParametersArray[4],
                commonParametersArray[5],
                commonParametersArray[6],
                figureColor);
        world3D.addSpline();
        for (Point p : points) {
            world3D.addPointToCurrentBSpline(p);
        }
        world3D.setSplineParameters(sp);
        world3D.setFigureCenter(new Point3D(figureShift[0], figureShift[1], figureShift[2]));
        world3D.setFigureRotation(figureRotation);
    }

    private int[] readFigureShift(Scanner scanner) {
        String[] shifts = readNextNumbers(scanner, 3);
        int x = Integer.parseInt(shifts[0]);
        int y = Integer.parseInt(shifts[1]);
        int z = Integer.parseInt(shifts[2]);
        return new int[]{x, y, z};
    }

    private List<Point> readBSplinePoints(Scanner scanner) {
        String[] k = readNextNumbers(scanner, 1);
        int cnt = Integer.parseInt(k[0]);
        if (cnt < 4) {
            throw new IllegalArgumentException("Must be at least 4 point for bspline");
        }
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < cnt; ++i) {
            Point p = readPoint(scanner);
            points.add(p);
        }
        return points;
    }

    private Point readPoint(Scanner scanner) {
        String[] numbers = readNextNumbers(scanner, 2);
        int x = Integer.parseInt(numbers[0]);
        int y = Integer.parseInt(numbers[1]);
        return new Point(x, y);
    }

    private SimpleMatrix readRotationMatrix(Scanner scanner) {
        String[] firstRow = readNextNumbers(scanner, 3);
        double a11 = Double.parseDouble(firstRow[0]);
        double a12 = Double.parseDouble(firstRow[1]);
        double a13 = Double.parseDouble(firstRow[2]);
        String[] secondRow = readNextNumbers(scanner, 3);
        double a21 = Double.parseDouble(secondRow[0]);
        double a22 = Double.parseDouble(secondRow[1]);
        double a23 = Double.parseDouble(secondRow[2]);
        String[] thirdRow = readNextNumbers(scanner, 3);
        double a31 = Double.parseDouble(thirdRow[0]);
        double a32 = Double.parseDouble(thirdRow[1]);
        double a33 = Double.parseDouble(thirdRow[2]);

        SimpleMatrix rotationMtx = new SimpleMatrix(new double[][]{
                {a11, a12, a13, 0.0},
                {a21, a22, a23, 0.0},
                {a31, a32, a33, 0.0},
                {0.0, 0.0, 0.0, 1.0}});
        return rotationMtx;
    }

    private int readFiguresQuantity(Scanner scanner) {
        String[] k = readNextNumbers(scanner, 1);
        int cnt = Integer.parseInt(k[0]);
        if (cnt < 1) {
            throw new IllegalArgumentException("Must be at least one figure");
        }
        return cnt;
    }

    private Color readNextColor(Scanner scanner) {
        String[] numbers = readNextNumbers(scanner, 3);
        int r = Integer.parseInt(numbers[0]);
        int g = Integer.parseInt(numbers[1]);
        int b = Integer.parseInt(numbers[2]);
        if (r < 0 || r > 255 || g < 0 || g > 255 | b < 0 || b > 255) {
            throw new IllegalArgumentException("Wrong color value in configuration file. They must be in range [0; 255]");
        }
        return new Color(r, g, b);
    }

    private int[] readWorldParameters(Scanner scanner) {
        String[] numbers = readNextNumbers(scanner, 4);
        int zf = Integer.parseInt(numbers[0]);
        int zb = Integer.parseInt(numbers[1]);
        int sw = Integer.parseInt(numbers[2]);
        int sh = Integer.parseInt(numbers[3]);

        if (zf >= zb) {
            throw new IllegalArgumentException("Must be: zf < zb");
        }

        return new int[]{zf, zb, sw, sh};
    }

    private double[] readCommonParameters(Scanner scanner) {
        String[] numbers = readNextNumbers(scanner, 7);
        int n = Integer.parseInt(numbers[0]);
        int m = Integer.parseInt(numbers[1]);
        int k = Integer.parseInt(numbers[2]);
        double a = Double.parseDouble(numbers[3]);
        double b = Double.parseDouble(numbers[4]);
        double c = Double.parseDouble(numbers[5]);
        double d = Double.parseDouble(numbers[6]);

        if (n < Globals.MIN_N || m < Globals.MIN_M || k < Globals.MIN_K) {
            throw new IllegalArgumentException("n, m, k must be positive number more than: " + Globals.MIN_N + " " + Globals.MIN_M + " " + Globals.MIN_K);
        }
        if (a < Globals.MIN_A || b < Globals.MIN_B || c < Globals.MIN_C || d < Globals.MIN_D) {
            throw new IllegalArgumentException("a, b, c, d must be positive number more than: " + Globals.MIN_A + " " + Globals.MIN_B + " " + Globals.MIN_C + " " + Globals.MIN_D);
        }
        if (a > Globals.MAX_A || b > Globals.MAX_B || c > Globals.MAX_C || d > Globals.MAX_D) {
            throw new IllegalArgumentException("a, b, c, d must be less than: " + Globals.MAX_A + " " + Globals.MAX_B + " " + Globals.MAX_C + " " + Globals.MAX_D);
        }
        if (a >= b || c >= d) {
            throw new IllegalArgumentException("Must be a < b  and c < d!");
        }

        return new double[]{n, m, k, a, b, c, d};
    }

    private String[] readNextNumbers(Scanner scanner, int n) {
        String line;
        do {
            line = scanner.nextLine();
            int commentBegin = line.indexOf("//");
            if (commentBegin != -1) {
                line = line.substring(0, commentBegin);
            }
        } while ("".equals(line));
        String[] numbers = line.split(" ");
        if (numbers.length != n) {
            throw new IllegalArgumentException("Bad number of parameters in configuration file");
        }
        return numbers;
    }

}
