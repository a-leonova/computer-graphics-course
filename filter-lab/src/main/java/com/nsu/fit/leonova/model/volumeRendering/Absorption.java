package com.nsu.fit.leonova.model.volumeRendering;

import com.nsu.fit.leonova.model.SafeColor;
import com.nsu.fit.leonova.model.filters.Filter;

import java.awt.image.BufferedImage;

public class Absorption {

    private int x;
    private double value;
    public Absorption(int x, double value) {
        this.x = x;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
