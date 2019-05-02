package com.nsu.fit.leonova.view.windows.worldWindow;

import com.nsu.fit.leonova.controller.WorldController;
import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.model.Point3D;
import com.nsu.fit.leonova.model.WorldParameters;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FigureSettingsPanel extends JPanel {
    private List<String> figures = new ArrayList<>();
    private JComboBox<String> box = new JComboBox<>();
    private WorldController controller;
    private WorldParameters wp = new WorldParameters();

    private JSpinner spinnerX = new JSpinner(new SpinnerNumberModel(0, null, null, 1));
    private JSpinner spinnerY = new JSpinner(new SpinnerNumberModel(0, null, null, 1));
    private JSpinner spinnerZ = new JSpinner(new SpinnerNumberModel(0, null, null, 1));
    private JSpinner spinnerZf = new JSpinner(new SpinnerNumberModel(wp.getZf(), null, null, 1));
    private JSpinner spinnerZb = new JSpinner(new SpinnerNumberModel(wp.getZb(), null, null, 1));
    private JSpinner spinnerSw = new JSpinner(new SpinnerNumberModel(wp.getSw(), null, null, 1));
    private JSpinner spinnerSh = new JSpinner(new SpinnerNumberModel(wp.getSh(), null, null, 1));

    public FigureSettingsPanel(WorldController controller) {
        this.controller = controller;
        spinnerX.addChangeListener(e -> this.controller.setFigureCenter(new Point3D((int)spinnerX.getValue(), (int)spinnerY.getValue(), (int)spinnerZ.getValue())));
        spinnerY.addChangeListener(e -> this.controller.setFigureCenter(new Point3D((int)spinnerX.getValue(), (int)spinnerY.getValue(), (int)spinnerZ.getValue())));
        spinnerZ.addChangeListener(e -> this.controller.setFigureCenter(new Point3D((int)spinnerX.getValue(), (int)spinnerY.getValue(), (int)spinnerZ.getValue())));
        spinnerZf.addChangeListener(e -> controller.setWorldParameters(new WorldParameters((int)spinnerZf.getValue(), (int)spinnerZb.getValue(), (int)spinnerSw.getValue(), (int)spinnerSh.getValue())));
        spinnerZb.addChangeListener(e -> controller.setWorldParameters(new WorldParameters((int)spinnerZf.getValue(), (int)spinnerZb.getValue(), (int)spinnerSw.getValue(), (int)spinnerSh.getValue())));
        spinnerSw.addChangeListener(e -> controller.setWorldParameters(new WorldParameters((int)spinnerZf.getValue(), (int)spinnerZb.getValue(), (int)spinnerSw.getValue(), (int)spinnerSh.getValue())));
        spinnerSh.addChangeListener(e -> controller.setWorldParameters(new WorldParameters((int)spinnerZf.getValue(), (int)spinnerZb.getValue(), (int)spinnerSw.getValue(), (int)spinnerSh.getValue())));

        ((JSpinner.DefaultEditor) spinnerX.getEditor()).getTextField().setColumns(10);
        ((JSpinner.DefaultEditor) spinnerY.getEditor()).getTextField().setColumns(10);
        ((JSpinner.DefaultEditor) spinnerZ.getEditor()).getTextField().setColumns(10);
        ((JSpinner.DefaultEditor) spinnerZf.getEditor()).getTextField().setColumns(10);

        box.addItem("World");
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

        add(new JLabel("Sw: "), createGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 6));
        add(spinnerSw, createGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 6));
        add(new JLabel("Sh: "), createGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 7));
        add(spinnerSh, createGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 7));

        box.addItemListener(e -> {
            String name = (String)e.getItem();
            int index;
            if(name.equalsIgnoreCase("world")){
                index = -1;
            }
            else {
                index = figures.indexOf(name);
            }
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
        box.removeItemAt(idx + 1);
    }

    public void renameFigure(String name, int idx){
        figures.set(idx, name);
        box.removeItemAt(idx + 1);
        box.insertItemAt(name, idx + 1);
        box.setSelectedIndex(idx + 1);
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
}
