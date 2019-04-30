package com.nsu.fit.leonova.view.windows.bsplineWindow.panels;

import com.nsu.fit.leonova.controller.BSplineController;
import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.model.bspline.SplineParameters;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel{
    private BSplineController controller;
    private JFrame parent;
    private JButton ok = new JButton("OK");
    private JButton apply = new JButton("Apply");
    private JButton newSpline = new JButton("New spline");
    private SplineParameters parameters = new SplineParameters("Some name");

    private JTextField nameTF = new JTextField();
    private JSpinner spinnerA = new JSpinner(new SpinnerNumberModel(parameters.getA(), 0.0, 0.99, 0.01));
    private JSpinner spinnerB = new JSpinner(new SpinnerNumberModel(parameters.getB(), 0.01, 1.0, 0.01));
    private JSpinner spinnerC = new JSpinner(new SpinnerNumberModel(parameters.getC(), 0.0, 6.27, 0.01));
    private JSpinner spinnerD = new JSpinner(new SpinnerNumberModel(parameters.getD(), 0.01, 6.28, 0.01));

    private JSpinner spinnerN = new JSpinner(new SpinnerNumberModel(parameters.getN(), 3, 100, 1));
    private JSpinner spinnerM = new JSpinner(new SpinnerNumberModel(parameters.getM(), 5, 100, 1));
    private JSpinner spinnerK = new JSpinner(new SpinnerNumberModel(parameters.getK(), 1, 100, 1));

    private JSpinner spinnerRed = new JSpinner(new SpinnerNumberModel(parameters.getColor().getRed(), 0, 255, 1));
    private JSpinner spinnerGreen = new JSpinner(new SpinnerNumberModel(parameters.getColor().getGreen(), 0, 255, 1));
    private JSpinner spinnerBlue = new JSpinner(new SpinnerNumberModel(parameters.getColor().getBlue(), 0, 255, 1));

    public SettingsPanel(BSplineController controller, JFrame parent) {
        this.controller = controller;
        this.parent = parent;
        ok.addActionListener(e -> {
            this.controller.apply(createSplineParameters());
            this.parent.setVisible(false);
        });
        apply.addActionListener(e -> {
            this.controller.apply(createSplineParameters());
        });
        newSpline.addActionListener(e -> controller.addSpline());
        fillPanel();
    }

    public void setController(BSplineController controller) {
        this.controller = controller;
        apply.addActionListener(e -> {
            this.controller.apply(createSplineParameters());
            parent.setVisible(false);
        });
        ok.addActionListener(e -> {
            this.controller.apply(createSplineParameters());
            parent.setVisible(false);
        });
    }

    public void setBSplineParameters(SplineParameters parameters) {
        this.parameters = parameters;
        changeInfo();
    }

    private void changeInfo(){
        nameTF.setText(parameters.getSplineName());
        spinnerA.setValue(parameters.getA());
        spinnerB.setValue(parameters.getB());
        spinnerC.setValue(parameters.getC());
        spinnerD.setValue(parameters.getD());

        spinnerN.setValue(parameters.getN());
        spinnerM.setValue(parameters.getM());
        spinnerK.setValue(parameters.getK());

        spinnerRed.setValue(parameters.getColor().getRed());
        spinnerGreen.setValue(parameters.getColor().getGreen());
        spinnerBlue.setValue(parameters.getColor().getBlue());
    }

    private void fillPanel(){
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 6),
                BorderFactory.createLoweredBevelBorder()));

        add(new JLabel("Name: "), createGridBagConstraints(GridBagConstraints.CENTER, 0, 0));
        add(nameTF, createGridBagConstraints(GridBagConstraints.CENTER, 1, 0));

        add(new JLabel("A: "),createGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 1));
        add(spinnerA,createGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 1));

        add(new JLabel("B: "), createGridBagConstraints(GridBagConstraints.HORIZONTAL, 2, 1));
        add(spinnerB,createGridBagConstraints(GridBagConstraints.HORIZONTAL, 3, 1));

        add(new JLabel("C: "), createGridBagConstraints(GridBagConstraints.HORIZONTAL, 4, 1));
        add(spinnerC, createGridBagConstraints(GridBagConstraints.HORIZONTAL, 5, 1));

        add(new JLabel("D: "),createGridBagConstraints(GridBagConstraints.HORIZONTAL, 6, 1));
        add(spinnerD, createGridBagConstraints(GridBagConstraints.HORIZONTAL, 7, 1));

        add(new JLabel("N: "), createGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 2));
        add(spinnerN, createGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 2));

        add(new JLabel("M: "), createGridBagConstraints(GridBagConstraints.HORIZONTAL, 2, 2));
        add(spinnerM, createGridBagConstraints(GridBagConstraints.HORIZONTAL, 3, 2));

        add(new JLabel("K: "), createGridBagConstraints(GridBagConstraints.HORIZONTAL, 4, 2));
        add(spinnerK, createGridBagConstraints(GridBagConstraints.HORIZONTAL, 5, 2));

        add(new JLabel("Red: "), createGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 3));
        add(spinnerRed, createGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 3));

        add(new JLabel("Green: "), createGridBagConstraints(GridBagConstraints.HORIZONTAL, 2, 3));
        add(spinnerGreen, createGridBagConstraints(GridBagConstraints.HORIZONTAL, 3, 3));

        add(new JLabel("Blue: "), createGridBagConstraints(GridBagConstraints.HORIZONTAL, 4, 3));
        add(spinnerBlue, createGridBagConstraints(GridBagConstraints.HORIZONTAL, 5, 3));

        add(ok, createGridBagConstraints(GridBagConstraints.CENTER, 2, 5));
        add(apply, createGridBagConstraints(GridBagConstraints.CENTER, 3, 5));
        add(newSpline, createGridBagConstraints(GridBagConstraints.CENTER, 5, 5));
    }

    private GridBagConstraints createGridBagConstraints(int fill, int gridX, int gridy){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = fill;
        c.gridx = gridX;
        c.gridy = gridy;
        return c;
    }

    private SplineParameters createSplineParameters(){
        String name = nameTF.getText();

        double a = (double)spinnerA.getValue();
        double b = (double)spinnerB.getValue();
        double c = (double)spinnerC.getValue();
        double d = (double)spinnerD.getValue();

        int n = (int)spinnerN.getValue();
        int m = (int)spinnerM.getValue();
        int k = (int)spinnerK.getValue();

        int red = (int)spinnerRed.getValue();
        int green = (int)spinnerGreen.getValue();
        int blue = (int)spinnerBlue.getValue();

        return new SplineParameters(name, n, m, k, a, b, c, d, new Color(red, green, blue));
    }
}
