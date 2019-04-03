package com.nsu.fit.leonova.model;

import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.model.graphicProvider.GraphicDrawer;
import com.nsu.fit.leonova.model.graphicProvider.IsolineDrawer;
import com.nsu.fit.leonova.observers.Observable;
import com.nsu.fit.leonova.observers.Observer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Model implements GraphicManager, IsolineManager, Observable {

    private ArrayList<Observer> observers = new ArrayList<>();

    private IsolineDrawer isolineDrawer = new IsolineDrawer();
    private GraphicDrawer graphicDrawer = new GraphicDrawer();

    private int colorsCnt;

    private BufferedImage sourceImage;

    @Override
    public void createGraphic(boolean gradient) {
        sourceImage = new BufferedImage(Globals.WIDTH, Globals.HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        graphicDrawer.createGraphic(sourceImage, gradient);
        for(Observer observer : observers){
            observer.setImage(sourceImage);
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
    public void drawIsolines() {
        double step = (graphicDrawer.getMaxZ() - graphicDrawer.getMinZ()) / colorsCnt;
        for(int i = 0; i < colorsCnt; ++i){
            isolineDrawer.drawIsoline(sourceImage, graphicDrawer.getMinZ() + step * i);
        }
        for(Observer observer : observers){
            observer.setImage(sourceImage);
        }

    }

    @Override
    public void setWidthInSquares(int width) {
        isolineDrawer.setWidthInSquares(width);
    }

    @Override
    public void setHeightInSquares(int height) {
        isolineDrawer.setHeightInSquares(height);
    }

    @Override
    public void setDefinitionArea(double minX, double minY, double maxX, double maxY) {
        graphicDrawer.setDefinitionArea(minX, minY, maxX, maxY);
        isolineDrawer.setDefinitionArea(minX, minY, maxX, maxY);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}
