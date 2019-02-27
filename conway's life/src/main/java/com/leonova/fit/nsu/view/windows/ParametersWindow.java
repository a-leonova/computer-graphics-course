package com.leonova.fit.nsu.view.windows;

import com.leonova.fit.nsu.controller.GameController;
import com.leonova.fit.nsu.model.GameOptions;
import com.leonova.fit.nsu.view.GraphicsOptions;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class ParametersWindow {

    private GraphicsOptions graphicsOptions;

    private GameController controller;
    private JFrame window;

    private JRadioButton xor;
    private JRadioButton replace;

    private JTextField widthTF;
    private JTextField heightTF;

    private JTextField lineTF;
    private JTextField edgeTF;

    private JTextField frsImpTF;
    private JTextField scdImpTF;
    private JTextField liveBegTF;
    private JTextField liveEndTF;
    private JTextField birthBeginTF;
    private JTextField birthEndTF;


    public ParametersWindow(GraphicsOptions graphicsOptions) {
        this.graphicsOptions = graphicsOptions;
        window = new JFrame("Parameters");
        //window.setSize(100, 100);
        JPanel modePanel = createModePanel();
        JPanel fieldPanel = createFieldPanel();
        JPanel cellPanel = createCellPanel();
        JPanel gamePanel = createGamePanel();

        window.add(modePanel, BorderLayout.WEST);
        window.add(fieldPanel, BorderLayout.CENTER);
        window.add(cellPanel, BorderLayout.EAST);
        window.add(gamePanel, BorderLayout.SOUTH);

        window.pack();
    }

    public void show(){
        window.setVisible(true);
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    private JPanel createGamePanel(){
        JPanel gamePanel = new JPanel();
        GridLayout layout = new GridLayout(2, 7);
        gamePanel.setLayout(layout);

        Label frsImpLabel = new Label("First impact:");
        Label scdImpLabel = new Label("Second impact:");
        Label liveBegLabel = new Label("Live begin:");
        Label liveEndLabel = new Label("Live end:");
        Label birthBeginLabel = new Label("Birth begin:");
        Label birthEndLabel = new Label("Birth end:");
        gamePanel.add(frsImpLabel,0);
        gamePanel.add(scdImpLabel,1);
        gamePanel.add(liveBegLabel);
        gamePanel.add(liveEndLabel);
        gamePanel.add(birthBeginLabel);
        gamePanel.add(birthEndLabel);

        frsImpTF = new JTextField("1");
        scdImpTF = new JTextField("0.3");
        liveBegTF = new JTextField("2");
        liveEndTF = new JTextField("3.3");
        birthBeginTF = new JTextField("2.3");
        birthEndTF = new JTextField("2.9");

        gamePanel.add(frsImpTF);
        gamePanel.add(scdImpTF);
        gamePanel.add(liveBegTF);
        gamePanel.add(liveEndTF);
        gamePanel.add(birthBeginTF);
        gamePanel.add(birthEndTF);

        JButton ok = new JButton("OK");
        JButton cancel = new JButton("CANCEL");

        ok.addActionListener(e->{

            //TODO: window on ERROR
            GameOptions gameOptions = new GameOptions();

            gameOptions.setModeXor(xor.isSelected());
            double frs = Double.parseDouble(frsImpTF.getText());
            double scd = Double.parseDouble(scdImpTF.getText());
            double lBeg = Double.parseDouble(liveBegTF.getText());
            double lEnd = Double.parseDouble(liveEndTF.getText());
            double bBeg = Double.parseDouble(birthBeginTF.getText());
            double bEnd = Double.parseDouble(birthEndTF.getText());

            gameOptions.setLiveEnd(lEnd);
            gameOptions.setBirthBegin(bBeg);
            gameOptions.setBirthEnd(bEnd);
            gameOptions.setFirstImpact(frs);
            gameOptions.setSecondImpact(scd);
            gameOptions.setLiveBegin(lBeg);

            GraphicsOptions graphicsOptions = new GraphicsOptions();
            graphicsOptions.setShowImpact(this.graphicsOptions.isShowImpact());
            int width = Integer.parseInt(widthTF.getText());
            int height = Integer.parseInt(heightTF.getText());
            int line = Integer.parseInt(lineTF.getText());
            int edge = Integer.parseInt(edgeTF.getText());
            graphicsOptions.setCellEdge(edge);
            graphicsOptions.setCellsInColumn(width);
            graphicsOptions.setCellsInRow(height);
            graphicsOptions.setLineWidth(line);

            controller.newOptions(gameOptions, graphicsOptions);
            window.setVisible(false);
        });

        cancel.addActionListener(e->{
            window.setVisible(false);
        });

        gamePanel.add(ok);
        gamePanel.add(cancel);

        return gamePanel;
    }

    private JPanel createCellPanel(){
        JPanel cellPanel = new JPanel();
        GridLayout layout = new GridLayout(2, 3);
        cellPanel.setLayout(layout);

        Label edgeLabel = new Label("Edge: ");
        edgeTF = new JTextField("10");
        JSlider edgeSlider = new JSlider(1, 100, 10);
        edgeSlider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                edgeTF.setText(String.valueOf(edgeSlider.getValue()));
            }
        });
        edgeTF.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
                String typed = edgeTF.getText();
                edgeSlider.setValue(0);
                if(!typed.matches("\\d+") || typed.length() > 3) {
                    return;
                }
                int value = Integer.parseInt(typed);
                edgeSlider.setValue(value);
            }
        });

        Label lineLabel = new Label("Line weight: ");
        lineTF = new JTextField("1");
        JSlider lineSlider = new JSlider(1, 15, 1);
        lineSlider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                lineTF.setText(String.valueOf(lineSlider.getValue()));
            }
        });
        edgeTF.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
                String typed = lineTF.getText();
                lineSlider.setValue(0);
                if(!typed.matches("\\d+") || typed.length() > 3) {
                    return;
                }
                int value = Integer.parseInt(typed);
                lineSlider.setValue(value);
            }
        });

        cellPanel.add(edgeLabel);
        cellPanel.add(edgeTF);
        cellPanel.add(edgeSlider);
        cellPanel.add(lineLabel);
        cellPanel.add(lineTF);
        cellPanel.add(lineSlider);

        return cellPanel;
    }

    private JPanel createFieldPanel(){
        JPanel fieldPanel = new JPanel();
        GridLayout layout = new GridLayout(3, 2);
        fieldPanel.setLayout(layout);

        Label fieldLabel = new Label("Field:");
        Label spaces = new Label("  ");
        fieldPanel.add(fieldLabel);
        fieldPanel.add(spaces);

        Label width = new Label("Width:");
        widthTF = new JTextField("10");
        fieldPanel.add(width);
        fieldPanel.add(widthTF);

        Label heightLabel = new Label("Height: ");
        heightTF = new JTextField("10");
        fieldPanel.add(heightLabel);
        fieldPanel.add(heightTF);

        return fieldPanel;
    }

    private JPanel createModePanel(){
        JPanel modePanel = new JPanel();
        GridLayout layout = new GridLayout(3,1);
        modePanel.setLayout(layout);

        xor = new JRadioButton("XOR");
        xor.setBounds(75,50,100,30);
        xor.setSelected(true);
        replace = new JRadioButton("Replace");
        replace.setBounds(75,50,100,30);
        ButtonGroup bg=new ButtonGroup();
        bg.add(xor);bg.add(replace);
        Label mode = new Label("Mode:");
        modePanel.add(mode);
        modePanel.add(xor);
        modePanel.add(replace);
        return modePanel;
    }



}
