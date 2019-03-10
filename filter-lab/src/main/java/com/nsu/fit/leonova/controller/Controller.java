package com.nsu.fit.leonova.controller;

import com.nsu.fit.leonova.model.FilterManager;
import com.nsu.fit.leonova.model.FiltersType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Controller implements FileManager, ImageController {

    private FilterManager filterManager;

    private BufferedImage loadedImage;
    private BufferedImage cropedImage;

    public void setFilterManager(FilterManager filterManager) {
        this.filterManager = filterManager;
    }

    @Override
    public void open(File file) {
        try{
            loadedImage = ImageIO.read(file);
        } catch (IOException e){
          //TODO: send error and show window with error
        }
    }


    @Override
    public void cropImage(Point leftTop, int width, int height) {
        if(loadedImage != null){
            cropedImage = loadedImage.getSubimage(leftTop.x, leftTop.y, width, height);
            filterManager.setWorkingImage(cropedImage);
        }
    }

    @Override
    public void setWorkingImage(BufferedImage filteredImage) {
        if(cropedImage != null){
            cropedImage = filteredImage;
            filterManager.setWorkingImage(cropedImage);
        }
    }

    @Override
    public void filterImage(FiltersType filterType) {
        if(cropedImage != null){
            filterManager.useFilterWithImage(cropedImage, filterType);
        }
    }
}
