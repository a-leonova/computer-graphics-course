package com.leonova.fit.nsu.controller;

import com.leonova.fit.nsu.model.FieldModel;
import com.leonova.fit.nsu.model.GameOptions;
import com.leonova.fit.nsu.model.Position;
import com.leonova.fit.nsu.view.GraphicsOptions;

public class FieldController implements GameController {
    private final static int TIME_TO_SLEEP = 1000;
    private FieldModel field;
    private GameOptions gameOptions;
    private boolean displayImpact = false;
    private boolean running = false;

    public FieldController(GameOptions gameOptions) {
        this.gameOptions = gameOptions;
    }

    public void setField(FieldModel field) {
        this.field = field;
    }


    @Override
    public void clearField() {
        field.clearField();
    }

    @Override
    public void nextStep() {
        field.nextStep();
    }

    @Override
    public void run() {
        running = !running;
        if(running){
            Thread timer = new Thread(){
                @Override
                public void run(){
                    super.run();
                    while (running){
                        try {
                            field.nextStep();
                            sleep(TIME_TO_SLEEP);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            timer.start();
        }
    }

    @Override
    public void setXor() {
        gameOptions.setModeXor(true);
    }

    @Override
    public void setReplace() {
        gameOptions.setModeXor(false);
    }

    @Override
    public void displayImpact() {
        field.impactPressed();
    }

    @Override
    public void pressCell(Position position) {
        field.pressedCell(position);
    }

    @Override
    public void newOptions(GameOptions gameOptions, GraphicsOptions graphicsOptions) {
        gameOptions = gameOptions;
        field.newOptions(gameOptions, graphicsOptions);
    }
}
