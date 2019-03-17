package com.nsu.fit.leonova.model.filters;

import com.nsu.fit.leonova.model.SafeIntColor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DesaturateFilter implements Filter {
    @Override
    public BufferedImage applyFilter(BufferedImage original) {
        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for(int i = 0; i < original.getHeight(); ++i){
            for(int j = 0; j < original.getWidth(); ++j){
                SafeIntColor color = new SafeIntColor(original.getRGB(i, j));
                //Luma formula
                int gray =  (int)Math.round(color.getRed() * 0.2126 + color.getGreen() * 0.7152 + color.getBlue() * 0.0722);
                SafeIntColor grayColor = new SafeIntColor(gray, gray, gray);
                filteredImage.setRGB(i, j, grayColor.getSafeIntColor());
            }
        }
        return filteredImage;
    }
}
