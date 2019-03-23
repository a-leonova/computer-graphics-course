package com.nsu.fit.leonova.view.parametersWindow;

import com.nsu.fit.leonova.controller.ImageController;
import com.nsu.fit.leonova.model.FiltersType;

import javax.swing.*;
import java.awt.*;

public class GammaParamsWindow extends JFrame {

    private final Double MIN_VALUE = 0.0;
    private final Double MAX_VALUE = 10.0;
    private final Double DEFAULT_VALUE = 1.0;
    private final Double STEP = 0.1;

    private ImageController controller;
    private SpinnerModel gammaSpinnerModel =
            new SpinnerNumberModel(DEFAULT_VALUE, MIN_VALUE, MAX_VALUE, STEP);
    private JSpinner gammaSpinner = new JSpinner(gammaSpinnerModel);
    private JButton apply = new JButton("Apply");

    public GammaParamsWindow(){
        super("Gamma parameters");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JLabel("Set gamma parameter"), BorderLayout.NORTH);
        mainPanel.add(new JLabel("Gamma: "), BorderLayout.WEST);
        mainPanel.add(gammaSpinner, BorderLayout.CENTER);
        mainPanel.add(apply, BorderLayout.SOUTH);
        add(mainPanel);
        pack();
    }

    public void setController(ImageController controller) {
        this.controller = controller;
        apply.addActionListener(e -> {
            try{
                double value = (Double)gammaSpinner.getValue();
                double[] params = {value};
                this.controller.filterImage(FiltersType.GAMMA, params);
                setVisible(false);
            } catch (NumberFormatException e1){
                new ErrorShowingWindow("Not an double!").show();
            }
        });
    }

}
