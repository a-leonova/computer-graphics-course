package com.nsu.fit.leonova.view;

import com.nsu.fit.leonova.controller.ImageController;
import com.nsu.fit.leonova.globals.Globals;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class SelectableImageManager extends ImageManager {

    private int ABSOLUTE_AREA_SIZE = 350;

    private ImageController imageController;

    private BufferedImage realImage;
    private Point center = null;

    private double resizeCoefficient;
    private int relativeAreaSize;

    public SelectableImageManager() {
        super();
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
        if (center != null) {
            //g.setColor(Color.CYAN);
            int x = center.x - relativeAreaSize / 2;
            int y = center.y - relativeAreaSize / 2;
            int width = relativeAreaSize;
            int height = relativeAreaSize;
            drawDashedRect(x, y, width, height, 2, Color.RED, (Graphics2D) g);
        }
    }

    @Override
    public void setImage(BufferedImage image) {
        super.setImage(image);
        //TODO: if small image
        if(image.getHeight() > image.getWidth()){
            resizeCoefficient = image.getHeight() / (double)Globals.HEIGHT;
        }
        else{
            resizeCoefficient = image.getWidth() / (double)Globals.WIDTH;
        }
        relativeAreaSize = (int)Math.floor(ABSOLUTE_AREA_SIZE/resizeCoefficient);
        realImage = image;
        repaint();
    }

    public void removeSelectedArea() {
        center = null;
        repaint();
    }


    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mouseDragged(MouseEvent mEvt) {

            //TODO - do not recount it everytime
            int x0 = relativeAreaSize / 2;
            int y0 = relativeAreaSize /2;
            int x1 = insideImage.getWidth(null) - relativeAreaSize /2 - Globals.DASH_BORDER_WIDTH;
            int y1 = insideImage.getHeight(null) - relativeAreaSize/2 - Globals.DASH_BORDER_WIDTH;

            int x = mEvt.getX();
            if(x < x0){
                x = x0;
            }
            else if (x > x1){
                x = x1;
            }

            int y = mEvt.getY();
            if(y < y0){
                y = y0;
            }
            else if(y >= y1){
                y = y1;
            }

            center = new Point(x,y);
            SelectableImageManager.this.repaint();
            imageController.cropImage(new Point(center.x - relativeAreaSize/2, center.y - relativeAreaSize/2), relativeAreaSize, relativeAreaSize);
        }

        @Override
        public void mousePressed(MouseEvent mEvt) {
            center = mEvt.getPoint();
            SelectableImageManager.this.repaint();
        }

    }
}
