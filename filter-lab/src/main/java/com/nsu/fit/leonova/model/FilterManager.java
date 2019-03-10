package com.nsu.fit.leonova.model;

import java.awt.image.BufferedImage;

public interface FilterManager {
    void useFilterWithImage(BufferedImage image, FiltersType filter);
    void setWorkingImage(BufferedImage image);
}
