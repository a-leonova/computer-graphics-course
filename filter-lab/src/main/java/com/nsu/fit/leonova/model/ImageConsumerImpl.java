package com.nsu.fit.leonova.model;

import com.nsu.fit.leonova.model.filters.*;
import com.nsu.fit.leonova.observer.Observable;
import com.nsu.fit.leonova.observer.Observer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class ImageConsumerImpl implements Observable, ImageConsumer {

    private BufferedImage sourceImage;
    private BufferedImage workingImage;
    private BufferedImage filteredImage;

    private HashMap<FiltersType, Filter> filters = new HashMap<>();
    private ArrayList<Observer> observers = new ArrayList<>();

    public ImageConsumerImpl() {
        filters.put(FiltersType.BLUR, new BlurFilter());
        filters.put(FiltersType.DESATURATION, new DesaturateFilter());
        filters.put(FiltersType.EMBOSS, new EmbossFilter());
        filters.put(FiltersType.FS_DITHERING, new FSDitheringFilter());
        filters.put(FiltersType.INVERT, new InvertFilter());
        filters.put(FiltersType.ORDERED, new OrderedDitheringFilter());
        filters.put(FiltersType.ROBERTS, new RobertsFilter());
        filters.put(FiltersType.SHARPEN, new SharpenFilter());
        filters.put(FiltersType.SOBEL, new SobelFilter());
        filters.put(FiltersType.WATERCOLOR, new WaterColorFilter());
        filters.put(FiltersType.ZOOM, new ZoomFilter());
        filters.put(FiltersType.GAMMA, new GammaFilter());
        filters.put(FiltersType.ROTATION, new RotationFilter());
    }

    @Override
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void deleteObserver(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void useFilterWithImage(FiltersType filterType) {
        if(workingImage != null){
            Filter filter = filters.get(filterType);
            filteredImage = filter.applyFilter(workingImage);
            setFilteredPicture();
        }
    }

    @Override
    public void setSourcePicture(BufferedImage sourcePicture) {
        this.sourceImage = sourcePicture;
        for(Observer observer : observers){
            observer.setSourceImage(sourcePicture);
        }
    }

    @Override
    public void filteredImageAsWorking() {
        if(filteredImage != null){
            workingImage = filteredImage;
            setWorkingPicture();
        }
    }


    @Override
    public void cropPicture(Point leftTop, int width, int height) {
        if(sourceImage != null){
            workingImage = sourceImage.getSubimage(leftTop.x, leftTop.y, width, height);
            setWorkingPicture();
        }
    }

    private void setWorkingPicture() {
        for(Observer observer : observers){
            observer.setWorkingImage(workingImage);
        }
    }

    private void setFilteredPicture() {
        for(Observer observer : observers){
            observer.setFilteredImage(filteredImage);
        }
    }
}
