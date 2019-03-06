package com.leonova.fit.nsu.view;

import com.leonova.fit.nsu.controller.FileManager;
import com.leonova.fit.nsu.controller.GameController;
import com.leonova.fit.nsu.model.Cell;
import com.leonova.fit.nsu.model.GameOptions;
import com.leonova.fit.nsu.observer.Observer;
import com.leonova.fit.nsu.view.windows.AboutWindow;
import com.leonova.fit.nsu.view.windows.AskSaveWindow;
import com.leonova.fit.nsu.view.windows.ParametersWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashSet;
import java.util.Objects;

public class WindowView extends JFrame implements Observer, ParametersWindowHandler {

    private GraphicsOptions graphicsOptions;
    private GameOptions gameOptions;
    private GameField field;
    private GameController gameController;
    private FileManager fileManager;

    private JCheckBoxMenuItem run;
    private JToggleButton runButton;
    private JScrollPane scrollPane;

    private AboutWindow aboutWindow = new AboutWindow();
    private ParametersWindow parametersWindow;
    private AskSaveWindow askSaveWindow = new AskSaveWindow(this);

    //private HashMap<JButton, JMenuItem> pairsToolBarMenu = new HashMap<>();

    private JMenuItem clearMI;
    private JMenuItem parametersMI;
    private JMenuItem stepMI;
    private JButton clearButton;
    private JButton parametersButton;
    private JButton stepButton;

    private JToggleButton xorButton = new JToggleButton();
    private JToggleButton replaceButton = new JToggleButton();
    private JRadioButtonMenuItem xorMI = new JRadioButtonMenuItem("XOR");
    private JRadioButtonMenuItem replaceMI = new JRadioButtonMenuItem("REPLACE");
    private XorReplaceHandler xorReplaceHandler = new XorReplaceHandler(xorButton, replaceButton, xorMI, replaceMI);

