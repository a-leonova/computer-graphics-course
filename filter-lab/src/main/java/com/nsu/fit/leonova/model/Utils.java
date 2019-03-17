package com.nsu.fit.leonova.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Utils {
    public static Point getSafePoint(int x0, int y0, BufferedImage source){
        x0 = x0 < 0 ? 0 : x0 >= source.getWidth() ? source.getWidth() - 1 : x0;
        y0 = y0 < 0 ? 0 : y0 >= source.getHeight() ? source.getHeight() - 1 : y0;
        return new Point(x0, y0);
    }

    public static boolean isInRangePoint(int x0, int y0, BufferedImage source){
        return x0 >= 0 && y0 >= 0 && x0 < source.getWidth() && y0 < source.getHeight();
    }
}
