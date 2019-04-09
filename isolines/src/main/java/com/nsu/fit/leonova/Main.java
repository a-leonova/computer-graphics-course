package com.nsu.fit.leonova;

import com.nsu.fit.leonova.controller.Controller;
import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.model.Model;
import com.nsu.fit.leonova.model.SafeColor;
import com.nsu.fit.leonova.model.graphicProvider.GraphicValues;
import com.nsu.fit.leonova.view.MainWindow;

public class Main {
    public static void main(String[] args) {
        GraphicValues graphicValues = new GraphicValues(0, 0, 10, 10);
        MainWindow mainWindow = new MainWindow(graphicValues, 60, 60);
        Controller controller = new Controller();
        Model model = new Model(graphicValues, 60, 60);

        controller.setModel(model);
        model.addObserver(mainWindow);
        mainWindow.setLogicController(controller);
        mainWindow.setImageController(controller);
        mainWindow.setFileController(controller);

        model.setColorsRGB(new SafeColor[]{
                new SafeColor(255,0,0),
                new SafeColor(255, 127, 0),
                new SafeColor(255, 255, 0),
                new SafeColor(0, 255, 0),
                new SafeColor(0, 0, 255),
                new SafeColor(139, 0, 255)
        });
        controller.createGraphic(Globals.START_IMAGE_WIDTH, Globals.START_IMAGE_HEIGHT);
        controller.createLegend(Globals.START_LEGEND_WIDTH, Globals.START_LEGEND_HEIGHT);
        mainWindow.setVisible(true);
    }
}