    public WindowView(GraphicsOptions graphicsOptions, GameOptions gameOptions){
        super("Conway's Life");
        this.graphicsOptions = graphicsOptions;
        this.gameOptions = gameOptions;
        parametersWindow = new ParametersWindow(graphicsOptions, gameOptions,this);

        field = new GameField(graphicsOptions);
        JMenuBar menu = createMenu();
        JToolBar toolBar = createToolBar();

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(replaceButton);
        buttonGroup.add(xorButton);
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(xorMI);
        buttonGroup1.add(replaceMI);

        replaceButton.setSelected(true);
        replaceMI.setSelected(true);
//        xorMI.setSelected(false);
//        xorButton.setSelected(false);
        add(toolBar);
        setLayout(new BorderLayout());
        getContentPane().add(toolBar, BorderLayout.PAGE_START);
        scrollPane = new JScrollPane(field,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(500,500));
        scrollPane.setViewportView(field);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setJMenuBar(menu);
        setSize(800, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
        field.setGameController(gameController);
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    private JMenuBar createMenu(){
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem newFile = new JMenuItem("New file");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        fileMenu.add(newFile);
        fileMenu.add(open);
        fileMenu.add(save);

        newFile.addActionListener(e->{
            askSaveWindow.show();
        });

        save.addActionListener(e->{

            JFileChooser jFileChooser = new JFileChooser();
            if(jFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
                File file =  jFileChooser.getSelectedFile();
                fileManager.save(file, graphicsOptions);
            }
        });
        open.addActionListener(e->{
            JFileChooser jFileChooser = new JFileChooser();
            if(jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                File file =  jFileChooser.getSelectedFile();
                fileManager.open(file);
            }
        });


        JMenu edit = new JMenu("Edit");
        xorMI.addActionListener(e -> {
            xorReplaceHandler.xorPressed(gameController);
        });
        replaceMI.addActionListener(e -> {
            xorReplaceHandler.replacePressed(gameController);
            });
        replaceMI.setSelected(true);
        clearMI = new JMenuItem("Clear");
        clearMI.addActionListener(e -> gameController.clearField());
        parametersMI = new JMenuItem("Parameters");
        parametersMI.addActionListener(e->{
            parametersWindow.setHandler(this);
            parametersWindow.show();
        });
        edit.add(xorMI);
        edit.add(replaceMI);
        edit.add(clearMI);
        edit.add(parametersMI);

        JMenu simulation = new JMenu("Simulation");
        stepMI = new JMenuItem("Step");
        stepMI.addActionListener(e -> gameController.nextStep());
        run = new JCheckBoxMenuItem("Run");
        run.setSelected(false);
        run.addActionListener(e -> {
            runButton.setSelected(!runButton.isSelected());
            parametersButton.setEnabled(!parametersButton.isEnabled());
            clearButton.setEnabled(!clearButton.isEnabled());
            stepButton.setEnabled(!stepButton.isEnabled());
            stepMI.setEnabled(!stepMI.isEnabled());
            clearMI.setEnabled(!clearMI.isEnabled());
            parametersMI.setEnabled(!parametersMI.isEnabled());

            gameController.run();
        });
        simulation.add(stepMI);
        simulation.add(run);

        JMenu help = new JMenu("About");
        JMenuItem  about = new JMenuItem("About");
        about.addActionListener(e -> aboutWindow.show());
        JMenuItem  helpIt = new JMenuItem("Help");
        help.add(about);
        help.add(helpIt);

        menuBar.add(fileMenu);
        menuBar.add(edit);
        menuBar.add(simulation);
        menuBar.add(help);

        return menuBar;
    }

    private JToolBar createToolBar(){
        JToolBar toolBar = new JToolBar();

        JButton newFileButton = new JButton();
        newFileButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-new-file-16.png"))));
        newFileButton.setToolTipText("New file");
        newFileButton.addActionListener(e->{
            askSaveWindow.show();
        });

        JButton openFileButton = new JButton();
        openFileButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-open-folder-16.png"))));
        openFileButton.setToolTipText("Open file");
        openFileButton.addActionListener(e->{
            JFileChooser jFileChooser = new JFileChooser();
            if(jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                File file =  jFileChooser.getSelectedFile();
                fileManager.open(file);
            }
        });
        JButton saveButton = new JButton();
        saveButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-save-16.png"))));
        saveButton.setToolTipText("Save game");

        saveButton.addActionListener(e->{

            JFileChooser jFileChooser = new JFileChooser();
            if(jFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
                File file =  jFileChooser.getSelectedFile();
                fileManager.save(file, graphicsOptions);
            }
        });

        JButton displayImpactButton = new JButton();
        displayImpactButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-1-key-16.png"))));
        displayImpactButton.setToolTipText("Display impact");
        displayImpactButton.addActionListener(e -> gameController.displayImpact());
        xorButton.addActionListener(e-> {
            xorReplaceHandler.xorPressed(gameController);
        });
        xorButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-triangle-16.png"))));
        xorButton.setToolTipText("XOR");
        replaceButton.addActionListener(e->{
            xorReplaceHandler.replacePressed(gameController);
        });
        replaceButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-circle-16.png"))));
        replaceButton.setToolTipText("Replace");
        clearButton = new JButton();
        clearButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-trash-can-16.png"))));
        clearButton.setToolTipText("Clear");
        clearButton.addActionListener(e->gameController.clearField());
        parametersButton = new JButton();
        parametersButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-table-of-content-16.png"))));
        parametersButton.setToolTipText("Parameters");
        parametersButton.addActionListener(e->{
                parametersWindow.setHandler(this);
                parametersWindow.show();
            });
        stepButton = new JButton();
        stepButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-resume-button-16.png"))));
        stepButton.setToolTipText("Next step");
        stepButton.addActionListener(e -> gameController.nextStep());
        runButton = new JToggleButton();
        runButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-play-16.png"))));
        runButton.setToolTipText("Run");
        runButton.addActionListener(e ->{
            run.setSelected(!run.isSelected());
            parametersButton.setEnabled(!parametersButton.isEnabled());
            clearButton.setEnabled(!clearButton.isEnabled());
            stepButton.setEnabled(!stepButton.isEnabled());
            stepMI.setEnabled(!stepMI.isEnabled());
            clearMI.setEnabled(!clearMI.isEnabled());
            parametersMI.setEnabled(!parametersMI.isEnabled());
            gameController.run();
        });
        JButton aboutButton = new JButton();
        aboutButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-question-mark-in-a-chat-bubble-16.png"))));
        aboutButton.setToolTipText("About");
        aboutButton.addActionListener(e->{
            aboutWindow.show();
        });

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

    @Override
    public void updateGraphicField(HashSet<Cell> changedCells) {
        field.updateGraphicField(changedCells);
    }

    @Override
    public void displayImpact(HashSet<Cell> cells) {

        graphicsOptions.setShowImpact(!graphicsOptions.isShowImpact());

        field.updateGraphicField(cells);

    }

    @Override
    public void repaintAll(HashSet<Cell> cells, GraphicsOptions graphicsOptions) {
        this.graphicsOptions = graphicsOptions;
        parametersWindow.setGraphicsOptions(graphicsOptions);
        getContentPane().remove(scrollPane);

        field = new GameField(this.graphicsOptions);
        field.setGameController(gameController);
        field.updateGraphicField(cells);
        scrollPane = new JScrollPane(field,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane = new JScrollPane(field,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(500,500));
        scrollPane.setViewportView(field);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().validate();
    }

    public void handleAskSave(Answer answer){
        switch (answer){
            case YES:
                JFileChooser jFileChooser = new JFileChooser();
                if(jFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
                    File file =  jFileChooser.getSelectedFile();
                    fileManager.save(file, graphicsOptions);
                }
                parametersWindow.setHandler(new ParametersNewFileHandler(gameController));
                parametersWindow.show();
                break;
            case NO:
                parametersWindow.show();
                break;
            case CANCEL:
                break;
        }
    }

    @Override
    public void handle(GraphicsOptions graphicsOptions, GameOptions gameOptions) {
        if(gameOptions.isModeXor()){
            xorReplaceHandler.xorPressed(gameController);
        }
        else{
            xorReplaceHandler.replacePressed(gameController);
        }
        replaceMI.setSelected(!gameOptions.isModeXor());
        xorMI.setSelected(gameOptions.isModeXor());
        gameController.newOptions(gameOptions, graphicsOptions);
    }
}
