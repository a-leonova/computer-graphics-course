package com.nsu.fit.leonova.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface ImageConsumer {
    void useFilterWithImage(FiltersType filter);
    void setSourcePicture(BufferedImage sourcePicture);
    //void setWorkingPicture(BufferedImage workingPicture);
    void filteredImageAsWorking();
    //void setFilteredPicture();
    void cropPicture(Point leftTop, int width, int height);
}
