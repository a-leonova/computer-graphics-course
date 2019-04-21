package com.nsu.fit.leonova;

import com.nsu.fit.leonova.controller.Controller;
import com.nsu.fit.leonova.model.BSplineImpl;
import com.nsu.fit.leonova.view.MainWindow;

public class Main {
    public static void main(String[] args) {
        BSplineImpl bSpline = new BSplineImpl();
        MainWindow mainWindow = new MainWindow(500, 500);
        Controller controller = new Controller();
        controller.setbSpline(bSpline);
        mainWindow.setBSplineController(controller);
        bSpline.addObserver(mainWindow);

    }
}
