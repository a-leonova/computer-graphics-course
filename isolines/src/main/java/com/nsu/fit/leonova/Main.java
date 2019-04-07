package com.nsu.fit.leonova;

import com.nsu.fit.leonova.controller.Controller;
import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.model.Model;
import com.nsu.fit.leonova.model.graphicProvider.GraphicDrawer;
import com.nsu.fit.leonova.model.SafeColor;
import com.nsu.fit.leonova.view.MainWindow;

public class Main {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow(Globals.WIDTH, Globals.HEIGHT);
        Controller controller = new Controller();
        Model model = new Model();

        controller.setModel(model);
        model.addObserver(mainWindow);
        mainWindow.setLogicController(controller);
        mainWindow.setImageController(controller);
        mainWindow.setFileController(controller);

        model.setDefinitionArea(0, 0, 10, 10);
        model.setColorsRGB(new SafeColor[]{
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
