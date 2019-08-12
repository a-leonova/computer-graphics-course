package com.nsu.fit.leonova.controller;

import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.model.*;
import com.nsu.fit.leonova.model.graphicProvider.GraphicValues;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Controller implements LogicController, ImageController, FileController {
    private InfoManager infoManager;
    private GraphicManager graphicManager;
    private IsolineManager isolineManager;

    private boolean gradient = false;

    private int imageWidth, imageHeight, legendWidth, legendHeight;


    public void setInfoManager(InfoManager infoManager) {
        this.infoManager = infoManager;
    }

    public void setGraphicManager(GraphicManager graphicManager) {
        this.graphicManager = graphicManager;
    }

    public void setIsolineManager(IsolineManager isolineManager) {
        this.isolineManager = isolineManager;
    }

    @Override
    public void createGraphic(int width, int height) {
        imageWidth = width;
        imageHeight = height;
        graphicManager.createGraphic(gradient, width, height);
    }

    @Override
    public void createLegend(int width, int height) {
        legendWidth = width;
        legendHeight = height;
        graphicManager.createLegend(gradient, width, height);
    }

    @Override
    public void gradientWasPressed() {
        gradient = !gradient;
        graphicManager.createGraphic(gradient, imageWidth, imageHeight);
        graphicManager.createLegend(gradient, legendWidth, legendHeight);
    }

    @Override
    public void drawAllLevelIsolines() {
        isolineManager.drawAllLevelIsolines();
    }

    @Override
    public void eraseIsolines() {
        isolineManager.removeIsolines();
        graphicManager.createGraphic(gradient, imageWidth, imageHeight);
    }

    @Override
    public void drawNet() {
        graphicManager.drawNet();
    }

    @Override
    public void pivotPoints() {
        graphicManager.pivotPoints();
    }

    @Override
    public void setParameters(GraphicValues graphicValues, int k, int m) {
        isolineManager.setNet(k, m);
        graphicManager.setDefinitionArea(graphicValues);
        graphicManager.createGraphic(gradient, imageWidth, imageHeight);
    }

    @Override
    public void resizeImage(int width, int height) {
        imageWidth = width;
        imageHeight = height;
        legendHeight = height;
        createGraphic(width, height);
        createLegend(legendWidth, height);
    }

    @Override
    public void imageWasClicked(Point pressedPixel) {
        isolineManager.clickedIsoline(pressedPixel);
    }

    @Override
    public void imageWasDragged(Point draggedPixel) {
        isolineManager.draggedIsoline(draggedPixel);
    }

    @Override
    public void imageWasMoved(Point pixel) {
        graphicManager.pixelToCoordinate(pixel);
    }

    @Override
    public void openFile(File file) {
        try (Scanner scanner = new Scanner(file)) {
            String[] netValues = readNextNumbers(scanner, 2);
            int k = Integer.parseInt(netValues[0]);
            int m = Integer.parseInt(netValues[1]);
            if(k < Globals.MIN_NET || k > Globals.MAX_NET || m < Globals.MIN_NET || m > Globals.MAX_NET){
                infoManager.showError("Check parameters of net! They must be in range [1; 1000]");
            }
            SafeColor[] colors = readColors(scanner);
            SafeColor isolineColor = readNextColor(scanner);
            graphicManager.setColorsRGB(colors);
            isolineManager.setNet(k, m);
            isolineManager.setIsolineColor(isolineColor);
            createGraphic(imageWidth, imageHeight);
            createLegend(legendWidth, legendHeight);
        } catch (FileNotFoundException | IllegalArgumentException e) {
            infoManager.showError(e.getMessage());
        }
    }

    private SafeColor[] readColors(Scanner scanner){
        int numOfColor = Integer.parseInt(readNextNumbers(scanner, 1)[0]);
        SafeColor[] colors = new SafeColor[numOfColor];
        for(int i =0; i < numOfColor; ++i){
            colors[i] = readNextColor(scanner);
        }
        return colors;
    }

    private SafeColor readNextColor(Scanner scanner) {
        String[] numbers = readNextNumbers(scanner, 3);
        int r = Integer.parseInt(numbers[0]);
        int g = Integer.parseInt(numbers[1]);
        int b = Integer.parseInt(numbers[2]);
        if(r < 0 || r > 255 || g < 0 || g > 255 | b < 0 || b > 255){
            throw new IllegalArgumentException("Wrong color value in configuration file. They must be in range [0; 255]");
        }
        return new SafeColor(r, g, b);
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
