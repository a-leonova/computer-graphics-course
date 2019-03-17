package com.nsu.fit.leonova.model;

public class SafeColor {
    private final double MIN_VALUE = 0;
    private final double MAX_VALUE = 255;

    private double red;
    private double green;
    private double blue;

    public SafeColor() {
    }

    public SafeColor(int rgb) {
        red = ((rgb >> 16) & 0x000000FF) / MAX_VALUE;
        green = ((rgb >> 8) & 0x000000FF) / MAX_VALUE;
        blue = ((rgb) & 0x000000FF) / MAX_VALUE;
    }

    public SafeColor(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public double getRed() {
        return red;
    }

    public double getGreen() {
        return green;
    }

    public double getBlue() {
        return blue;
    }

    public void setRgb(double red, double green, double blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getIntRgb(){
        int r = (int)Math.round(MAX_VALUE * red);
        int g = (int)Math.round(MAX_VALUE * green);
        int b = (int)Math.round(MAX_VALUE * blue);

        r = r < 0 ? 0 : r > 255 ? 255 : r;
        g = g < 0 ? 0 : g > 255 ? 255 : g;
        b = b < 0 ? 0 : b > 255 ? 255 : b;
        return r << 16 | g << 8 | b;
    }
}
