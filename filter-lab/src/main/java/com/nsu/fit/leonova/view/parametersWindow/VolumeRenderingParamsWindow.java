package com.nsu.fit.leonova.view.parametersWindow;

import com.nsu.fit.leonova.controller.ImageController;
import com.nsu.fit.leonova.model.FiltersType;

import javax.swing.*;
import java.awt.*;

public class VolumeRenderingParamsWindow extends JFrame {

    private ImageController controller;
    private JTextField maxXTF = new JTextField("10");
    private JTextField maxYTF = new JTextField("10");
    private JTextField maxZTF = new JTextField("10");
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
                if(maxX < 0 || maxY < 0 || maxZ < 0 ||
                        maxX > 100 || maxY > 100 || maxZ > 100){
                    new ErrorShowingWindow("RGB must be in range [0; 100]").show();
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
