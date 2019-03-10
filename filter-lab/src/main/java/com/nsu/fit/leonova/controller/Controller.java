package com.nsu.fit.leonova.controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Controller implements FileManager, ImageController {
    private BufferedImage loadedImage;
    private BufferedImage cropedImage;

    @Override
    public void open(File file) {
        try{
            loadedImage = ImageIO.read(file);

        } catch (IOException e){
          //TODO: send error and show window with error
        }
    }


    @Override
    public void selectImage(Point leftTop, int width, int height) {
        cropedImage = loadedImage.getSubimage(leftTop.x, leftTop.y, width, height);
    }
}
