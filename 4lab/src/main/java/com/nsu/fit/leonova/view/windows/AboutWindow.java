package com.nsu.fit.leonova.view.windows;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AboutWindow extends JFrame {

    public AboutWindow() {
        super("About");
        JPanel imagePanel = new JPanel();
        JPanel infoPanel = new JPanel();

        add(imagePanel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.EAST);

        ImageIcon photo = new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("images/i.jpg")));
        JLabel label = new JLabel(photo);
        imagePanel.add(label);

        GridLayout info = new GridLayout(3,2);
        infoPanel.setLayout(info);
        Label author = new Label("Author: ");
        Label group = new Label("Group: ");
        Label version = new Label("Version: ");
        Label authorInfo = new Label("Ananstasia Leonova ");
        Label groupInfo = new Label("16206, FIT, NSU");
        Label versionInfo = new Label("Wireframe 1.0 ");

        infoPanel.add(author);
        infoPanel.add(authorInfo);
        infoPanel.add(group);
        infoPanel.add(groupInfo);
        infoPanel.add(version);
        infoPanel.add(versionInfo);

        Label cr = new Label("Copyright, 2019");
        add(cr, BorderLayout.SOUTH);
        pack();
    }
}
