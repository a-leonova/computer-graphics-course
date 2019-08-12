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

    public SafeColor(int red, int green, int blue){
        this.red = red / MAX_VALUE;
        this.green = green / MAX_VALUE;
        this.blue = blue / MAX_VALUE;
    }

    public SafeColor(double red, double green, double blue, int _){
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


    public SafeColor multiple(double scalar){
        double newRed = red * scalar;
        double newGreen = green * scalar;
        double newBlue = blue * scalar;

        return new SafeColor(newRed, newGreen, newBlue, 0);
    }

    public SafeColor plus(SafeColor color){
        double newRed = red + color.red;
        double newGreen = green + color.green;
        double newBlue = blue + color.blue;

        return new SafeColor(newRed, newGreen, newBlue, 0);
    }
}