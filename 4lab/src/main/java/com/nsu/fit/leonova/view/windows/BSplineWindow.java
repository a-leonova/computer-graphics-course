package com.nsu.fit.leonova.view.windows;

import com.nsu.fit.leonova.view.ImageManager;
import com.nsu.fit.leonova.controller.BSplineController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class BSplineWindow extends JFrame {
    private BSplineController controller;
    private ImageManager imageManager = new ImageManager();
    private JButton apply = new JButton("Apply");

    public BSplineWindow() throws HeadlessException {
        super("Creating B-Spline");
        add(imageManager, BorderLayout.CENTER);
        add(apply, BorderLayout.SOUTH);
        imageManager.setMouseListener(new MyMouseAdapter());
        pack();
    }

    public void setController(BSplineController controller) {
        this.controller = controller;
        apply.addActionListener(e -> {
            this.controller.apply();
            this.setVisible(false);
        });
    }

    public void setImage(BufferedImage image){
        imageManager.setImage(image);
        repaint();
    }

    private class MyMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e){
            if(SwingUtilities.isLeftMouseButton(e)){
                controller.ImageLeftClicked(e.getPoint());
            }
            else if (SwingUtilities.isRightMouseButton(e)){
                controller.ImageRightClicked(e.getPoint());
            }
        }

        @Override
        public void mousePressed(MouseEvent e){
            if(SwingUtilities.isLeftMouseButton(e)){
                controller.ImageLeftPressed(e.getPoint());
            }
        }

        @Override
        public void mouseDragged(MouseEvent e){
            if(SwingUtilities.isLeftMouseButton(e)){
                controller.ImageLeftDragged(e.getPoint());
            }
        }
    }
}
