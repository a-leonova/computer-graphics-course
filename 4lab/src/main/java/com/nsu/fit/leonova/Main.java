package com.nsu.fit.leonova;

import com.nsu.fit.leonova.controller.Controller;
import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.model.World3DImpl;
import com.nsu.fit.leonova.view.windows.BSplineWindow;
import com.nsu.fit.leonova.view.windows.WorldWindow;

public class Main {
    public static void main(String[] args) {
        World3DImpl world = new World3DImpl();
        Controller controller = new Controller(world);
        BSplineWindow bSplineWindow = new BSplineWindow(controller);
        WorldWindow worldWindow = new WorldWindow(Globals.IMAGE_WIDTH, Globals.IMAGE_HEIGHT, controller);

        world.addObserver(worldWindow);
        world.addObserver(bSplineWindow);

    }
}
