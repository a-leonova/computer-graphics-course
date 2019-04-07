package com.nsu.fit.leonova.observers;

import com.nsu.fit.leonova.model.graphicProvider.DoublePoint;

import java.awt.image.BufferedImage;

public interface Observer {
    void setImage(BufferedImage image);
    void setLegend(BufferedImage legend);
    void setCoordinates(DoublePoint coordinates);
}
