package com.nsu.fit.leonova.view.windows.bsplineWindow.panels;

import com.nsu.fit.leonova.controller.BSplineController;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class SplinesListPanel extends JPanel {
    private BSplineController controller;
    private JPanel mainList;
    private List<JPanel> splines = new ArrayList<>();

    public SplinesListPanel(BSplineController controller){
        this.controller = controller;
        setLayout(new BorderLayout());
        mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainList.add(new JPanel(), gbc);

        add(new JScrollPane(mainList));
    }

    public void setController(BSplineController controller) {
        this.controller = controller;
    }

    @Override
    public Dimension getPreferredSize() {
        //TODO: think about dimension
        return new Dimension(100, 500);
    }

    public void addSpline(String name){
        JPanel panel = new JPanel();
        panel.add(new JLabel(name));
        splines.add(panel);
        panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainList.add(panel, gbc, 0);

        validate();
        repaint();
    }

    public void removeSpline(int index){
        mainList.remove(index);
        validate();
        repaint();
    }

}
