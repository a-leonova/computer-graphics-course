package com.nsu.fit.leonova.view.windows.bsplineWindow.panels;

import com.nsu.fit.leonova.controller.BSplineController;
import com.nsu.fit.leonova.globals.Globals;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class SplinesListPanel extends JPanel {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 500;
    private BSplineController controller;
    private JPanel mainList;
    private List<JPanel> splines = new ArrayList<>();
    private JToggleButton selectedButton = new JToggleButton();

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
        addSpline(Globals.FIRST_FIGURE_NAME);
    }

    public void setController(BSplineController controller) {
        this.controller = controller;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    public void addSpline(String name){
        JPanel panel = new JPanel();
        JToggleButton button = new JToggleButton(name);
        button.addActionListener(e -> {
            if(!button.isSelected()){
                button.setSelected(true);
                return;
            }
            controller.showBSplineInfo(splines.indexOf(panel));
            selectedButton.setSelected(false);
            selectedButton = button;
        });
        selectedButton.setSelected(false);
        button.setSelected(true);
        selectedButton = button;

        panel.add(button);
        splines.add(panel);
        panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainList.add(panel, gbc);

        validate();
        repaint();
    }

    public void rename(String name, int idx){
        JPanel panel = splines.get(idx);
        for (Component c : panel.getComponents()){
            if (c instanceof JToggleButton){
                JToggleButton b = (JToggleButton)c;
                b.setText(name);
            }
        }
    }

    public void removeSpline(int index){
        mainList.remove(index);
        validate();
        repaint();
    }
}
