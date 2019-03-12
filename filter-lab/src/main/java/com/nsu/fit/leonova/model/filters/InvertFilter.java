package com.nsu.fit.leonova.model.filters;

import java.awt.image.BufferedImage;

public class InvertFilter implements Filter {
    @Override
    public BufferedImage applyFilter(BufferedImage original) {
        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for(int i = 0; i < original.getHeight(); ++i){
            for(int j = 0; j < original.getWidth(); ++j){
                int rgb = original.getRGB(i, j);
                int red = (rgb >> 16) & 0x000000FF;
                int green = (rgb >>8 ) & 0x000000FF;
                int blue = (rgb) & 0x000000FF;

                int newRed = 255 - red;
                int newGreen = 255 - green;
                int newBlue = 255 - blue;

                int grayColor = (newRed << 16 | newGreen << 8 | newBlue);
                filteredImage.setRGB(i, j, grayColor);
            }
        }
        return filteredImage;
    }
}
