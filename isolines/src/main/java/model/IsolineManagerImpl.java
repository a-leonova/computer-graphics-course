package model;

import observers.Observable;
import observers.Observer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class IsolineManagerImpl implements Observable, IsolineManager {
    private BufferedImage image;
    private ArrayList<Observer> observers = new ArrayList<>();
    private int[] colorsRGB;

    private double minX;
    private double maxX;

    private double minY;
    private double maxY;

    private double minZ;
    private double maxZ;

    private double xWidth;
    private double yHeight;


    public IsolineManagerImpl(int width, int height) {
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
        double[] minMax = findMinMax();
        minZ = minMax[0];
        maxZ = minMax[1];
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
    }

    public void createGraphic(){
        for(int y = 0; y < image.getHeight(); ++y){
            for (int x = 0; x < image.getWidth(); ++x){
                double y0 = y / (double)image.getHeight() * xWidth + minY;
                double x0 = x / (double)image.getWidth() * yHeight + minX;
                int color = findColor(countFunction(x0, y0));
                image.setRGB(x, y, color);
            }
        }
        for(Observer observer : observers){
            observer.setImage(image);
        }
    }

    @Override
    public void createLegend() {
        BufferedImage legend = new BufferedImage(10, 500, BufferedImage.TYPE_3BYTE_BGR);

        for(int y = 0; y < legend.getHeight(); ++y){
            for (int x = 0; x < legend.getWidth(); ++x){
                double y0 = (500 - y) / ((double)legend.getHeight()) * (colorsRGB.length - 1);
                //double x0 = x / (double)image.getWidth() * yHeight + minX;
                int color = colorsRGB[(int)Math.round(y0)];
                legend.setRGB(x, y, color);
            }
        }
        for(Observer observer : observers){
            observer.setLegend(legend);
        }
    }

    private double[] findMinMax(){
        double minZ = countFunction(minX, minY);
        double maxZ = countFunction(minX, minY);
        for(int y = 0; y < image.getHeight(); ++y){
            for (int x = 0; x < image.getWidth(); ++x){
                double y0 = y / (double)image.getHeight() * yHeight + minY;
                double x0 = x / (double)image.getWidth() * xWidth + minX;
                double z = countFunction(x0, y0);
                minZ = z < minZ ? z : minZ;
                maxZ = z > maxZ ? z : maxZ;
            }
        }
        return new double[]{minZ, maxZ};
    }

    private int findColor(double z){
        double relative = (z - minZ) / (maxZ - minZ);
        int i = (int)Math.round(relative * (colorsRGB.length - 1));
        return colorsRGB[i];
    }

    private double countFunction(double x, double y){
        return Math.sin(x) + Math.cos(y);
    }

    private double countLinearFunction(double x, double y){
        return x + y;
    }


    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}
