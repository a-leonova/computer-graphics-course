package com.nsu.fit.leonova.model.filters;

import com.nsu.fit.leonova.model.SafeColor;
import com.nsu.fit.leonova.model.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ZoomFilter implements Filter {

    private final float SCALE = 2.0f;
    private final float PIV_X = 0.5f;
    private final float PIV_Y = 0.5f;

    @Override
    public BufferedImage applyFilter(BufferedImage original, double[] parameters) {

        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for(int y = 0; y < filteredImage.getHeight(); ++y){
            for (int x = 0; x < filteredImage.getWidth(); ++x){
                float xf = ((float)x/original.getWidth() - PIV_X) / SCALE + PIV_X;
                float yf = ((float)y/original.getHeight() - PIV_Y) / SCALE + PIV_Y;
                filteredImage.setRGB(x, y, getColorOfFloatPixel(xf, yf, original));
            }
        }
        return filteredImage;
    }

    private int getColorOfFloatPixel(float x0, float y0, BufferedImage source){
        x0 *= source.getWidth();
        y0 *= source.getHeight();

        int xBase = Math.round(x0);
        int yBase = Math.round(y0);

        int xSign = (int)Math.signum(x0 - xBase);
        int ySign = (int)Math.signum(y0 - yBase);

        float xWeight = Math.abs(x0 - xBase);
        float yWeight = Math.abs(y0 - yBase);

        Point pixel = Utils.getSafePoint(xBase, yBase, source);
        SafeColor c00 = new SafeColor(source.getRGB(pixel.x, pixel.y));

        pixel = Utils.getSafePoint(xBase + xSign, yBase, source);
        SafeColor c10 = new SafeColor(source.getRGB(pixel.x, pixel.y));

        pixel = Utils.getSafePoint(xBase, yBase + ySign, source);
        SafeColor c01 = new SafeColor(source.getRGB(pixel.x, pixel.y));

        pixel = Utils.getSafePoint(xBase + xSign, yBase + ySign, source);
        SafeColor c11 = new SafeColor(source.getRGB(pixel.x, pixel.y));

        SafeColor interpolate1 = interpolate(c00, c10, xWeight, 1 - xWeight);
        SafeColor interpolate2 = interpolate(c01, c11, xWeight, 1 - xWeight);

        SafeColor res = interpolate(interpolate1, interpolate2, yWeight, 1 - yWeight);

        return res.getIntRgb();
    }


    private SafeColor interpolate(SafeColor c1, SafeColor c2, float weight1, float weight2){
        float w = weight1 / (weight1 + weight2);

        double dr = c2.getRed() - c1.getRed();
        double dg = c2.getGreen() - c1.getGreen();
        double db = c2.getBlue() - c1.getBlue();

        SafeColor newColor = new SafeColor(c1.getRed() + dr*w,
                c1.getGreen() + dg*w,c1.getBlue() + db*w);
        return newColor;
    }


}
