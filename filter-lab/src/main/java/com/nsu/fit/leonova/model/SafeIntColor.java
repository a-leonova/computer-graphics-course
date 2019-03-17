package com.nsu.fit.leonova.model;

public class SafeIntColor {
    private int red;
    private int green;
    private int blue;

    public SafeIntColor(int rgb) {
        red = (rgb >> 16) & 0x000000FF;
        green = (rgb >> 8) & 0x000000FF;
        blue = (rgb) & 0x000000FF;
    }

    public SafeIntColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getSafeIntColor(){
        red = red < 0 ? 0 : red > 255 ? 255 : red;
        blue = blue < 0 ? 0 : blue > 255 ? 255 : blue;
        green = green < 0 ? 0 : green > 255 ? 255 : green;
        return red << 16 | green << 8 | blue;
    }

    public SafeIntColor(){

    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }
}
