package com.leonova.fit.nsu.controller;

import com.leonova.fit.nsu.model.FieldModel;
import com.leonova.fit.nsu.model.GameOptions;
import com.leonova.fit.nsu.model.Position;

public class FieldController implements GameController {
    private FieldModel field;
    private GameOptions gameOptions;
    private boolean displayImpact = false;

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
    public void setXor() {
        gameOptions.setModeXor(true);
    }

    @Override
    public void setReplace() {
        gameOptions.setModeXor(false);
    }

    @Override
    public void displayImpact() {
        displayImpact = !displayImpact;
        if(displayImpact){
            field.displayImpact();
        }
        else {
            field.stopDisplayImpact();
        }
    }

    @Override
    public void pressCell(Position position) {
        field.pressedCell(position);
    }
}
