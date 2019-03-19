package com.nsu.fit.leonova.view.parametersWindow;

import javax.swing.*;
import java.awt.*;

public class ErrorShowingWindow {

    private JFrame window;
    public ErrorShowingWindow(String message) {
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
