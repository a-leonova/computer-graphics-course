package com.nsu.fit.leonova.model.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BlurFilter implements Filter {

    private final int MATRIX_SIZE = 5;
    private int[][] gaussianMatrix = {
            {1,  2,  3,  2,  1},
            {2, 4, 5, 4,  2,},
            {3, 5, 6, 5,  3,},
            {2, 4, 5, 4,  2},
            {1,  2,  3,  2,  1}
    };
    private double normalizationCoeff = 1.0 / 74.0;


    @Override
    public BufferedImage applyFilter(BufferedImage original) {
        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for(int i = 0; i < original.getHeight(); ++i){
            for(int j = 0; j < original.getWidth(); ++j){
                int newRgb = countNewBlurRgb(original, new Point(i, j), gaussianMatrix, normalizationCoeff,MATRIX_SIZE);
                filteredImage.setRGB(i, j, newRgb);
            }
        }
        return filteredImage;
    }

    private int countNewBlurRgb(BufferedImage source, Point center, int[][] gaussianMatrix, double normalizationCoeff, int matrixSize){

        float finalRed = 0;
        float finalGreen = 0;
        float finalBlue = 0;
        int half = matrixSize / 2;
        for(int i = -half; i <= half; ++i){
            for(int j = -half; j <= half; ++j){
                int x0 = center.x + i;
                x0 = x0 < 0 ? 0 : x0 >= source.getWidth() ? source.getWidth() - 1 : x0;

                int y0 = center.y + j;
                y0 = y0 < 0 ? 0 : y0 >= source.getHeight() ? source.getHeight() - 1 : y0;
                int rgb = source.getRGB(x0, y0);
                int red = (rgb >> 16) & 0x000000FF;
                int green = (rgb >>8 ) & 0x000000FF;
                int blue = (rgb) & 0x000000FF;

                int m = i + half;
                int n = j + half;
                finalRed += (red * normalizationCoeff * gaussianMatrix[m][n]);
                finalGreen += (green * normalizationCoeff * gaussianMatrix[m][n]);
                finalBlue += (blue * normalizationCoeff * gaussianMatrix[m][n]);
            }
        }
        int newRgb = (Math.round(finalRed) << 16 | Math.round(finalGreen) << 8 | Math.round(finalBlue));
        return newRgb;
    }
}
