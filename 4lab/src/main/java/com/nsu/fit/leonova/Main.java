package com.nsu.fit.leonova;

import com.nsu.fit.leonova.controller.Controller;
import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.model.World3DImpl;
import com.nsu.fit.leonova.view.MainWindow;

public class Main {
    public static void main(String[] args) {
        World3DImpl world = new World3DImpl();
        MainWindow mainWindow = new MainWindow(Globals.IMAGE_WIDTH, Globals.IMAGE_HEIGHT);
        Controller controller = new Controller();

        controller.setbSplineCreator(world);
        controller.setWorld3D(world);
        mainWindow.setBSplineController(controller);
        mainWindow.setWorldController(controller);
        world.addObserver(mainWindow);

    }
}
