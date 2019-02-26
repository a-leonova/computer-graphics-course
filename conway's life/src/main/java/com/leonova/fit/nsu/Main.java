package com.leonova.fit.nsu;

import com.leonova.fit.nsu.controller.FieldController;
import com.leonova.fit.nsu.model.Field;
import com.leonova.fit.nsu.model.GameOptions;
import com.leonova.fit.nsu.view.GraphicsOptions;
import com.leonova.fit.nsu.view.WindowView;


public class Main {


    public static void main(String[] args) {
        GraphicsOptions graphicsOptions = new GraphicsOptions(5, 5, 1, 20);
        GameOptions gameOptions = new GameOptions();

        FieldController controller = new FieldController(gameOptions);
        Field field = new Field(graphicsOptions.getCellsInRow(), graphicsOptions.getCellsInColumn(), gameOptions);
        WindowView view = new WindowView(graphicsOptions);
        view.setGameController(controller);
        controller.setField(field);
        field.addObserver(view);

        view.setVisible(true);
    }
}
