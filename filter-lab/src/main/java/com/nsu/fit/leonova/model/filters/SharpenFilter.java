package com.nsu.fit.leonova.model.filters;

import com.nsu.fit.leonova.model.SafeFloatColor;
import com.nsu.fit.leonova.model.SafeIntColor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SharpenFilter implements Filter {

    private final int MATRIX_SIZE = 5;
    //unsharp masking.
    private final int[][] GAUSSIAN_MATRIX = {
            {1, 4, 6, 4, 1},
            {4, 16, 24, 16, 24},
            {6, 24, -476, 24, 6},
            {4, 16, 24, 16, 4},
            {1, 4, 6, 4, 1}
    };
    //usual masking.
//    private int[][] GAUSSIAN_MATRIX = {
//        {0, -1, 0},
//        {-1, 5, -1},
//        {0, -1, 0}
//};
    private final float NORMALIZATION_COEFFICIENT = -1.0f / 256.0f;

    @Override
    public BufferedImage applyFilter(BufferedImage original) {
        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < original.getHeight(); ++i) {
            for (int j = 0; j < original.getWidth(); ++j) {
                int newRgb = countNewSharpRgb(original, new Point(i, j), GAUSSIAN_MATRIX, NORMALIZATION_COEFFICIENT, MATRIX_SIZE);
                filteredImage.setRGB(i, j, newRgb);
            }
        }
        return filteredImage;
    }

    private int countNewSharpRgb(BufferedImage source, Point center, int[][] gaussianMatrix, float normalizationCoeff, int matrixSize) {
        SafeFloatColor finalColor = new SafeFloatColor();
        int half = matrixSize / 2;
        for (int i = -half; i <= half; ++i) {
            for (int j = -half; j <= half; ++j) {
                int x0 = center.x + i;
                x0 = x0 < 0 ? 0 : x0 >= source.getWidth() ? source.getWidth() - 1 : x0;

                int y0 = center.y + j;
                y0 = y0 < 0 ? 0 : y0 >= source.getHeight() ? source.getHeight() - 1 : y0;

                SafeIntColor color = new SafeIntColor(source.getRGB(x0, y0));

                int m = i + half;
                int n = j + half;
                finalColor.setColor(finalColor.getRed() + color.getRed() * gaussianMatrix[m][n],
                        finalColor.getGreen() + color.getGreen() * gaussianMatrix[m][n],
                        finalColor.getBlue() + color.getBlue() * gaussianMatrix[m][n]);
            }
        }

        finalColor.setColor(finalColor.getRed() * normalizationCoeff,
                finalColor.getGreen() * normalizationCoeff,
                finalColor.getBlue() * normalizationCoeff);

        return finalColor.getIntColor();
    }
}
