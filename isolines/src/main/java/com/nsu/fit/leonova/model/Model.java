package com.nsu.fit.leonova.model;

import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.model.graphicProvider.*;
import com.nsu.fit.leonova.observers.Observable;
import com.nsu.fit.leonova.observers.Observer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Model implements GraphicManager, IsolineManager, Observable {

    private int k = 60;
    private int m = 60;

    private GraphicValues graphicValues;
    private ArrayList<Observer> observers = new ArrayList<>();

    private IsolineDrawer isolineDrawer = new IsolineDrawer(k, m);
    private GraphicDrawer graphicDrawer = new GraphicDrawer();
    private int colorsCnt;

    private BufferedImage sourceGraphic;
    private boolean drawNet = false;
    private boolean pivotPoints = false;
    private HashSet<Double> isolinesToDraw = new HashSet<>();
    private List<Point> points;


    @Override
    public void createGraphic(boolean gradient) {
        sourceGraphic = new BufferedImage(Globals.WIDTH, Globals.HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        graphicDrawer.createGraphic(sourceGraphic, gradient);
        for(Observer observer : observers){
            observer.setImage(sourceGraphic);
        }
    }

    @Override
    public void createLegend(boolean gradient) {
        BufferedImage legend = new BufferedImage(Globals.LEGEND_WIDTH, Globals.LEGEND_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        graphicDrawer.createLegend(legend, gradient);
        for(Observer observer : observers){
            observer.setLegend(legend);
        }
    }

    @Override
    public void setColorsRGB(SafeColor[] colorsRGB) {
        colorsCnt = colorsRGB.length;
        graphicDrawer.setColorsRGB(colorsRGB);
    }

    @Override
    public void drawAllLevelIsolines() {
        double step = (graphicDrawer.getMaxZ() - graphicDrawer.getMinZ()) / colorsCnt;
        for(int i = 0; i < colorsCnt; ++i){
            isolinesToDraw.add(graphicDrawer.getMinZ() + step * i);
        }
        createImage();
    }

    @Override
    public void clickedIsoline(Point pressedPixel) {
        DoublePoint point = graphicValues.pixelToGraphicCoord(new DoublePoint(pressedPixel.x, pressedPixel.y), sourceGraphic.getWidth(), sourceGraphic.getHeight());
        double z = Function.countValue(point);
        isolinesToDraw.add(z);
        createImage();
    }

    @Override
    public void draggedIsoline(Point pressedPixel) {
        DoublePoint point = graphicValues.pixelToGraphicCoord(new DoublePoint(pressedPixel.x, pressedPixel.y), sourceGraphic.getWidth(), sourceGraphic.getHeight());
        double z = Function.countValue(point);
        isolinesToDraw.add(z);
        createImage();
        isolinesToDraw.remove(z);
    }

    @Override
    public void setDefinitionArea(double minX, double minY, double maxX, double maxY) {
        graphicValues = new GraphicValues(minX, minY, maxX, maxY);
        graphicDrawer.setGraphicValues(graphicValues);
        isolineDrawer.setGraphicValues(graphicValues);
    }

    @Override
    public void removeIsolines() {
        isolinesToDraw.clear();
    }

    @Override
    public void setNet(int k, int m) {
        this.k = k;
        this.m = m;
        isolineDrawer.setNet(k, m);
    }

    @Override
    public void drawNet() {
        drawNet = !drawNet;
        createImage();
    }

    @Override
    public void pivotPoints() {
        pivotPoints = !pivotPoints;
        createImage();
    }

    @Override
    public void pixelToCoordinate(Point pixel) {
        DoublePoint graphicCoordinate = graphicValues.pixelToGraphicCoord(new DoublePoint(pixel), sourceGraphic.getWidth(), sourceGraphic.getHeight());
        for(Observer observer : observers){
            observer.setCoordinates(graphicCoordinate);
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void createImage(){
        BufferedImage image = deepCopy(sourceGraphic);
        points = new ArrayList<>();
        for(Double isolineValue: isolinesToDraw){
            points.addAll(isolineDrawer.drawIsoline(image, isolineValue));
        }
        if(drawNet){
            int stepX = image.getWidth() / k;
            int stepY = image.getHeight() / m;
            Graphics2D g2 = image.createGraphics();
            for(int i = 0; i < image.getWidth(); i += stepX){
                g2.drawLine(i, 0, i, image.getHeight() - 1);
            }
            for(int i = 0; i < image.getHeight(); i += stepY){
                g2.drawLine(0, i, image.getWidth() - 1, i);
            }
        }
        if(pivotPoints){
            for(Point point : points){
                Graphics2D g2 = image.createGraphics();
                //g2.drawRect(point.x, point.y, 4, 4);
                g2.setPaint(Color.BLACK);
                g2.fillRect(point.x, point.y, 3, 3);

            }
        }
        for(Observer observer : observers){
            observer.setImage(image);
        }
    }

    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
