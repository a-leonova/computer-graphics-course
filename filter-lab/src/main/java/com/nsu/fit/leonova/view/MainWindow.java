package com.nsu.fit.leonova.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MainWindow {
    private JFrame window;

    public MainWindow() throws IOException {
        window = new JFrame("Minimal photoshop");
        ImageManager jPanel = new ImageManager();
        window.add(jPanel);
//
        BufferedImage image = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("Picture.bmp")));
        jPanel.setImage(image);
//        Image newImage = resizeImage(image);
//        JLabel l1 = new JLabel("1");
//        //l1.setSize(350,350);(9
//        l1.setMinimumSize(new Dimension(350,350));
//        JLabel l2 = new JLabel();
//        JLabel l3 = new JLabel();
//        Border border = BorderFactory.createDashedBorder(Color.BLUE, 5, 10, 5, true);
//        l1.setBorder(border);
//        l2.setBorder(border);
//        l3.setBorder(border);
//        jPanel.add(l1, BorderLayout.WEST);
//        jPanel.add(l2, BorderLayout.CENTER);
//        jPanel.add(l3, BorderLayout.EAST);
        window.setSize(800, 500);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);


    }




}
