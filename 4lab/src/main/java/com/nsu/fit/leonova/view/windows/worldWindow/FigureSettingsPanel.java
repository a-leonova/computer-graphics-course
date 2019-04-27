package com.nsu.fit.leonova.view.windows.worldWindow;

import com.nsu.fit.leonova.controller.WorldController;
import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.model.Point3D;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FigureSettingsPanel extends JPanel {
    private List<String> figures = new ArrayList<>();
    private JComboBox<String> box = new JComboBox<>();
    private WorldController controller;

    //TODO: good constants!
    private JSpinner spinnerX = new JSpinner(new SpinnerNumberModel(0, -Globals.IMAGE_WIDTH/2, Globals.IMAGE_WIDTH/2, 1));
    private JSpinner spinnerY = new JSpinner(new SpinnerNumberModel(0, -Globals.IMAGE_HEIGHT/2, Globals.IMAGE_HEIGHT/2, 1));
    private JSpinner spinnerZ = new JSpinner(new SpinnerNumberModel(0, -Globals.IMAGE_WIDTH, Globals.IMAGE_WIDTH, 1));

    public FigureSettingsPanel(WorldController controller) {
        this.controller = controller;
        spinnerX.addChangeListener(new SpinnerListener());
        spinnerY.addChangeListener(new SpinnerListener());
        spinnerZ.addChangeListener(new SpinnerListener());
        //TODO: constants!
        box.addItem("Figure #0");
        figures.add("Figure #0");

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 6),
                BorderFactory.createLoweredBevelBorder()));

        add(box, createGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 0));
        add(new JLabel("X: "), createGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 1));
        add(spinnerX, createGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 1));
        add(new JLabel("Y: "), createGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 2));
        add(spinnerY, createGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 2));
        add(new JLabel("Z: "), createGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 3));
        add(spinnerZ, createGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 3));
    }

    public void addFigure(String name){
        figures.add(name);
        box.addItem(name);
    }

    public void removeFigure(int idx){
        figures.remove(idx);
        box.remove(idx);
    }

    public void renameFigure(String name, int idx){
        figures.set(idx, name);
        box.removeItemAt(idx);
        box.insertItemAt(name, idx);
    }

    public void setInfo(Point3D figureCenter){
        spinnerX.setValue(figureCenter.getX());
        spinnerY.setValue(figureCenter.getY());
        spinnerZ.setValue(figureCenter.getZ());
    }

    private GridBagConstraints createGridBagConstraints(int fill, int gridX, int gridy){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = fill;
        c.gridx = gridX;
        c.gridy = gridy;
        return c;
    }

    private class SpinnerListener implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e) {
            //System.out.println("WOW!");
            controller.setFigureCenter(new Point3D((int)spinnerX.getValue(), (int)spinnerY.getValue(), (int)spinnerZ.getValue()));
        }
    }
}
