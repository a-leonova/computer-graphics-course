package com.leonova.fit.nsu.view;

import com.leonova.fit.nsu.model.GameOptions;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class WindowView {

    private GraphiscOptions options;

    private JFrame frame = new JFrame("Conway's Life");
    private GameField field;

    public WindowView(GraphiscOptions options){
        this.options = options;

        field = new GameField(options);
        JMenuBar menu = createMenu();
        JToolBar toolBar = createToolBar();
        frame.add(toolBar);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(toolBar, BorderLayout.PAGE_START);
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

    private JToolBar createToolBar(){
        JToolBar toolBar = new JToolBar();

        JButton newFileButton = new JButton();
        newFileButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-new-file-16.png"))));
        JButton openFileButton = new JButton();
        openFileButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-open-folder-16.png"))));
        JButton saveButton = new JButton();
        saveButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-save-16.png"))));
        JButton displayImpactButton = new JButton();
        displayImpactButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-1-key-16.png"))));
        JButton xorButton = new JButton();
        xorButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-triangle-16.png"))));
        JButton replaceButton = new JButton();
        replaceButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-circle-16.png"))));
        JButton clearButton = new JButton();
        clearButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-trash-can-16.png"))));
        JButton parametersButton = new JButton();
        parametersButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-table-of-content-16.png"))));
        JButton stepButton = new JButton();
        stepButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-resume-button-16.png"))));
        JButton runButton = new JButton();
        runButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-play-16.png"))));
        JButton aboutButton = new JButton();
        aboutButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-question-mark-in-a-chat-bubble-16.png"))));

        toolBar.add(aboutButton);
        toolBar.add(newFileButton);
        toolBar.add(openFileButton);
        toolBar.add(saveButton);
        toolBar.add(displayImpactButton);
        toolBar.add(xorButton);
        toolBar.add(replaceButton);
        toolBar.add(clearButton);
        toolBar.add(parametersButton);
        toolBar.add(stepButton);
        toolBar.add(runButton);
        return toolBar;

    }

}
