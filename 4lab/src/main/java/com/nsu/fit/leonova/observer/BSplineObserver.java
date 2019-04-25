package com.nsu.fit.leonova.observer;

import com.nsu.fit.leonova.model.bspline.SplineParameters;

import java.awt.image.BufferedImage;

public interface BSplineObserver {
    void setBSpline(BufferedImage bSpline);
    void setBSplineParameters(SplineParameters parameters);
}
