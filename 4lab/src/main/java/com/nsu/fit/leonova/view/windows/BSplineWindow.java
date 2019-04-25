package com.nsu.fit.leonova.view.windows;

import com.nsu.fit.leonova.model.bspline.SplineParameters;
import com.nsu.fit.leonova.observer.BSplineObserver;
import com.nsu.fit.leonova.view.ImageManager;
import com.nsu.fit.leonova.controller.BSplineController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class BSplineWindow extends JFrame implements BSplineObserver {
    private BSplineController controller;
    private ImageManager imageManager = new ImageManager();
    private JButton apply = new JButton("Apply");

    public BSplineWindow(BSplineController controller) throws HeadlessException {
        super("Creating B-Spline");
        this.controller = controller;
        apply.addActionListener(e -> {
            this.controller.apply();
            this.setVisible(false);
        });
        add(imageManager, BorderLayout.CENTER);
        add(apply, BorderLayout.SOUTH);
        imageManager.setMouseListener(new MyMouseAdapter());
        //pack();
    }

    public void setController(BSplineController controller) {
        this.controller = controller;
        apply.addActionListener(e -> {
            this.controller.apply();
            this.setVisible(false);
        });
    }

    @Override
    public void setBSpline(BufferedImage bSpline) {
        setVisible(true);
        imageManager.setImage(bSpline);
        repaint();
    }

    @Override
    public void setBSplineParameters(SplineParameters parameters) {

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
