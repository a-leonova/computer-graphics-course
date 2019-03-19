package com.nsu.fit.leonova.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public interface ImageConsumer {
    void useFilterWithImage(FiltersType filter, double[] parameters);
    void setSourcePicture(BufferedImage sourcePicture);
    //void setWorkingPicture(BufferedImage workingPicture);
    void filteredImageAsWorking();
    //void setFilteredPicture();
    void cropPicture(Point leftTop, int width, int height);
    void emissionWasPressed();
    void absorptionWasPressed();
    void openConfigFile(File file);
}
