package com.nsu.fit.leonova.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

public class ImageManager extends JPanel {
    private BufferedImage image;

    public ImageManager() {
    }

    public ImageManager(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        setPreferredSize(new Dimension(width, height));
    }

    public void setMouseListener(MouseAdapter listener){
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    public int getImageHeight(){
        return image.getHeight();
    }

    public int getImageWidth(){
        return image.getWidth();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, this);
    }
    public void setImage(BufferedImage image){
        this.image = image;
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        repaint();
    }
}
