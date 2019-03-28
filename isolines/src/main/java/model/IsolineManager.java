package model;

import observers.Observable;
import observers.Observer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class IsolineManager implements Observable {
    private BufferedImage image;
    private ArrayList<Observer> observers = new ArrayList<>();
    private int[] colorsRGB;
    private double[] keyValues;

    private double minX;
    private double maxX;

    private double minY;
    private double maxY;

    private double xWidth;
    private double yHeight;

    public IsolineManager(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void setGraphicArea(double minX, double maxX, double minY, double maxY){
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        xWidth = Math.abs(minX) + Math.abs(maxX);
        yHeight = Math.abs(minY) + Math.abs(maxY);
    }

    public double getMinX() {
        return minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setColorsRGB(int[] colorsRGB) {
        this.colorsRGB = colorsRGB;
        keyValues = new double[colorsRGB.length - 1];
        double[] minMax = findMinMax();
        double step = (minMax[1] - minMax[0]) / (keyValues.length - 1);
        keyValues[0] = minMax[0];
        keyValues[keyValues.length - 1] = minMax[1];
        for(int i = 1; i < keyValues.length - 1; ++i){
            keyValues[i] = minMax[0] + i * step;
        }
    }

    public void createGraphic(){
        for(int y = 0; y < image.getHeight(); ++y){
            for (int x = 0; x < image.getWidth(); ++x){
                double y0 = y / (double)image.getHeight() * xWidth + minY;
                double x0 = x / (double)image.getWidth() * yHeight + minX;

            }
        }
    }

    private double[] findMinMax(){
        double minZ = countFunction(minX, minY);
        double maxZ = countFunction(minX, minY);
        for(int y = 0; y < image.getHeight(); ++y){
            for (int x = 0; x < image.getWidth(); ++x){
                double y0 = y / (double)image.getHeight() * xWidth + minY;
                double x0 = x / (double)image.getWidth() * yHeight + minX;
                double z = countFunction(x0, y0);
                minZ = z < minZ ? z : minZ;
                maxZ = z > maxZ ? z : maxZ;
            }
        }
        return new double[]{minZ, maxZ};
    }

//    private int findColor(double x, double y){
//        double z = countFunction(x, y);
//    }

    private double countFunction(double x, double y){
        return Math.sin(Math.toRadians(x)) + Math.cos(Math.toRadians(y));
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}
