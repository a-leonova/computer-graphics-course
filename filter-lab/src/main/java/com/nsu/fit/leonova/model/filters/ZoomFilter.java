package com.nsu.fit.leonova.model.filters;

import com.nsu.fit.leonova.model.SafeFloatColor;
import com.nsu.fit.leonova.model.SafeIntColor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ZoomFilter implements Filter {

    private final float SCALE = 2.0f;
    private final float PIV_X = 0.5f;
    private final float PIV_Y = 0.5f;

    @Override
    public BufferedImage applyFilter(BufferedImage original) {

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

        Point pixel = getSafePixel(new Point(xBase, yBase), source);
        SafeIntColor c00 = new SafeIntColor(source.getRGB(pixel.x, pixel.y));

        pixel = getSafePixel(new Point(xBase + xSign, yBase), source);
        SafeIntColor c10 = new SafeIntColor(source.getRGB(pixel.x, pixel.y));

        pixel = getSafePixel(new Point(xBase, yBase + ySign), source);
        SafeIntColor c01 = new SafeIntColor(source.getRGB(pixel.x, pixel.y));

        pixel = getSafePixel(new Point(xBase + xSign, yBase + ySign), source);
        SafeIntColor c11 = new SafeIntColor(source.getRGB(pixel.x, pixel.y));

        SafeIntColor interpolate1 = interpolate(c00, c10, xWeight, 1 - xWeight);
        SafeIntColor interpolate2 = interpolate(c01, c11, xWeight, 1 - xWeight);

        SafeIntColor res = interpolate(interpolate1, interpolate2, yWeight, 1 - yWeight);

        return res.getSafeIntColor();
    }


    private SafeIntColor interpolate(SafeIntColor c1, SafeIntColor c2, float weight1, float weight2){
        float w = weight1 / (weight1 + weight2);

        int dr = c2.getRed() - c1.getRed();
        int dg = c2.getGreen() - c1.getGreen();
        int db = c2.getBlue() - c1.getBlue();

        SafeFloatColor newColor = new SafeFloatColor(c1.getRed() + dr*w,
                c1.getGreen() + dg*w,c1.getBlue() + db*w);
        return new SafeIntColor(newColor.getIntColor());
    }

    private Point getSafePixel(Point pixel, BufferedImage source){

        int x0 = pixel.x < 0 ? 0 : pixel.x >= source.getWidth() ? source.getWidth() - 1 : pixel.x;
        int y0 = pixel.y < 0 ? 0 : pixel.y >= source.getHeight() ? source.getHeight() - 1 : pixel.y;

        return new Point(x0, y0);
    }

}
