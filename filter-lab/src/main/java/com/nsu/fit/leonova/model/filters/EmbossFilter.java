package com.nsu.fit.leonova.model.filters;

import com.nsu.fit.leonova.model.SafeFloatColor;
import com.nsu.fit.leonova.model.SafeIntColor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EmbossFilter implements Filter {

    private final int MATRIX_SIZE = 3;
    private int[][] gaussianMatrix = {
        {0, 1, 0},
        {1, 0, -1},
        {0, -1, 0}
};

    @Override
    public BufferedImage applyFilter(BufferedImage original) {
        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < original.getHeight(); ++i) {
            for (int j = 0; j < original.getWidth(); ++j) {
                int newRgb = countNewEmbossRgb(original, new Point(i, j), gaussianMatrix, MATRIX_SIZE);
                filteredImage.setRGB(i, j, newRgb);
            }
        }
        return filteredImage;
    }

    private int countNewEmbossRgb(BufferedImage source, Point center, int[][] gaussianMatrix, int matrixSize) {
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

        finalColor.setColor(finalColor.getRed() + 128,
                finalColor.getGreen() + 128,
                finalColor.getBlue() + 128);

        return finalColor.getIntColor();
    }
}
