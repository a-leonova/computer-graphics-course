package com.leonova.fit.nsu.view;

import com.leonova.fit.nsu.controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class WindowView extends JFrame{

    private GraphicsOptions options;
    private GameField field;
    private GameController gameController;

    public WindowView(GraphicsOptions options){
        super("Conway's Life");
        this.options = options;

        field = new GameField(options);
        JMenuBar menu = createMenu();
        JToolBar toolBar = createToolBar();
        add(toolBar);
        setLayout(new BorderLayout());
        getContentPane().add(toolBar, BorderLayout.PAGE_START);
        JScrollPane scrollPane = new JScrollPane(field,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(500,500));
        scrollPane.setViewportView(field);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setJMenuBar(menu);
        setSize(800, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
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
        newFileButton.setToolTipText("New file");
        JButton openFileButton = new JButton();
        openFileButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-open-folder-16.png"))));
        openFileButton.setToolTipText("Open file");
        JButton saveButton = new JButton();
        saveButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-save-16.png"))));
        saveButton.setToolTipText("Save game");
        JButton displayImpactButton = new JButton();
        displayImpactButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-1-key-16.png"))));
        displayImpactButton.setToolTipText("Display impact");
        JButton xorButton = new JButton();
        xorButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-triangle-16.png"))));
        xorButton.setToolTipText("XOR");
        JButton replaceButton = new JButton();
        replaceButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-circle-16.png"))));
        replaceButton.setToolTipText("Replace");
        JButton clearButton = new JButton();
        clearButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-trash-can-16.png"))));
        clearButton.setToolTipText("Clear");
        JButton parametersButton = new JButton();
        parametersButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-table-of-content-16.png"))));
        parametersButton.setToolTipText("Parameters");
        JButton stepButton = new JButton();
        stepButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-resume-button-16.png"))));
        stepButton.setToolTipText("Next step");
        JButton runButton = new JButton();
        runButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-play-16.png"))));
        runButton.setToolTipText("Run");
        JButton aboutButton = new JButton();
        aboutButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-question-mark-in-a-chat-bubble-16.png"))));
        aboutButton.setToolTipText("About");

        toolBar.add(newFileButton);
        toolBar.add(openFileButton);
        toolBar.add(saveButton);
        toolBar.addSeparator();
        toolBar.add(displayImpactButton);
        toolBar.addSeparator();
        toolBar.add(xorButton);
        toolBar.add(replaceButton);
        toolBar.addSeparator();
        toolBar.add(clearButton);
        toolBar.add(parametersButton);
        toolBar.addSeparator();
        toolBar.add(stepButton);
        toolBar.add(runButton);
        toolBar.addSeparator();
        toolBar.add(aboutButton);
        return toolBar;

    }

}
