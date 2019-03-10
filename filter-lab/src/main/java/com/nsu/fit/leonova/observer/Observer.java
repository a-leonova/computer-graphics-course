package com.nsu.fit.leonova.observer;

import java.awt.image.BufferedImage;
import java.util.HashSet;

public interface Observer {
    void setFilteredImage(BufferedImage image);
    void setWorkingImage(BufferedImage image);

}
