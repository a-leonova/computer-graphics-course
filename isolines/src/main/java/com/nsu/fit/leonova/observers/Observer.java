package com.nsu.fit.leonova.observers;

import java.awt.image.BufferedImage;

public interface Observer {
    void setImage(BufferedImage image);
    void setLegend(BufferedImage legend);
}
