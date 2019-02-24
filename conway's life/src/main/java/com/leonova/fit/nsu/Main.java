package com.leonova.fit.nsu;

import com.leonova.fit.nsu.controller.FieldController;
import com.leonova.fit.nsu.model.Field;
import com.leonova.fit.nsu.model.FieldModel;
import com.leonova.fit.nsu.model.GameOptions;
import com.leonova.fit.nsu.view.GraphicsOptions;
import com.leonova.fit.nsu.view.WindowView;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {
        GraphicsOptions graphicsOptions = new GraphicsOptions(20, 20, 5, 20);
        GameOptions gameOptions = new GameOptions();

        FieldController controller = new FieldController(gameOptions);
        FieldModel field = new Field(graphicsOptions.getCellsInRow(), graphicsOptions.getCellsInColumn(), gameOptions);
        WindowView view = new WindowView(graphicsOptions);

        view.setVisible(true);
    }
}
