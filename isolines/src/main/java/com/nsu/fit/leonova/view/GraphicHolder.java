package com.nsu.fit.leonova.view;

import com.nsu.fit.leonova.controller.ImageController;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GraphicHolder extends ImageManager{
    private ImageController imageController;

    public GraphicHolder(int width, int height) {
        super(width, height);
        MyMouseAdapter mouseAdapter = new MyMouseAdapter();
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    public void setImageController(ImageController imageController) {
        this.imageController = imageController;
    }

    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mouseDragged(MouseEvent mEvt) {
            imageController.imageWasDragged(mEvt.getPoint());
        }

        @Override
        public void mouseClicked(MouseEvent mEvt) {
            imageController.imageWasClicked(mEvt.getPoint());
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent){
            imageController.imageWasMoved(mouseEvent.getPoint());
        }

    }
}
