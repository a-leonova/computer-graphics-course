package com.nsu.fit.leonova.model.filters;

import com.nsu.fit.leonova.model.SafeFloatColor;
import com.nsu.fit.leonova.model.SafeIntColor;

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
        SafeIntColor color = new SafeIntColor(source.getRGB(pixel.x, pixel.y));
        SafeFloatColor colorFloat = new SafeFloatColor(color.getRed() / 255.0f,
                color.getGreen() / 255.0f,
                color.getBlue() / 255.0f);

        SafeFloatColor newGammaColor = new SafeFloatColor(
                (float)Math.pow(colorFloat.getRed(), power) * 255,
                (float)Math.pow(colorFloat.getGreen(), power) * 255,
                (float)Math.pow(colorFloat.getBlue(), power) * 255);

        return newGammaColor.getIntColor();

    }
}
