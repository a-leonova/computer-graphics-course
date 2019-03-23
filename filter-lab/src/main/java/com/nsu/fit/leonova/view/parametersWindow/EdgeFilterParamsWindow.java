package com.nsu.fit.leonova.view.parametersWindow;

import com.nsu.fit.leonova.controller.ImageController;
import com.nsu.fit.leonova.model.FiltersType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EdgeFilterParamsWindow extends JFrame {

    private final int MIN_VALUE = 1;
    private final int MAX_VALUE = 255;
    private final int DEFAULT_VALUE = 100;

    private ImageController controller;
    private FiltersType type;
    private JTextField edgeThresholdTF;
    private JButton apply = new JButton("Apply");

    public EdgeFilterParamsWindow(){
        super("Edge threshold");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(new JLabel("Edge threshold"), BorderLayout.NORTH);
        edgeThresholdTF = new JTextField(String.valueOf(DEFAULT_VALUE));

        JSlider edgeSlider = new JSlider(MIN_VALUE, MAX_VALUE, Integer.parseInt(edgeThresholdTF.getText()));
        edgeSlider.addChangeListener(e -> edgeThresholdTF.setText(String.valueOf(edgeSlider.getValue())));
        edgeThresholdTF.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
                String typed = edgeThresholdTF.getText();
                edgeSlider.setValue(0);
                int value = Integer.parseInt(typed);
                edgeSlider.setValue(value);
            }
        });
        mainPanel.add(edgeThresholdTF, BorderLayout.WEST);
        mainPanel.add(edgeSlider, BorderLayout.CENTER);
        mainPanel.add(apply, BorderLayout.SOUTH);

        add(mainPanel);
        pack();
    }

    public void setController(ImageController controller) {
        this.controller = controller;
        apply.addActionListener(e -> {
            try{
                int value = Integer.parseInt(edgeThresholdTF.getText());
                if(value < 1 || value > 255){
                    new ErrorShowingWindow("Value must be in range [" + MIN_VALUE + "; "  + MAX_VALUE + "]").show();
                    return;
                }
                double[] params = {value};
                this.controller.filterImage(type, params);
                setVisible(false);
            } catch (NumberFormatException e1){
                new ErrorShowingWindow("Not an integer").show();
            }
        });
    }

    public void setType(FiltersType type) {
        this.type = type;
    }

}
