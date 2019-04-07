package com.nsu.fit.leonova.model.graphicProvider;

import com.nsu.fit.leonova.model.SafeColor;

import java.awt.image.BufferedImage;

public class GraphicDrawer extends GraphicProvider {
    private SafeColor[] colorsRGB;
    private double minZ;
    private double maxZ;

    public double getMinZ() {
        return minZ;
    }

    public double getMaxZ() {
        return maxZ;
    }

    public void setColorsRGB(SafeColor[] colorsRGB) {
        this.colorsRGB = colorsRGB;
    }

    public void createGraphic(BufferedImage image, Boolean gradient){
        double[] minMax = findMinMax(image.getWidth(), image.getHeight());
        minZ = minMax[0];
        maxZ = minMax[1];

        for(int y = 0; y < image.getHeight(); ++y){
            for (int x = 0; x < image.getWidth(); ++x){
                double y0 =+ y / (double)image.getHeight() * definitionAreaWidth + minY;
                double x0 = x / (double)image.getWidth() * definitionAreaHeight + minX;
                int color;
                if(gradient){
                    color = findGradientColor(Function.countValue(x0, y0), minZ, maxZ);
                }
                else{
                    color = findUsualColor(Function.countValue(x0, y0), minZ, maxZ);
                }
                image.setRGB(x, y, color);
            }
        }
    }

    public void createLegend(BufferedImage legend , boolean gradient) {
        for(int y = 0; y < legend.getHeight(); ++y){
            double y0 = y / ((double)legend.getHeight());
            int rgb;
            if(gradient){
                rgb = findGradientColor(1 - y0, 0, 1);
            }
            else{
                rgb = findUsualColor(1 - y0, 0, 1);
            }
            for (int x = 0; x < legend.getWidth(); ++x){
                legend.setRGB(x, y, rgb);
            }
        }
    }

    private double[] findMinMax(int imageWidth, int imageHeight){
        double minZ = Function.countValue(minX, minY);
        double maxZ = Function.countValue(minX, minY);
        for(int y = 0; y < imageHeight; ++y){
            for (int x = 0; x < imageWidth; ++x){
                double y0 = y / (double)imageHeight * definitionAreaHeight + minY;
                double x0 = x / (double)imageWidth * definitionAreaWidth + minX;
                double z = Function.countValue(x0, y0);
                minZ = z < minZ ? z : minZ;
                maxZ = z > maxZ ? z : maxZ;
            }
        }
        return new double[]{minZ, maxZ};
    }

    private int findUsualColor(double z, double minZ, double maxZ){
        double relative = (z - minZ) / (maxZ - minZ);
        int i = Math.min((int)Math.floor(relative * (colorsRGB.length)), colorsRGB.length - 1);
        return colorsRGB[i].getIntRgb();
    }

    private int findGradientColor(double z, double minZ, double maxZ){
        double relative = (z - minZ) / (maxZ - minZ);
        double i = relative * (colorsRGB.length - 1);

        int left = (int)Math.floor(i);
        int right = (int)Math.ceil(i);
        if(left == right){
            return colorsRGB[left].getIntRgb();
        }
        SafeColor colorLeft = colorsRGB[left];
        SafeColor colorRight = colorsRGB[right];
        SafeColor res = colorLeft.multiple(right - i).plus(colorRight.multiple(i - left));
        return res.getIntRgb();
    }
}
