package com.nsu.fit.leonova.model.filters;

import com.nsu.fit.leonova.model.SafeColor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GammaFilter implements Filter {

    private final float HARDCODE_P = 1.5f;

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

    private int countNewGammaGRB(BufferedImage source, Point pixel, float power){
        SafeColor color = new SafeColor(source.getRGB(pixel.x, pixel.y));

        color.setRgb(Math.pow(color.getRed(), power),
                Math.pow(color.getGreen(), power),
                Math.pow(color.getBlue(), power));

        return color.getIntRgb();
    }
}
