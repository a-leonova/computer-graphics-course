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
    public void openImage(File file) {
        imageConsumer.openSourcePicture(file);
    }

    @Override
    public void openConfigFile(File file) {
        imageConsumer.openConfigFile(file);
    }

    @Override
    public void saveImage(File file) {
        imageConsumer.saveImage(file);
    }


    @Override
    public void cropImage(Point leftTop, int width, int height) {
        imageConsumer.cropPicture(leftTop, width, height);
    }

    @Override
    public void absorptionWasPressed() {
        imageConsumer.absorptionWasPressed();
    }

    @Override
    public void emissionWasPressed() {
        imageConsumer.emissionWasPressed();
    }

    @Override
    public void filteredImageAsWorking() {
        imageConsumer.filteredImageAsWorking();
    }

    @Override
    public void workingImageAsFiltered() {
        imageConsumer.workingImageAsFiltered();
    }


    @Override
    public void filterImage(FiltersType filterType, double[] parameters) {
        imageConsumer.useFilterWithImage(filterType, parameters);
    }

    @Override
    public void removeAllImages() {
        imageConsumer.removeAllImages();
    }
}
