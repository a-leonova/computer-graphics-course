package com.nsu.fit.leonova.view.windows;

import javax.swing.*;
import java.awt.*;

public class ErrorWindow extends JFrame {
    public ErrorWindow(String message) {
        super("Error");
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        JLabel errorLabel = new JLabel(message);
        jPanel.add(errorLabel, BorderLayout.CENTER);
        add(jPanel);
        pack();
    }
}
