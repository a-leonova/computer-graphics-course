package com.nsu.fit.leonova.model.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DesaturateFilter implements Filter {
    @Override
    public BufferedImage applyFilter(BufferedImage original) {
        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for(int i = 0; i < original.getHeight(); ++i){
            for(int j = 0; j < original.getWidth(); ++j){
                int rgb = original.getRGB(i, j);
                int red = (rgb >> 16) & 0x000000FF;
                int green = (rgb >>8 ) & 0x000000FF;
                int blue = (rgb) & 0x000000FF;

                //Luma formula
                int gray =  (int)Math.round(red * 0.2126 + green * 0.7152 + blue * 0.0722);
                int grayColor = (gray << 16 | gray << 8 | gray);
                filteredImage.setRGB(i, j, grayColor);
            }
        }
        return filteredImage;
    }
}
