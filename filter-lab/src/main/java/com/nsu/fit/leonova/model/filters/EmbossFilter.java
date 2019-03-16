package com.nsu.fit.leonova.model.filters;

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
        float finalRed = 0;
        float finalGreen = 0;
        float finalBlue = 0;
        int half = matrixSize / 2;
        for (int i = -half; i <= half; ++i) {
            for (int j = -half; j <= half; ++j) {
                int x0 = center.x + i;
                x0 = x0 < 0 ? 0 : x0 >= source.getWidth() ? source.getWidth() - 1 : x0;

                int y0 = center.y + j;
                y0 = y0 < 0 ? 0 : y0 >= source.getHeight() ? source.getHeight() - 1 : y0;
                int rgb = source.getRGB(x0, y0);
                int red = (rgb >> 16) & 0x000000FF;
                int green = (rgb >> 8) & 0x000000FF;
                int blue = (rgb) & 0x000000FF;

                int m = i + half;
                int n = j + half;
                finalRed += (red * gaussianMatrix[m][n]);
                finalGreen += (green * gaussianMatrix[m][n]);
                finalBlue += (blue * gaussianMatrix[m][n]);

            }
        }

        finalRed += 128;
        finalGreen += 128;
        finalBlue += 128;

        finalRed = finalRed < 0 ? 0 : finalRed > 255 ? 255 : finalRed;
        finalBlue = finalBlue < 0 ? 0 : finalBlue > 255 ? 255 : finalBlue;
        finalGreen = finalGreen < 0 ? 0 : finalGreen > 255 ? 255 : finalGreen;

        int newRgb = (Math.round(finalRed) << 16 | Math.round(finalGreen) << 8 | Math.round(finalBlue));
        return newRgb;
    }
}
