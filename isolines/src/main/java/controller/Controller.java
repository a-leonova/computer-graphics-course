package controller;

import model.IsolineManager;

public class Controller implements LogicController {
    private IsolineManager isolineManager;

    public void setIsolineManager(IsolineManager isolineManager) {
        this.isolineManager = isolineManager;
    }

    @Override
    public void createGraphic() {
        isolineManager.createGraphic();
    }
}
