package com.nsu.fit.leonova.model.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SobelFilter implements Filter {

    private int[][] horizontalMatrix = {
            {-1, -2, -1},
            {0, 0, 0},
            {1, 2, 1}
    };

    private int[][] verticalMatrix = {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
    };


    private DesaturateFilter desaturateFilter = new DesaturateFilter();

    @Override
    public BufferedImage applyFilter(BufferedImage original, double[] parameters) {
        int edgeTreshold = (int)parameters[0];
        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        BufferedImage grayImage = desaturateFilter.applyFilter(original, parameters);
        for (int i = 0; i < original.getHeight(); ++i) {
            for (int j = 0; j < original.getWidth(); ++j) {
                int diff1 = countGx(grayImage, new Point(i, j), horizontalMatrix, 3);
                int diff2 = countGx(grayImage, new Point(i, j), verticalMatrix, 3);

                int gray = (int)Math.round(Math.sqrt(diff1 * diff1 + diff2 * diff2));
                gray = gray > edgeTreshold ? 255 : 0;
                int newRgb = (gray << 16 | gray << 8 | gray);
                filteredImage.setRGB(i, j, newRgb);
            }
        }
        return filteredImage;
    }

    private int countGx(BufferedImage source, Point center, int[][] matrix, int size){

        int finalGray = 0;

        int half = size / 2;
        for(int i = -half; i <= half; ++i){
            for (int j = -half; j <= half; ++j){
                int x0 = center.x + i;
                x0 = x0 < 0 ? 0 : x0 >= source.getWidth() ? source.getWidth() - 1 : x0;

                int y0 = center.y + j;
                y0 = y0 < 0 ? 0 : y0 >= source.getHeight() ? source.getHeight() - 1 : y0;

                int rgb = source.getRGB(x0, y0);
                int gray = (rgb) & 0x000000FF;

                int m = i + half;
                int n = j + half;

                finalGray += gray * matrix[m][n];
            }
        }
        return finalGray;
    }

}
