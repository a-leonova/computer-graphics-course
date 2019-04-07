package com.nsu.fit.leonova.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageManager extends JPanel {
    private BufferedImage image;

    public ImageManager(int width, int height){
        image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, this);
    }

    public void setImage(BufferedImage newImage){
        image = newImage;
        Graphics2D graphics2D = image.createGraphics();
        graphics2D.drawImage(image,0, 0, image.getWidth(), image.getHeight(), null);
        graphics2D.dispose();
        repaint();
    }
}
