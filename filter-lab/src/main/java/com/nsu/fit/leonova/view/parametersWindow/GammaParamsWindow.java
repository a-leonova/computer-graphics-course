package com.nsu.fit.leonova.view.parametersWindow;

import com.nsu.fit.leonova.controller.ImageController;
import com.nsu.fit.leonova.model.FiltersType;

import javax.swing.*;
import java.awt.*;

public class GammaParamsWindow extends JFrame {
    private ImageController controller;
    SpinnerModel gammaSpinnerModel =
            new SpinnerNumberModel(1.0, 0.0, 10, 0.1);
    JSpinner gammaSpinner = new JSpinner(gammaSpinnerModel);
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
        //TODO: ask!!
        this.controller = controller;
        apply.addActionListener(e -> {
            try{
                double value = (Double)gammaSpinner.getValue();
                double[] params = {value};
                controller.filterImage(FiltersType.GAMMA, params);
                setVisible(false);
            } catch (NumberFormatException e1){
                new ErrorShowingWindow("Not an double!").show();
            }
        });
    }

}
