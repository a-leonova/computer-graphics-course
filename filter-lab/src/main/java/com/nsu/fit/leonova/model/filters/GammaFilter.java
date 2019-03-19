package com.nsu.fit.leonova.model.filters;

import com.nsu.fit.leonova.model.SafeColor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GammaFilter implements Filter {

    private double power;

    @Override
    public BufferedImage applyFilter(BufferedImage original, double[] parameters) {
        power = parameters[0];
        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < original.getHeight(); ++i) {
            for (int j = 0; j < original.getWidth(); ++j) {
                int newRgb = countNewGammaGRB(original, new Point(i, j), power);
                filteredImage.setRGB(i, j, newRgb);
            }
        }
        return filteredImage;
    }

    private int countNewGammaGRB(BufferedImage source, Point pixel, double power){
        SafeColor color = new SafeColor(source.getRGB(pixel.x, pixel.y));

        color.setRgb(Math.pow(color.getRed(), power),
                Math.pow(color.getGreen(), power),
                Math.pow(color.getBlue(), power));

        return color.getIntRgb();
    }
}
