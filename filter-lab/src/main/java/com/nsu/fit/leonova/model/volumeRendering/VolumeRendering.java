package com.nsu.fit.leonova.model.volumeRendering;

import com.nsu.fit.leonova.model.SafeColor;
import com.nsu.fit.leonova.model.filters.Filter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class VolumeRendering implements Filter {

    private boolean emissionEnabled = false;
    private boolean absorptionEnabled = false;

    private double[] absorptions = null;
    private int[][] emissions = null;
    private List<Charge> charges = new ArrayList<>();


    private double fMax;
    private double fMin;
    private int maxX = 10;
    private int maxY = 10;
    private int maxZ = 10;

    private double dx = 1./maxX;
    private double dy = 1./maxY;
    private double dz = 1./maxZ;

    public void setAbsorptions(double[] absorptions) {
        this.absorptions = absorptions;
    }

    public void setEmissions(int[][] emissions) {
        this.emissions = emissions;
    }

    public void setCharges(List<Charge> charges) {
        this.charges = charges;
    }

    public void emissionWasPressed(){
        emissionEnabled = !emissionEnabled;
    }

    public void absorptionWasPressed(){
        absorptionEnabled = !absorptionEnabled;
    }

    @Override
    public BufferedImage applyFilter(BufferedImage original, double[] parameters) {
        maxX = (int)parameters[0];
        maxY = (int)parameters[1];
        maxZ = (int)parameters[2];

        dx = 1./maxX;
        dy = 1./maxY;
        dz = 1./maxZ;

        BufferedImage filteredImage = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        findMinMax();
        for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                int newColor = renderPixel(original, x, y);
                filteredImage.setRGB(x, y, newColor);
            }
        }
        return filteredImage;
    }

    //находим минимальный и максимальные заряды
    private void findMinMax() {
        fMin = Double.MAX_VALUE;
        fMax = Double.MIN_VALUE;
        double value;
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                for (int z = 0; z < maxZ; z++) {
                    value = findChargesImpact(x, y, z);
                    fMin = (value < fMin) ? value : fMin;
                    fMax = (value > fMax) ? value : fMax;
                }
            }
        }
    }

    private int renderPixel(BufferedImage source, int x, int y) {
        try{
            SafeColor color = new SafeColor(source.getRGB(x, y));
            for (int z = 0; z < maxZ; z++) {
                //находим в каком вокселе находится точка
                double value = findChargesImpact(x * maxX / (350), y * maxY / (350), z);
                //сколько процентов от максимального взаимодействия
                int quant = (int) Math.round((value - fMin) / (fMax - fMin) * 100);
                double absorptionInPoint = absorptionEnabled ? Math.exp(-1 * absorptions[quant] * dz) : 1.;
                color.setRgb(color.getRed() * absorptionInPoint + (emissionEnabled ? (double) emissions[quant][0] / 255 * dz : 0),
                        color.getGreen() * absorptionInPoint + (emissionEnabled ? (double) emissions[quant][1] / 255 * dz : 0),
                        color.getBlue() * absorptionInPoint + (emissionEnabled ? (double) emissions[quant][2] / 255 * dz : 0));
            }
            return color.getIntRgb();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    private double findChargesImpact(int x, int y, int z) {
        //находим середину вокселя
        double centerX = (x + 0.5) * dx;
        double centerY = (y + 0.5) * dy;
        double centerZ = (z + 0.5) * dz;

        //считаем, как заряды влияет
        double value = 0.0;
        for (Charge c : charges) {
            double distance = Math.sqrt(
                    Math.pow(Math.abs(centerX - c.getX()), 2)
                            + Math.pow(Math.abs(centerY - c.getY()), 2)
                            + Math.pow(Math.abs(centerZ - c.getZ()), 2));
            distance = Math.max(0.1, distance);
            value += c.getQ() / distance;
        }
        return value;
    }

}
