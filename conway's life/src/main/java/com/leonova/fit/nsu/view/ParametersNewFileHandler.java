package com.leonova.fit.nsu.view;

import com.leonova.fit.nsu.controller.GameController;
import com.leonova.fit.nsu.model.GameOptions;

public class ParametersNewFileHandler implements ParametersWindowHandler{
    private GameController gameController;

    public ParametersNewFileHandler(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void handle(GraphicsOptions graphicsOptions, GameOptions gameOptions) {
        gameController.createNewField(gameOptions, graphicsOptions);
    }
}
