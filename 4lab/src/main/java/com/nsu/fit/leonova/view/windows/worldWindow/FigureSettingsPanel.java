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
    private static final int MIN_X = -1000;
    private static final int MIN_Y = -1000;
    private static final int MIN_Z = -5000;
    private static final int MAX_X = 1000;
    private static final int MAX_Y = 1000;
    private static final int MAX_Z = 5000;
    private List<String> figures = new ArrayList<>();
    private JComboBox<String> box = new JComboBox<>();
    private WorldController controller;

    private JSpinner spinnerX = new JSpinner(new SpinnerNumberModel(0, null, null, 1));
    private JSpinner spinnerY = new JSpinner(new SpinnerNumberModel(0, null, null, 1));
    private JSpinner spinnerZ = new JSpinner(new SpinnerNumberModel(0, null, null, 1));
    private JSpinner spinnerZf = new JSpinner(new SpinnerNumberModel(0, null, null, 1));
    private JSpinner spinnerZb = new JSpinner(new SpinnerNumberModel(0, null, null, 1));

    public FigureSettingsPanel(WorldController controller) {
        this.controller = controller;
        spinnerX.addChangeListener(new SpinnerListener());
        spinnerY.addChangeListener(new SpinnerListener());
        spinnerZ.addChangeListener(new SpinnerListener());

        spinnerZf.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                controller.setZf((int)spinnerZf.getValue());
            }
        });

        box.addItem(Globals.FIRST_FIGURE_NAME);
        figures.add(Globals.FIRST_FIGURE_NAME);

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

        add(new JLabel("Zf: "), createGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 4));
        add(spinnerZf, createGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 4));
        add(new JLabel("Zb: "), createGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 5));
        add(spinnerZb, createGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 5));

        box.addItemListener(e -> {
            String name = (String)e.getItem();
            int index = figures.indexOf(name);
            System.out.println(index);
            controller.setSelectedFigure(index);
        });
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
        box.setSelectedIndex(idx);
    }

    public void setInfo(Point3D figureCenter){
        spinnerX.setValue((int)figureCenter.getX());
        spinnerY.setValue((int)figureCenter.getY());
        spinnerZ.setValue((int)figureCenter.getZ());
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
            controller.setFigureCenter(new Point3D((int)spinnerX.getValue(), (int)spinnerY.getValue(), (int)spinnerZ.getValue()));
        }
    }
}
