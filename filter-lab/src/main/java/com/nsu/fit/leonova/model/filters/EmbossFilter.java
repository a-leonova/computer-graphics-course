package com.nsu.fit.leonova.model.filters;

import com.nsu.fit.leonova.model.SafeColor;
import com.nsu.fit.leonova.model.Utils;

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
    public BufferedImage applyFilter(BufferedImage original, double[] parameters) {
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

        finalColor.setRgb(finalColor.getRed() + 0.5,
                finalColor.getGreen() + 0.5,
                finalColor.getBlue() + 0.5);

        return finalColor.getIntRgb();
    }
}
