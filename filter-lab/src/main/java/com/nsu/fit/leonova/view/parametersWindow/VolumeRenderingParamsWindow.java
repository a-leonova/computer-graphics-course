package com.nsu.fit.leonova.view.parametersWindow;

import com.nsu.fit.leonova.controller.ImageController;
import com.nsu.fit.leonova.model.FiltersType;

import javax.swing.*;
import java.awt.*;

public class VolumeRenderingParamsWindow extends JFrame {
    private final Integer MIN_VALUE = 0;
    private final Integer MAX_VALUE = 100;
    private final Integer DEFAULT_VALUE = 10;


    private ImageController controller;
    private JTextField maxXTF = new JTextField(DEFAULT_VALUE.toString());
    private JTextField maxYTF = new JTextField(DEFAULT_VALUE.toString());
    private JTextField maxZTF = new JTextField(DEFAULT_VALUE.toString());
    private JButton apply = new JButton("Apply");

    public VolumeRenderingParamsWindow(){
        super("Voxels params");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new Label("Choose RGB"), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3,2));

        centerPanel.add(new JLabel("X: "));
        centerPanel.add(maxXTF);
        centerPanel.add(new JLabel("Y: "));
        centerPanel.add(maxYTF);
        centerPanel.add(new JLabel("Z: "));
        centerPanel.add(maxZTF);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(apply, BorderLayout.SOUTH);

        add(mainPanel);
        pack();
    }

    public void setController(ImageController controller) {
        this.controller = controller;

        apply.addActionListener(e -> {
            try{
                int maxX= Integer.parseInt(maxXTF.getText());
                int maxY = Integer.parseInt(maxYTF.getText());
                int maxZ = Integer.parseInt(maxZTF.getText());
                if(maxX < MIN_VALUE || maxY < MIN_VALUE || maxZ < MIN_VALUE ||
                        maxX > MAX_VALUE || maxY > MAX_VALUE || maxZ > MAX_VALUE){
                    new ErrorShowingWindow("Values must be in range [" + MIN_VALUE + "; "  + MAX_VALUE + "]").show();
                    return;
                }
                double[] params = {maxX, maxY, maxZ};
                controller.filterImage(FiltersType.VOLUME_RENDERING, params);
                setVisible(false);
            } catch (NumberFormatException e1){
                new ErrorShowingWindow("Not an integer").show();
            }
        });
    }

}
