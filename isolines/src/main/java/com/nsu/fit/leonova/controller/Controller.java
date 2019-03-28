package com.nsu.fit.leonova.controller;

import com.nsu.fit.leonova.model.IsolineManager;

public class Controller implements LogicController {
    private IsolineManager isolineManager;

    public void setIsolineManager(IsolineManager isolineManager) {
        this.isolineManager = isolineManager;
    }

    @Override
    public void createGraphic() {
        isolineManager.createGraphic();
    }

    @Override
    public void createLegend() {
        isolineManager.createLegend();
    }
}
