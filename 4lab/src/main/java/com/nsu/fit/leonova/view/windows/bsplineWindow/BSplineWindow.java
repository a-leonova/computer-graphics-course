package com.nsu.fit.leonova.view.windows.bsplineWindow;

import com.nsu.fit.leonova.model.bspline.SplineParameters;
import com.nsu.fit.leonova.observer.BSplineObserver;
import com.nsu.fit.leonova.view.ImageManager;
import com.nsu.fit.leonova.controller.BSplineController;
import com.nsu.fit.leonova.view.windows.bsplineWindow.panels.SettingsPanel;
import com.nsu.fit.leonova.view.windows.bsplineWindow.panels.SplinesListPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class BSplineWindow extends JFrame implements BSplineObserver {
    private BSplineController controller;
    private ImageManager imageManager = new ImageManager();
    private SettingsPanel settings;
    private SplinesListPanel splines;

    public BSplineWindow(BSplineController controller) throws HeadlessException {
        super("Creating B-Spline");
        this.controller = controller;
        settings = new SettingsPanel(controller, this);
        splines = new SplinesListPanel(controller);

        add(imageManager, BorderLayout.CENTER);
        add(settings, BorderLayout.SOUTH);
        add(splines, BorderLayout.WEST);

        controller.addSpline();

        imageManager.setMouseListener(new MyMouseAdapter());
        pack();
    }

    public void setController(BSplineController controller) {
        this.controller = controller;
        settings.setController(controller);
        splines.setController(controller);
    }

    @Override
    public void setBSpline(BufferedImage bSpline) {
        setVisible(true);
        imageManager.setImage(bSpline);
        repaint();
    }

    @Override
    public void setBSplineParameters(SplineParameters parameters) {
        settings.setBSplineParameters(parameters);
    }

    @Override
    public void addSpline(String name) {
        splines.addSpline(name);
    }

    @Override
    public void removeSpline(int index) {
        splines.removeSpline(index);
    }

    @Override
    public void changeFigureName(String name, int index) {
        splines.rename(name, index);
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
