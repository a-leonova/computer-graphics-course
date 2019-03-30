package com.nsu.fit.leonova.model;

import com.nsu.fit.leonova.observers.Observable;
import com.nsu.fit.leonova.observers.Observer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class IsolineManagerImpl implements Observable, IsolineManager {
    private BufferedImage image;
    private ArrayList<Observer> observers = new ArrayList<>();
    private SafeColor[] colorsRGB;

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

    public void setColorsRGB(SafeColor[] colorsRGB) {
        this.colorsRGB = colorsRGB;
    }

    @Override
    public void createGraphic(boolean gradient){
        image = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for(int y = 0; y < image.getHeight(); ++y){
            for (int x = 0; x < image.getWidth(); ++x){
                double y0 = y / (double)image.getHeight() * xWidth + minY;
                double x0 = x / (double)image.getWidth() * yHeight + minX;
                int color;
                if(gradient){
                    color = findGradientColor(countFunction(x0, y0), minZ, maxZ);
                }
                else{
                    color = findUsualColor(countFunction(x0, y0), minZ, maxZ);
                }
                image.setRGB(x, y, color);
            }
        }
        for(Observer observer : observers){
            observer.setImage(image);
        }
    }

    @Override
    public void createLegend(boolean gradient) {
        BufferedImage legend = new BufferedImage(10, 500, BufferedImage.TYPE_3BYTE_BGR);
        for(int y = 0; y < legend.getHeight(); ++y){
            double y0 = y / ((double)legend.getHeight());
            int rgb;
            if(gradient){
                rgb = findGradientColor(1 - y0, 0, 1);
            }
            else{
                rgb = findUsualColor(1 - y0, 0, 1);
            }
            for (int x = 0; x < legend.getWidth(); ++x){
                legend.setRGB(x, y, rgb);
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

    private int findUsualColor(double z, double minZ, double maxZ){
        double relative = (z - minZ) / (maxZ - minZ);
        int i = Math.min((int)Math.floor(relative * (colorsRGB.length)), colorsRGB.length - 1);
        return colorsRGB[i].getIntRgb();
    }

    private int findGradientColor(double z, double minZ, double maxZ){
        double relative = (z - minZ) / (maxZ - minZ);
        double i = relative * (colorsRGB.length - 1);

        int left = (int)Math.floor(i);
        int right = (int)Math.ceil(i);
        if(left == right){
            return colorsRGB[left].getIntRgb();
        }
        SafeColor colorLeft = colorsRGB[left];
        SafeColor colorRight = colorsRGB[right];
        SafeColor res = colorLeft.multiple(right - i).plus(colorRight.multiple(i - left));
        return res.getIntRgb();
    }

    private double countFunction(double x, double y){
        return Math.sin(x) + Math.cos(y);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}
