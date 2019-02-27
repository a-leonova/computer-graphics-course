package com.leonova.fit.nsu.view.windows;

import com.leonova.fit.nsu.view.Answer;
import com.leonova.fit.nsu.view.WindowView;

import javax.swing.*;
import java.awt.*;

public class AskSaveWindow {

    private JFrame window;
    private WindowView windowView;

    public AskSaveWindow(WindowView windowView) {
        this.windowView = windowView;
        window = new JFrame("Save file?");
        JButton ok = new JButton("YES");
        ok.addActionListener(e->{
            windowView.handleAskSave(Answer.YES);
            window.setVisible(false);
        });
        JButton no = new JButton("NO");
        no.addActionListener(e->{
            windowView.handleAskSave(Answer.NO);
            window.setVisible(false);
        });
        JButton cancel = new JButton("CANCEL");
        cancel.addActionListener(e->{
            windowView.handleAskSave(Answer.CANCEL);
            window.setVisible(false);
        });

        JLabel question = new JLabel("Do you want to save file?");

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(1,3));

        window.add(question, BorderLayout.NORTH);
        jPanel.add(ok);
        jPanel.add(no);
        jPanel.add(cancel);
        window.add(jPanel, BorderLayout.CENTER);
        window.pack();

    }
    public void show(){
        window.setVisible(true);
    }
}
