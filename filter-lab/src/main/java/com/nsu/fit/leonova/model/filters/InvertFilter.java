package com.nsu.fit.leonova.model.filters;

import com.nsu.fit.leonova.model.SafeColor;

import java.awt.image.BufferedImage;

public class InvertFilter implements Filter {
    @Override
    public BufferedImage applyFilter(BufferedImage original) {
        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for(int i = 0; i < original.getHeight(); ++i){
            for(int j = 0; j < original.getWidth(); ++j){
                SafeColor color = new SafeColor(original.getRGB(i, j));
                color.setRgb(1 - color.getRed(),
                        1 - color.getGreen(),
                        1 - color.getBlue());
                filteredImage.setRGB(i, j, color.getIntRgb());
            }
        }
        return filteredImage;
    }
}
