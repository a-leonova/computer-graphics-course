package com.nsu.fit.leonova.controller;

import com.nsu.fit.leonova.model.FiltersType;
import com.nsu.fit.leonova.model.ImageConsumer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Controller implements FileManager, ImageController {

    private ImageConsumer imageConsumer;

    public void setImageConsumer(ImageConsumer imageConsumer) {
        this.imageConsumer = imageConsumer;
    }

    @Override
    public void open(File file) {
        try{
            BufferedImage loadedImage = ImageIO.read(file);
            imageConsumer.setSourcePicture(loadedImage);
        } catch (IOException e){
          //TODO: send error and show window with error
        }
    }


    @Override
    public void cropImage(Point leftTop, int width, int height) {
        imageConsumer.cropPicture(leftTop, width, height);
    }

    @Override
    public void filteredImageAsWorking() {
        imageConsumer.filteredImageAsWorking();
    }


    @Override
    public void filterImage(FiltersType filterType) {
        imageConsumer.useFilterWithImage(filterType);
    }
}
