package com.nsu.fit.leonova.model.filters;

import com.nsu.fit.leonova.model.SafeIntColor;

import java.awt.image.BufferedImage;

public class InvertFilter implements Filter {
    @Override
    public BufferedImage applyFilter(BufferedImage original) {
        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for(int i = 0; i < original.getHeight(); ++i){
            for(int j = 0; j < original.getWidth(); ++j){
                SafeIntColor color = new SafeIntColor(original.getRGB(i, j));
                SafeIntColor newColor = new SafeIntColor(
                        255 - color.getRed(),
                        255 - color.getGreen(),
                        255 - color.getBlue());
                filteredImage.setRGB(i, j, newColor.getSafeIntColor());
            }
        }
        return filteredImage;
    }
}
