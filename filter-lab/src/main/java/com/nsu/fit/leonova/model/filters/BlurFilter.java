package com.nsu.fit.leonova.model.filters;

import com.nsu.fit.leonova.model.SafeColor;
import com.nsu.fit.leonova.model.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BlurFilter implements Filter {

    private final int MATRIX_SIZE = 5;
    private final int[][] gaussianMatrix = {
            {1, 2, 3, 2, 1},
            {2, 4, 5, 4, 2},
            {3, 5, 6, 5, 3},
            {2, 4, 5, 4, 2},
            {1, 2, 3, 2, 1}
    };
    private final double normalizationCoeff = 1.0f / 74.0;


    @Override
    public BufferedImage applyFilter(BufferedImage original, double[] parameters) {
        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < original.getHeight(); ++i) {
            for (int j = 0; j < original.getWidth(); ++j) {
                int newRgb = countNewBlurRgb(original, new Point(i, j), gaussianMatrix, normalizationCoeff, MATRIX_SIZE);
                filteredImage.setRGB(i, j, newRgb);
            }
        }
        return filteredImage;
    }

    private int countNewBlurRgb(BufferedImage source, Point center, int[][] gaussianMatrix, double normalizationCoeff, int matrixSize) {

        SafeColor finalColor = new SafeColor();

        int half = matrixSize / 2;
        for (int i = -half; i <= half; ++i) {
            for (int j = -half; j <= half; ++j) {
                Point safePoint = Utils.getSafePoint(center.x + i, center.y + j, source);
                SafeColor color = new SafeColor(source.getRGB(safePoint.x, safePoint.y));

                int m = i + half;
                int n = j + half;

                finalColor.setRgb(finalColor.getRed() + color.getRed() * gaussianMatrix[m][n],
                         finalColor.getGreen() + color.getGreen() * gaussianMatrix[m][n],
                         finalColor.getBlue() + color.getBlue() * gaussianMatrix[m][n]);
            }
        }

        finalColor.setRgb(finalColor.getRed() * normalizationCoeff,
                finalColor.getGreen() * normalizationCoeff,
                finalColor.getBlue() * normalizationCoeff);


        return finalColor.getIntRgb();
    }
}
