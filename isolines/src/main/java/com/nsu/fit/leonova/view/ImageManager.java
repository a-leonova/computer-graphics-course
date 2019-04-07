package com.nsu.fit.leonova.view;

import com.nsu.fit.leonova.controller.ImageController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ImageManager extends JPanel {
    private BufferedImage image;
    private ImageController imageController;

    public ImageManager(int width, int height){
        image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        MyMouseAdapter mouseAdapter = new MyMouseAdapter();
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    public void setImageController(ImageController imageController) {
        this.imageController = imageController;
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


    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mouseDragged(MouseEvent mEvt) {
            imageController.imageWasDragged(new Point(mEvt.getX(), mEvt.getY()));
        }

        @Override
        public void mouseClicked(MouseEvent mEvt) {
            imageController.imageWasClicked(new Point(mEvt.getX(), mEvt.getY()));
        }

    }
}
