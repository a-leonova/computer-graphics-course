package com.nsu.fit.leonova.model;

import com.nsu.fit.leonova.model.filters.*;
import com.nsu.fit.leonova.observer.Observable;
import com.nsu.fit.leonova.observer.Observer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class FilterManagerImpl implements Observable, FilterManager {
    private HashMap<FiltersType, Filter> filters = new HashMap<>();
    private ArrayList<Observer> observers = new ArrayList<>();

    public FilterManagerImpl() {
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
    public void useFilterWithImage(BufferedImage original, FiltersType filterType) {
        Filter filter = filters.get(filterType);
        BufferedImage filteredImage = filter.applyFilter(original);
        notifyObserverForFilteredImage(filteredImage);
    }

    @Override
    public void setWorkingImage(BufferedImage image) {
        for(Observer observer : observers){
            observer.setWorkingImage(image);
        }
    }

    private void notifyObserverForFilteredImage(BufferedImage image) {
        for(Observer observer : observers){
            observer.setFilteredImage(image);
        }
    }
}
