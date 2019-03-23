package com.nsu.fit.leonova.view.parametersWindow;

import com.nsu.fit.leonova.controller.ImageController;
import com.nsu.fit.leonova.model.FiltersType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FSDitheringParamsWindow extends JFrame {

    private final Integer MIN_VALUE = 0;
    private final Integer MAX_VALUE = 16;
    private final Integer DEFAULT_VALUE = 1;

    private ImageController controller;
    private JTextField redTF = new JTextField(DEFAULT_VALUE.toString());
    private JTextField greenTF = new JTextField(DEFAULT_VALUE.toString());
    private JTextField blueTF = new JTextField(DEFAULT_VALUE.toString());
    private JButton apply = new JButton("Apply");

    public  FSDitheringParamsWindow(){
        super("Fs dithering parameters");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new Label("Choose number of bits for each color"), BorderLayout.NORTH);

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
                if(redValue < MIN_VALUE || greenValue < MIN_VALUE || blueValue < MIN_VALUE ||
                        redValue > MAX_VALUE || greenValue > MAX_VALUE || blueValue > MAX_VALUE){
                    new ErrorShowingWindow("Values must be in range [" + MIN_VALUE + "; "  + MAX_VALUE + "]").show();
                    return;
                }
                double[] params = {redValue, greenValue, blueValue};
                this.controller.filterImage(FiltersType.FS_DITHERING, params);
                setVisible(false);
            } catch (NumberFormatException e1){
                new ErrorShowingWindow("Not an integer").show();
            }
        });
    }
}
