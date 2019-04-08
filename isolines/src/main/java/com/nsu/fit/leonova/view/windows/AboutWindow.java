package com.nsu.fit.leonova.view.windows;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AboutWindow {

    private JFrame window;
    private JPanel imagePanel;
    private JPanel infoPanel;

    public AboutWindow() {
        window = new JFrame("About");
        imagePanel = new JPanel();
        infoPanel = new JPanel();

        window.add(imagePanel, BorderLayout.WEST);
        window.add(infoPanel, BorderLayout.EAST);

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
        Label versionInfo = new Label("Isolines 1.0 ");

        infoPanel.add(author);
        infoPanel.add(authorInfo);
        infoPanel.add(group);
        infoPanel.add(groupInfo);
        infoPanel.add(version);
        infoPanel.add(versionInfo);

        Label cr = new Label("Copyright, 2019");
        window.add(cr, BorderLayout.SOUTH);

        window.pack();
    }

    public void show(){
        window.setVisible(true);
    }

}
