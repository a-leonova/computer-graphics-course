package com.nsu.fit.leonova.model.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GammaFilter implements Filter {

    private final double HARDCODE_P = 1.5;

    @Override
    public BufferedImage applyFilter(BufferedImage original) {

        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < original.getHeight(); ++i) {
            for (int j = 0; j < original.getWidth(); ++j) {

                int newRgb = countNewGammaGRB(original, new Point(i, j), HARDCODE_P);
                filteredImage.setRGB(i, j, newRgb);
            }
        }
        return filteredImage;
    }

    private int countNewGammaGRB(BufferedImage source, Point pixel, double power){
        int rgb = source.getRGB(pixel.x, pixel.y);

        int red = (rgb >> 16) & 0x000000FF;
        int green = (rgb >> 8) & 0x000000FF;
        int blue = (rgb) & 0x000000FF;

        double red1 = red / 255.0;
        double green1 = green / 255.0;
        double blue1 = blue / 255.0;

        int newRed = (int)Math.round(Math.pow(red1, power) * 255);
        int newGreen = (int)Math.round(Math.pow(green1, power) * 255);
        int newBlue = (int)Math.round(Math.pow(blue1, power) * 255);

        return (Math.round(newRed) << 16 | Math.round(newGreen) << 8 | Math.round(newBlue));

    }
}
