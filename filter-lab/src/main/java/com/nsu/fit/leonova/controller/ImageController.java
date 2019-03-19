package com.nsu.fit.leonova.controller;

import com.nsu.fit.leonova.model.FiltersType;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface ImageController {
    void cropImage(Point leftTop, int width, int height);
    void absorptionWasPressed();
    void emissionWasPressed();
    void filteredImageAsWorking();
    void filterImage(FiltersType filterType, double[] parameters);
}
