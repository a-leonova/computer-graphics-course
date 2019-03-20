package com.nsu.fit.leonova.observer;

import java.awt.image.BufferedImage;
import java.util.HashSet;

public interface Observer {
    void setFilteredImage(BufferedImage image);
    void setWorkingImage(BufferedImage image);
    void setSourceImage(BufferedImage image);

    void removeAllImages();

    void setOneGraphic(double[] graphic);
    void setManyGraphics(double[][] graphics, int width, int height);



}
