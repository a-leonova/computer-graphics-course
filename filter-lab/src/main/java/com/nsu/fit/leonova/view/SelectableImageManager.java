package com.nsu.fit.leonova.view;

import com.nsu.fit.leonova.controller.ImageController;
import com.nsu.fit.leonova.globals.GlobalsImage;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class SelectableImageManager extends ImageManager {

    private int ABSOLUTE_AREA_SIZE = 350;

    private ImageController imageController;
    private Point center = null;

    private double resizeCoefficient;
    private int relativeAreaSize;
    private boolean select = false;

    private Point leftTop;
    private Point rightBottom;

    public SelectableImageManager() {
        super();
        MyMouseAdapter mouseAdapter = new MyMouseAdapter();
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    public void setImageController(ImageController imageController) {
        this.imageController = imageController;
    }

    public void changeSelect(boolean select) {
        this.select = select;
        if(!select){
            removeSelectedArea();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (select && center != null) {
            int x = center.x - relativeAreaSize / 2;
            int y = center.y - relativeAreaSize / 2;
            int width = relativeAreaSize;
            int height = relativeAreaSize;
            drawDashedRect(x, y, width, height, 2, 1,Color.RED, (Graphics2D) g);
        }
    }

    @Override
    public void setImage(BufferedImage image) {
        super.setImage(image);
        if(image.getHeight() > image.getWidth()){
            resizeCoefficient = image.getHeight() / (double) GlobalsImage.HEIGHT;
        }
        else{
            resizeCoefficient = image.getWidth() / (double) GlobalsImage.WIDTH;
        }
        if(image.getWidth() < GlobalsImage.WIDTH || image.getHeight() < GlobalsImage.HEIGHT){
            relativeAreaSize = (int)Math.floor(ABSOLUTE_AREA_SIZE * resizeCoefficient);
            ABSOLUTE_AREA_SIZE = relativeAreaSize;
        }
        else{
            ABSOLUTE_AREA_SIZE = 350;
            relativeAreaSize = (int)Math.floor(ABSOLUTE_AREA_SIZE/resizeCoefficient);
        }
        leftTop = new Point(Math.round(relativeAreaSize / 2.f) + GlobalsImage.DASH_BORDER_WIDTH - GlobalsImage.SelectAreaSize,
                Math.round(relativeAreaSize / 2.f) + GlobalsImage.DASH_BORDER_WIDTH - GlobalsImage.SelectAreaSize);
        rightBottom = new Point(insideImage.getWidth(null) - Math.round(relativeAreaSize / 2.f) + GlobalsImage.DASH_BORDER_WIDTH,
                insideImage.getHeight(null) - Math.round(relativeAreaSize / 2.f) + GlobalsImage.DASH_BORDER_WIDTH);
        repaint();
    }

    public void removeSelectedArea() {
        center = null;
        repaint();
    }

    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mouseDragged(MouseEvent mEvt) {
            if(!select){
                return;
            }

            int x = mEvt.getX();
            if(x <= leftTop.x){
                center.x = leftTop.x;
                x = 0;
            }
            else if (x >= rightBottom.x){
                center.x = rightBottom.x;
                x = insideImage.getWidth(null) - 1 - relativeAreaSize;
                x = x < 0 ? 0 : x;
            }
            else {
                center.x = x;
                x = x - GlobalsImage.DASH_BORDER_WIDTH - Math.round(relativeAreaSize / 2.f);
            }

            int y = mEvt.getY();
            if(y <= leftTop.y){
                center.y = leftTop.y;
                y = 0;
            }
            else if(y >= rightBottom.y){
                center.y = rightBottom.y;
                y = insideImage.getHeight(null) - 1 - relativeAreaSize;
                y = y < 0 ? 0 : y;
            }
            else {
                center.y = y;
                y = y - GlobalsImage.DASH_BORDER_WIDTH - Math.round(relativeAreaSize / 2.f);
            }
            SelectableImageManager.this.repaint();

            int realX = (int)Math.floor(x * resizeCoefficient);
            int realY = (int)Math.floor(y * resizeCoefficient);
            imageController.cropImage(new Point(realX, realY), ABSOLUTE_AREA_SIZE, ABSOLUTE_AREA_SIZE);
        }

        @Override
        public void mousePressed(MouseEvent mEvt) {
            center = mEvt.getPoint();
            mouseDragged(mEvt);
            SelectableImageManager.this.repaint();
        }

    }
}
