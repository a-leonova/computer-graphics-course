package com.nsu.fit.leonova.model.filters;

import java.awt.image.BufferedImage;

public interface Filter {
    BufferedImage applyFilter(BufferedImage original, double[] parameters);
}
