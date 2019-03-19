package com.nsu.fit.leonova.view.parametersWindow;

import com.nsu.fit.leonova.controller.ImageController;
import com.nsu.fit.leonova.model.FiltersType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OrderDitheringParamsWindow extends JFrame {

    private ImageController controller;
    private JTextField power;
    private JButton apply;

    public OrderDitheringParamsWindow() {
        super("Order dithering parameters");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new Label("Matrix size is 2^(k). Choose k."), BorderLayout.NORTH);
        power = new JTextField("4");

        JSlider powerSlider = new JSlider(1, 10, Integer.parseInt(power.getText()));
        powerSlider.addChangeListener(e -> power.setText(String.valueOf(powerSlider.getValue())));
        power.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
                String typed = power.getText();
                powerSlider.setValue(0);
                int value = Integer.parseInt(typed);
                powerSlider.setValue(value);
            }
        });
        panel.add(power, BorderLayout.WEST);
        panel.add(powerSlider, BorderLayout.CENTER);
        apply = new JButton("Apply");
        panel.add(apply, BorderLayout.SOUTH);

        add(panel);
        pack();
    }

    public void setController(ImageController controller) {
        this.controller = controller;
        apply.addActionListener(e -> {
            try{
                int value = Integer.parseInt(power.getText());
                if(value < 1 || value > 10){
                    new ErrorShowingWindow("Value must be in range [1; 10]").show();
                    return;
                }
                double[] params = {value};
                controller.filterImage(FiltersType.ORDERED, params);
                setVisible(false);
            } catch (NumberFormatException e1){
                new ErrorShowingWindow("Not an integer").show();
            }
        });
    }
}
