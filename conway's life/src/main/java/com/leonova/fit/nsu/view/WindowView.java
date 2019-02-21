package com.leonova.fit.nsu.view;

import com.leonova.fit.nsu.model.GameOptions;

import javax.swing.*;

public class WindowView {

    private GraphiscOptions options;

    private JFrame frame = new JFrame("Conway's Life");
    private GameField field;

    public WindowView(GraphiscOptions options){
        this.options = options;

        field = new GameField(options);
        JMenuBar menu = createMenu();
        frame.setJMenuBar(menu);
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(field);
        frame.add(field);

        //frame.setVisible(true);
    }

    public void setVisible(boolean value){
        frame.setVisible(value);
    }

    private JMenuBar createMenu(){
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem newFile = new JMenuItem("New file");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem saveAs = new JMenuItem("Save as...");
        JMenuItem exit = new JMenuItem("Exit");
        file.add(newFile);
        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.add(exit);

        JMenu edit = new JMenu("Edit");
        JMenuItem xor = new JMenuItem("XOR");
        JMenuItem replace = new JMenuItem("Replace");
        JMenuItem clear = new JMenuItem("Clear");
        JMenuItem parameters = new JMenuItem("Parameters");
        edit.add(xor);
        edit.add(replace);
        edit.add(clear);
        edit.add(parameters);



        JMenu view = new JMenu("View");

        JMenu simulation = new JMenu("Simulation");
        JMenuItem step = new JMenuItem("Step");
        JMenuItem run = new JMenuItem("Run");
        simulation.add(step);
        simulation.add(run);

        JMenu help = new JMenu("Help");

        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(view);
        menuBar.add(simulation);
        menuBar.add(help);

        return menuBar;
    }

}
