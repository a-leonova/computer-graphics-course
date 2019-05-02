package com.nsu.fit.leonova.observer;

import com.nsu.fit.leonova.model.bspline.SplineParameters;

import java.awt.image.BufferedImage;

public interface BSplineObserver {
    void setBSpline(BufferedImage bSpline);
    void setBSplineParameters(SplineParameters parameters);
    void addSpline(String name);
    void removeSpline(int index);
    void changeFigureName(String name, int index);

    void openFrame();
}
