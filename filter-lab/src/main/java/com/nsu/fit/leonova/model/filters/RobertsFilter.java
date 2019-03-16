package com.nsu.fit.leonova.model.filters;

import java.awt.image.BufferedImage;

public class RobertsFilter implements Filter {

    private DesaturateFilter desaturateFilter = new DesaturateFilter();

    @Override
    public BufferedImage applyFilter(BufferedImage original) {
        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        BufferedImage grayImage = desaturateFilter.applyFilter(original);
        for (int i = 0; i < original.getHeight(); ++i) {
            for (int j = 0; j < original.getWidth(); ++j) {
                int diff1 = countG1(grayImage, i, j);
                int diff2 = countG2(grayImage, i, j);

                int gray = (int)Math.round(Math.sqrt(diff1 * diff1 + diff2 * diff2));
                gray = gray > 100 ? 255 : 0;
                int newRgb = (gray << 16 | gray << 8 | gray);
                filteredImage.setRGB(i, j, newRgb);
            }
        }
        return filteredImage;
    }


    private int countG1(BufferedImage sourceImage, int x0, int y0) {
        int x1 = x0 + 1;
        int y1 = y0 + 1;
        x1 = x1 < 0 ? 0 : x1 >= sourceImage.getWidth() ? sourceImage.getWidth() - 1 : x1;
        y1 = y1 < 0 ? 0 : y1 >= sourceImage.getHeight() ? sourceImage.getHeight() - 1 : y1;

        int rgb0 = sourceImage.getRGB(x0, y0);
        int rgb1 = sourceImage.getRGB(x1, y1);

        return rgb0 & 0x000000FF - rgb1 & 0x000000FF;
    }

    private int countG2(BufferedImage sourceImage, int x0, int y0) {

        int x1 = x0 + 1;
        int y1 = y0 + 1;
        x1 = x1 < 0 ? 0 : x1 >= sourceImage.getWidth() ? sourceImage.getWidth() - 1 : x1;
        y1 = y1 < 0 ? 0 : y1 >= sourceImage.getHeight() ? sourceImage.getHeight() - 1 : y1;

        int rgb0 = sourceImage.getRGB(x0, y1);
        int rgb1 = sourceImage.getRGB(x1, y0);

        return rgb0 & 0x000000FF - rgb1 & 0x000000FF;
    }


}
