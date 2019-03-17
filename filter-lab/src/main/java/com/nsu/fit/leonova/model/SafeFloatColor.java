package com.nsu.fit.leonova.model;

public class SafeFloatColor {

    private float red = 0;
    private float green = 0;
    private float blue = 0;

    public SafeFloatColor() {
    }

    public SafeFloatColor(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public void setColor(float newRed, float newGreen, float newBlue) {
        red = newRed;
        green = newGreen;
        blue = newBlue;
    }

    public int getIntColor(){
        red = red < 0 ? 0 : red > 255 ? 255 : red;
        blue = blue < 0 ? 0 : blue > 255 ? 255 : blue;
        green = green < 0 ? 0 : green > 255 ? 255 : green;

        return (Math.round(red) << 16 | Math.round(green) << 8 | Math.round(blue));
    }

    public float getRed() {
        return red;
    }

    public void setRed(float red) {
        this.red = red;
    }

    public float getGreen() {
        return green;
    }

    public void setGreen(float green) {
        this.green = green;
    }

    public float getBlue() {
        return blue;
    }

    public void setBlue(float blue) {
        this.blue = blue;
    }
}
