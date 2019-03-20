package com.nsu.fit.leonova.view.parametersWindow;

import com.nsu.fit.leonova.controller.ImageController;
import com.nsu.fit.leonova.model.FiltersType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RotationParamsWindow extends JFrame {

    private ImageController controller;
    private JTextField rotationTF = new JTextField("90", 4);
    private JButton apply = new JButton("Apply");

    public RotationParamsWindow(){
        super("Rotation parameters");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JLabel("Set angle"), BorderLayout.NORTH);

        rotationTF.setMinimumSize(new Dimension(50,50));
        JSlider rotationSlider = new JSlider(-180, 180, Integer.parseInt(rotationTF.getText()));
        rotationSlider.addChangeListener(e -> rotationTF.setText(String.valueOf(rotationSlider.getValue())));
        rotationTF.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
                String typed = rotationTF.getText();
                rotationSlider.setValue(0);
                int value = Integer.parseInt(typed);
                rotationSlider.setValue(value);
            }
        });

        mainPanel.add(rotationTF, BorderLayout.WEST);
        mainPanel.add(rotationSlider, BorderLayout.CENTER);
        mainPanel.add(apply, BorderLayout.SOUTH);
        //mainPanel.setMinimumSize(new Dimension(300, 50));
        add(mainPanel);
      //  pack();
        setMinimumSize(new Dimension(350,100));
    }

    public void setController(ImageController controller) {
        this.controller = controller;
        apply.addActionListener(e -> {
            try{
                int value = Integer.parseInt(rotationTF.getText());
                if(value < -180 || value > 180){
                    new ErrorShowingWindow("Value must be in range [-180; 180]").show();
                    return;
                }
                double[] params = {value};
                controller.filterImage(FiltersType.ROTATION, params);
                setVisible(false);
            } catch (NumberFormatException e1){
                new ErrorShowingWindow("Not an integer").show();
            }
        });
    }



}
