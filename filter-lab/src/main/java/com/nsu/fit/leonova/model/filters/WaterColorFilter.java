package com.nsu.fit.leonova.model.filters;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class WaterColorFilter implements Filter {

    private SharpenFilter sharpenFilter = new SharpenFilter();


    @Override
    public BufferedImage applyFilter(BufferedImage original) {
        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < original.getHeight(); ++i) {
            for (int j = 0; j < original.getWidth(); ++j) {
                int newRgb = countNewMedianRgb(original, new Point(i, j), 5, 5);
                filteredImage.setRGB(i, j, newRgb);
            }
        }
        filteredImage = sharpenFilter.applyFilter(filteredImage);
        return filteredImage;

    }

    private int countNewMedianRgb(BufferedImage source, Point center, int neigbourWidth, int neighbourHeight) {

        ArrayList<Integer> redColors = new ArrayList<>(neigbourWidth * neighbourHeight);
        ArrayList<Integer> greenColors = new ArrayList<>(neigbourWidth * neighbourHeight);
        ArrayList<Integer> blueColors = new ArrayList<>(neigbourWidth * neighbourHeight);

        int halfWidth = neigbourWidth / 2;
        int halfHeight = neighbourHeight / 2;
        for (int i = -halfHeight; i <= halfHeight; ++i) {
            for (int j = -halfWidth; j <= halfWidth; ++j) {
                int x0 = center.x + i;
                x0 = x0 < 0 ? 0 : x0 >= source.getWidth() ? source.getWidth() - 1 : x0;

                int y0 = center.y + j;
                y0 = y0 < 0 ? 0 : y0 >= source.getHeight() ? source.getHeight() - 1 : y0;

                int rgb = source.getRGB(x0, y0);
                int red = (rgb >> 16) & 0x000000FF;
                int green = (rgb >> 8) & 0x000000FF;
                int blue = (rgb) & 0x000000FF;

                redColors.add(red);
                greenColors.add(green);
                blueColors.add(blue);
            }
        }

        redColors.sort(Integer::compareTo);
        greenColors.sort(Integer::compareTo);
        blueColors.sort(Integer::compareTo);

        int newRed = redColors.get(redColors.size() / 2 + 1);
        int newGreen = greenColors.get(greenColors.size() / 2 + 1);
        int newBlue = blueColors.get(blueColors.size() / 2 + 1);

        return (newRed << 16 | newGreen << 8 | newBlue);
    }

}
