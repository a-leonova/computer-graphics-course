package com.nsu.fit.leonova.controller;

import com.nsu.fit.leonova.model.IsolineManager;

public class Controller implements LogicController {
    private IsolineManager isolineManager;
    private boolean gradient = false;

    public void setIsolineManager(IsolineManager isolineManager) {
        this.isolineManager = isolineManager;
    }

    @Override
    public void createGraphic() {
        isolineManager.createGraphic(gradient);
    }

    @Override
    public void createLegend() {
        isolineManager.createLegend(gradient);
    }

    @Override
    public void gradientWasPressed() {
        gradient = !gradient;
        isolineManager.createGraphic(gradient);
        isolineManager.createLegend(gradient);
    }
}
