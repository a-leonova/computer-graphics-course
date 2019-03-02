package com.leonova.fit.nsu.view.windows;

import javax.swing.*;
import java.awt.*;

public class BadParametersErrorWindow {
    private JFrame window;

    public BadParametersErrorWindow(String message) {
        window = new JFrame("Error");
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        JLabel errorLabel = new JLabel(message);
        jPanel.add(errorLabel, BorderLayout.CENTER);
        window.add(jPanel);
        window.pack();
    }

    public void show(){
        window.setVisible(true);
    }
}
