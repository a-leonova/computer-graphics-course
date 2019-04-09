package com.nsu.fit.leonova.model.graphicProvider;

import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.model.SafeColor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphicDrawer{

    private static final int LEGEND_VALUE_X = 15;
    private static final int LEGEND_MAX_VALUE_Y = 15;

    private SafeColor[] colorsRGB;

    private GraphicValues graphicValues;
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

    public void setGraphicValues(GraphicValues graphicValues) {
        this.graphicValues = graphicValues;
    }

    public void createGraphic(BufferedImage image, Boolean gradient){
        double[] minMax = findMinMax(image.getWidth(), image.getHeight());
        minZ = minMax[0];
        maxZ = minMax[1];

        for(int y = 0; y < image.getHeight(); ++y){
            for (int x = 0; x < image.getWidth(); ++x){
                DoublePoint point = graphicValues.pixelToGraphicCoord(new DoublePoint(x, y), image.getWidth(), image.getHeight());
                int color;
                if(gradient){
                    color = findGradientColor(Function.countValue(point), minZ, maxZ);
                }
                else{
                    color = findUsualColor(Function.countValue(point), minZ, maxZ);
                }
                image.setRGB(x, y, color);
            }
        }
    }

    public void createLegend(BufferedImage legend , boolean gradient) {
        //fill colors in legend
        for(int y = 0; y < legend.getHeight(); ++y){
            double y0 = y / ((double)legend.getHeight());
            int rgb;
            if(gradient){
                rgb = findGradientColor(1 - y0, 0, 1);
            }
            else{
                rgb = findUsualColor(1 - y0, 0, 1);
            }
            for (int x = 0; x < 10; ++x){
                legend.setRGB(x, y, rgb);
            }
        }
        //fill values in legend
        int step = (legend.getHeight() ) / (colorsRGB.length);
        double valueStep = (maxZ - minZ) / colorsRGB.length;
        Graphics2D g2 = legend.createGraphics();
        g2.setPaint(Color.WHITE);
        for(int i = 0; i < colorsRGB.length; ++i){
            int y = legend.getHeight() - step * i;
            double value = minZ + i * valueStep;
            g2.drawString(Globals.DECIMAL_FORMAT.format(value), LEGEND_VALUE_X, y);
        }
        g2.drawString(Globals.DECIMAL_FORMAT.format(maxZ), LEGEND_VALUE_X, LEGEND_MAX_VALUE_Y);
    }

    private double[] findMinMax(int imageWidth, int imageHeight){
        double minZ = Function.countValue(new DoublePoint(graphicValues.getMinX(), graphicValues.getMinY()));
        double maxZ = Function.countValue(new DoublePoint(graphicValues.getMinX(), graphicValues.getMinY()));
        for(int y = 0; y < imageHeight; ++y){
            for (int x = 0; x < imageWidth; ++x){
                DoublePoint point = graphicValues.pixelToGraphicCoord(new DoublePoint(x, y), imageWidth, imageHeight);
                double z = Function.countValue(point);
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
