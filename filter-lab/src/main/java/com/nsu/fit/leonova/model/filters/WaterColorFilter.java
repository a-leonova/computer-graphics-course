package com.nsu.fit.leonova.model.filters;

import com.nsu.fit.leonova.model.SafeColor;
import com.nsu.fit.leonova.model.Utils;

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
        ArrayList<Double> redColors = new ArrayList<>(neigbourWidth * neighbourHeight);
        ArrayList<Double> greenColors = new ArrayList<>(neigbourWidth * neighbourHeight);
        ArrayList<Double> blueColors = new ArrayList<>(neigbourWidth * neighbourHeight);

        int halfWidth = neigbourWidth / 2;
        int halfHeight = neighbourHeight / 2;
        for (int i = -halfHeight; i <= halfHeight; ++i) {
            for (int j = -halfWidth; j <= halfWidth; ++j) {
                Point safePoint = Utils.getSafePoint(center.x + i, center.y + j, source);
                SafeColor color = new SafeColor(source.getRGB(safePoint.x, safePoint.y));

                redColors.add(color.getRed());
                greenColors.add(color.getGreen());
                blueColors.add(color.getBlue());
            }
        }

        redColors.sort(Double::compareTo);
        greenColors.sort(Double::compareTo);
        blueColors.sort(Double::compareTo);

        SafeColor color = new SafeColor(redColors.get(redColors.size() / 2 + 1),
                greenColors.get(greenColors.size() / 2 + 1),
                blueColors.get(blueColors.size() / 2 + 1));

        return color.getIntRgb();
    }

}
