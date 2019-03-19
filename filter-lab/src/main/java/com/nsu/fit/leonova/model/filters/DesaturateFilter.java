package com.nsu.fit.leonova.model.filters;

import com.nsu.fit.leonova.model.SafeColor;

import java.awt.image.BufferedImage;

public class DesaturateFilter implements Filter {
    @Override
    public BufferedImage applyFilter(BufferedImage original, double[] parameters) {
        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for(int i = 0; i < original.getHeight(); ++i){
            for(int j = 0; j < original.getWidth(); ++j){
                SafeColor color = new SafeColor(original.getRGB(i, j));
                //Luma formula
                double gray =  color.getRed() * 0.2126 + color.getGreen() * 0.7152 + color.getBlue() * 0.0722;
                SafeColor grayColor = new SafeColor(gray, gray, gray);
                filteredImage.setRGB(i, j, grayColor.getIntRgb());
            }
        }
        return filteredImage;
    }
}
