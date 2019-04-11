package com.nsu.fit.leonova.view.windows;

import com.nsu.fit.leonova.controller.LogicController;
import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.model.graphicProvider.GraphicValues;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;

public class ConfigurationWindow extends JFrame{
    private LogicController logicController = null;

    private GraphicValues graphicValues;

    private JTextField netWidthTF;
    private JTextField netHeightTF;
    private JTextField minXTF;
    private JTextField minYTF;
    private JTextField maxXTF;
    private JTextField maxYTF;

    private JButton ok;

    public ConfigurationWindow(GraphicValues graphicValues, int k, int m){
        super("Configurations");
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(6, 2));

        JLabel netWidth = new JLabel("Net width: ");
        JLabel netHeight = new JLabel("Net height: ");
        JLabel minX = new JLabel("Min X: ");
        JLabel minY = new JLabel("Min Y: ");
        JLabel maxX = new JLabel("Max X: ");
        JLabel maxY = new JLabel("Max Y: ");

        netWidthTF = new JTextField(Globals.DECIMAL_FORMAT.format(k));
        netHeightTF = new JTextField(Globals.DECIMAL_FORMAT.format(m));
        minXTF = new JTextField(Globals.DECIMAL_FORMAT.format(graphicValues.getMinY()));
        minYTF = new JTextField(Globals.DECIMAL_FORMAT.format(graphicValues.getMinY()));
        maxXTF = new JTextField(Globals.DECIMAL_FORMAT.format(graphicValues.getMaxX()));
        maxYTF = new JTextField(Globals.DECIMAL_FORMAT.format(graphicValues.getMaxY()));

        center.add(netWidth);
        center.add(netWidthTF);
        center.add(netHeight);
        center.add(netHeightTF);
        center.add(minX);
        center.add(minXTF);
        center.add(minY);
        center.add(minYTF);
        center.add(maxX);
        center.add(maxXTF);
        center.add(maxY);
        center.add(maxYTF);

        ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");

        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1,2));
        bottom.add(cancel);
        bottom.add(ok);
        cancel.addActionListener(e -> setVisible(false));

        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        pack();
    }

    public void setGraphicsOptions(GraphicValues graphicValues) {
        this.graphicValues = graphicValues;
        minXTF.setText(Globals.DECIMAL_FORMAT.format(graphicValues.getMinX()));
        minYTF.setText(Globals.DECIMAL_FORMAT.format(graphicValues.getMinY()));
        maxXTF.setText(Globals.DECIMAL_FORMAT.format(graphicValues.getMaxX()));
        maxYTF.setText(Globals.DECIMAL_FORMAT.format(graphicValues.getMaxY()));
    }
    public void setNetOptions(int k, int m){
        netWidthTF.setText(Globals.DECIMAL_FORMAT.format(k));
        netHeightTF.setText(Globals.DECIMAL_FORMAT.format(m));
    }

    public void setLogicController(LogicController logicController) {
        this.logicController = logicController;
        ok.addActionListener(e ->{
            try{
                int k = Integer.parseInt(netWidthTF.getText());
                int m = Integer.parseInt(netHeightTF.getText());
                double minX = Double.parseDouble(minXTF.getText());
                double minY = Double.parseDouble(minYTF.getText());
                double maxX = Double.parseDouble(maxXTF.getText());
                double maxY = Double.parseDouble(maxYTF.getText());
                if(k < Globals.MIN_NET || k > Globals.MAX_NET || m < Globals.MIN_NET || m > Globals.MAX_NET){
                    new ErrorWindow("Check parameters of net! They must be in range [1; 1000]").show();
                    return;
                }
                if(minX >= maxX || minY >= maxY){
                    new ErrorWindow("Check area values!Min value must be less than max value!").show();
                    return;
                }
                graphicValues = new GraphicValues(minX, minY, maxX, maxY);
                this.logicController.setParameters(graphicValues, k, m);
                setVisible(false);
            } catch (NumberFormatException e1){
                new ErrorWindow("Not a number: " + e1.getMessage()).show();
            }
        });
    }
}
