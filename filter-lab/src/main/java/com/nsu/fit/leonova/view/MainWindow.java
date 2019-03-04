package com.nsu.fit.leonova.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MainWindow extends JFrame{

    public MainWindow() throws IOException {
        super("Minimal photoshop");
        ImageManager sourceImage = new ImageManager();
        ImageManager chosenArea = new ImageManager();
        ImageManager filteredArea = new ImageManager();

        setLayout(new FlowLayout());

//
        BufferedImage image = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("Picture.bmp")));
        sourceImage.setImage(image);
        add(sourceImage);
        add(chosenArea);
        add(filteredArea);
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
        setSize(1000, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);


    }




}
