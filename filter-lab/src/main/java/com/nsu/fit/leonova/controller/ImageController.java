package com.nsu.fit.leonova.controller;

import com.nsu.fit.leonova.model.FiltersType;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface ImageController {
    void cropImage(Point leftTop, int width, int height);
    //void setWorkingImage(BufferedImage filteredImage);
    void filteredImageAsWorking();
    void filterImage(FiltersType filterType);
}
