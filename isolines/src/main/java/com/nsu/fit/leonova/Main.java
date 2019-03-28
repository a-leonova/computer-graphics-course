package com.nsu.fit.leonova;

import com.nsu.fit.leonova.controller.Controller;
import com.nsu.fit.leonova.model.IsolineManagerImpl;
import com.nsu.fit.leonova.model.SafeColor;
import com.nsu.fit.leonova.view.MainWindow;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow(500, 500);
        Controller controller = new Controller();
        IsolineManagerImpl isolineManager = new IsolineManagerImpl(500, 500);

        controller.setIsolineManager(isolineManager);
        isolineManager.addObserver(mainWindow);
        mainWindow.setLogicController(controller);

        isolineManager.setGraphicArea(-50, 50, -50, 50);
        isolineManager.setColorsRGB(new SafeColor[]{
                new SafeColor(255,0,0),
                new SafeColor(255, 127, 0),
                new SafeColor(255, 255, 0),
                new SafeColor(0, 255, 0),
                new SafeColor(0, 0, 255),
                new SafeColor(139, 0, 255)
        });
        controller.createGraphic();
        controller.createLegend();
        mainWindow.setVisible(true);
    }
}
