package com.nsu.fit.leonova.model.filters;

import com.nsu.fit.leonova.model.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RotationFilter implements Filter {

    private int angleInDegrees;
    private double angleInRad;

    @Override
    public BufferedImage applyFilter(BufferedImage original, double[] parameters) {
        angleInDegrees = (int) parameters[0];
        angleInRad = Math.toRadians(angleInDegrees);

        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for(int y = 0; y < filteredImage.getHeight(); ++y){
            for (int x = 0; x < filteredImage.getWidth(); ++x){
                filteredImage.setRGB(x, y, findSourcePixel(new Point(x, y), original));
            }
        }
        return filteredImage;
    }

    private int findSourcePixel(Point point, BufferedImage source){
        //rotate filtered image pixel on opposite angle
        double cosA = Math.cos(angleInRad);
        double sinA = -Math.sin(angleInRad);
        double sin2 = -Math.sin(angleInRad/2);
        double cos2 = Math.cos(angleInRad/2);

        int rotationPointX = source.getWidth() / 2;
        int rotationPointY = source.getHeight() / 2;
        int x0 = (int)Math.round(point.x * cosA + point.y * sinA + 2 * sin2 * (rotationPointX * sin2 - rotationPointY  * cos2));
        int y0 = (int)Math.round(-point.x * sinA + point.y * cosA + 2 * sin2 * (rotationPointX * cos2 + rotationPointY * sin2));

        if(Utils.isInRangePoint(x0, y0, source)){
            return source.getRGB(x0, y0);
        }
        else {
            return Color.BLACK.getRGB();
        }
    }

}
