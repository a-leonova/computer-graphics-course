package com.nsu.fit.leonova.view;

import com.nsu.fit.leonova.globals.Globals;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImagesHolder extends JPanel {

    private boolean selected = true;
    private SelectableImageManager sourceImage = new SelectableImageManager();
    private ImageManager chosenArea = new ImageManager();
    private ImageManager filteredArea = new ImageManager();

    public ImagesHolder() throws IOException {
        setLayout(new FlowLayout(FlowLayout.LEFT, Globals.BORDER, Globals.BORDER));

        BufferedImage image = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("ChristmasBaubles.bmp")));
        sourceImage.setImage(image);
        add(sourceImage);
        add(chosenArea);
        add(filteredArea);
    }

    public void setSelected(boolean selected) {
        if(!selected){
            sourceImage.removeSelectedArea();
        }
        this.selected = selected;
    }
}
