package com.nsu.fit.leonova.view.parametersWindow;

import com.nsu.fit.leonova.controller.ImageController;

import javax.swing.*;
import java.awt.*;

public class OrderDitheringParamsWindow extends JFrame {

    public OrderDitheringParamsWindow(ImageController controller){
        super("Order dithering parameters");

        JPanel panel = new JPanel();
        panel.add(new Label("Matrix size is 2^(k). Choose k."), BorderLayout.NORTH);
//        panel.add(new)
    }
}
