package com.nsu.fit.leonova.model.filters;

import com.nsu.fit.leonova.model.SafeColor;
import com.nsu.fit.leonova.model.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FSDitheringFilter implements Filter {

    private final int matrixSize = 2;
    private final int[][] shifts = {
            {1, 0},
            {-1, 1},
            {0, 1},
            {1, 1}
    };
    private final double[] diffusion = {
            7.0 / 16.0,
            3.0 / 16.0,
            5.0 / 16.0,
            1.0 / 16.0
    };

    private int redValue;
    private int greenValue;
    private int blueValue;

    @Override
    public BufferedImage applyFilter(BufferedImage original, double[] parameters) {
        redValue = (int)parameters[0];
        greenValue = (int)parameters[1];
        blueValue = (int)parameters[2];

        SafeColor[][] errors = new SafeColor[original.getHeight()][original.getWidth()];
        for(int i = 0; i < original.getHeight(); ++i){
            for(int j = 0; j < original.getWidth(); ++j){
                errors[i][j] = new SafeColor();
            }
        }

        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for (int y = 0; y < filteredImage.getHeight(); ++y) {
            for (int x = 0; x < filteredImage.getWidth(); ++x) {
                int color = getDitheringColor(original, new Point(x, y), errors);
                filteredImage.setRGB(x, y, color);
            }
        }
        return filteredImage;
    }

    private int getDitheringColor(BufferedImage source, Point point, SafeColor[][] errors) {
        SafeColor color = new SafeColor(source.getRGB(point.x, point.y));
        double red = color.getRed() + errors[point.x][point.y].getRed();
        double green = color.getGreen() + errors[point.x][point.y].getGreen();
        double blue = color.getBlue() + errors[point.x][point.y].getBlue();

        double newRed = findClosest(red, redValue);
        double newGreen = findClosest(green, greenValue);
        double newBlue = findClosest(blue, blueValue);

        for (int i = 0; i < matrixSize * matrixSize; ++i) {
            int xNeighbour = point.x + shifts[i][0];
            int yNeighbour = point.y + shifts[i][1];
            if (!Utils.isInRangePoint(xNeighbour, yNeighbour, source)) {
                continue;
            }
            SafeColor error = errors[xNeighbour][yNeighbour];
            error.setRgb(
                    error.getRed() + diffusion[i] * (red - newRed),
                    error.getGreen() + diffusion[i] * (green - newGreen),
                    error.getBlue() + diffusion[i] * (blue - newBlue)
            );
        }
        return new SafeColor(newRed, newGreen, newBlue).getIntRgb();
    }

    private double findClosest(double actual, int shadow) {
        double space = 1. / (Math.pow(2, shadow) - 1);
        int n = (int) Math.round(actual / space);
        return trim(n * space);
    }

    private double trim(double c) {
        if (c < 0.0) {
            return 0.0;
        } else if (c > 1.0) {
            return 1.0;
        } else {
            return c;
        }
    }
}
