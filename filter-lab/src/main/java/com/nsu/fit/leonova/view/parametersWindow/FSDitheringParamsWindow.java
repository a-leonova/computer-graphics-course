package com.nsu.fit.leonova.view.parametersWindow;

import com.nsu.fit.leonova.controller.ImageController;
import com.nsu.fit.leonova.model.FiltersType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FSDitheringParamsWindow extends JFrame {

    private ImageController controller;
    private JTextField redTF = new JTextField("1");
    private JTextField greenTF = new JTextField("1");
    private JTextField blueTF = new JTextField("1");
    private JButton apply = new JButton("Apply");

    public  FSDitheringParamsWindow(){
        super("Order dithering parameters");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new Label("Choose RGB"), BorderLayout.NORTH);

        JPanel panelForCenter = new JPanel();
        panelForCenter.setLayout(new GridLayout(3,2));

        panelForCenter.add(new JLabel("Red"));
        panelForCenter.add(redTF);
        panelForCenter.add(new JLabel("Green"));
        panelForCenter.add(greenTF);
        panelForCenter.add(new JLabel("Blue"));
        panelForCenter.add(blueTF);

        panel.add(panelForCenter, BorderLayout.CENTER);
        panel.add(apply, BorderLayout.SOUTH);

        add(panel);
        pack();
    }

    public void setController(ImageController controller) {
        this.controller = controller;

        apply.addActionListener(e -> {
            try{
                int redValue = Integer.parseInt(redTF.getText());
                int greenValue = Integer.parseInt(greenTF.getText());
                int blueValue = Integer.parseInt(blueTF.getText());
                if(redValue < 0 || greenValue < 0 || blueValue < 0 ||
                        redValue > 255 || greenValue > 255 || blueValue > 255){
                    new ErrorShowingWindow("RGB must be in range [0;255]").show();
                    return;
                }
                double[] params = {redValue, greenValue, blueValue};
                controller.filterImage(FiltersType.FS_DITHERING, params);
                setVisible(false);
            } catch (NumberFormatException e1){
                new ErrorShowingWindow("Not an integer").show();
            }
        });
    }
}
