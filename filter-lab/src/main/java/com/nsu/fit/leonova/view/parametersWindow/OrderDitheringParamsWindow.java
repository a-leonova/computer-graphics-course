package com.nsu.fit.leonova.view.parametersWindow;

import com.nsu.fit.leonova.controller.ImageController;
import com.nsu.fit.leonova.model.FiltersType;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OrderDitheringParamsWindow extends JFrame {

    private ImageController controller;
    private JTextField edgeTreshold;
    JButton apply;

    public OrderDitheringParamsWindow() {
        super("Order dithering parameters");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new Label("Matrix size is 2^(k). Choose k."), BorderLayout.NORTH);
        edgeTreshold = new JTextField("4");

        JSlider edgeSlider = new JSlider(1, 10, Integer.parseInt(edgeTreshold.getText()));
        edgeSlider.addChangeListener(e -> edgeTreshold.setText(String.valueOf(edgeSlider.getValue())));
        edgeTreshold.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
                String typed = edgeTreshold.getText();
                edgeSlider.setValue(0);
                int value = Integer.parseInt(typed);
                edgeSlider.setValue(value);
            }
        });
        panel.add(edgeTreshold, BorderLayout.WEST);
        panel.add(edgeSlider, BorderLayout.CENTER);
        apply = new JButton("Apply");
        panel.add(apply, BorderLayout.SOUTH);

        add(panel);
        pack();
    }

    public void setController(ImageController controller) {
        this.controller = controller;

        apply.addActionListener(e -> {
            try{
                int value = Integer.parseInt(edgeTreshold.getText());
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
