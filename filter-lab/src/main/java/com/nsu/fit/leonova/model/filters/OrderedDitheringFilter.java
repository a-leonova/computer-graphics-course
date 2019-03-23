package com.nsu.fit.leonova.model.filters;

import com.nsu.fit.leonova.model.SafeColor;

import java.awt.image.BufferedImage;

public class OrderedDitheringFilter implements Filter {
    private int powerOfMatrixSize;
    private double[][] matrix;

    @Override
    public BufferedImage applyFilter(BufferedImage original, double[] parameters) {
        powerOfMatrixSize = (int)parameters[0];
        matrix = generateMatrix(powerOfMatrixSize);
        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        int size = 1 << powerOfMatrixSize;
        for(int y = 0; y < filteredImage.getHeight(); ++y){
            for (int x = 0; x < filteredImage.getWidth(); ++x){
                SafeColor color = new SafeColor(original.getRGB(x, y));
                int r = color.getRed() > matrix[x % size][y % size] ? 255 : 0;
                int g = color.getGreen() > matrix[x % size][y % size] ? 255 : 0;
                int b = color.getBlue() > matrix[x % size][y % size] ? 255 : 0;
                int filteredColor = r << 16 | g << 8 | b;
                filteredImage.setRGB(x, y, filteredColor);
            }
        }
        return filteredImage;
    }

    private double[][] generateMatrix(int matrixSize){

        int n = 1 << matrixSize;
        double[][] matrix = new double[n][n];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; ++j){
                int v = 0;
                int mask = matrixSize - 1;
                int xc = j ^ i;
                int yc = j;
                for(int bit = 0; bit < 2 * matrixSize; --mask) {
                    v |= ((yc >> mask) & 1) << bit++;
                    v |= ((xc >> mask) & 1) << bit++;
                }
                matrix[i][j] = ((double)v)/(n * n);
            }
        }
        return matrix;
    }
}


