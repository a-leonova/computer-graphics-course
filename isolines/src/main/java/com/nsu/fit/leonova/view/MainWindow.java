package com.nsu.fit.leonova.view;

import com.nsu.fit.leonova.controller.FileController;
import com.nsu.fit.leonova.controller.ImageController;
import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.model.graphicProvider.DoublePoint;
import com.nsu.fit.leonova.model.graphicProvider.GraphicValues;
import com.nsu.fit.leonova.observers.Observer;
import com.nsu.fit.leonova.controller.LogicController;
import com.nsu.fit.leonova.view.windows.AboutWindow;
import com.nsu.fit.leonova.view.windows.ConfigurationWindow;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Objects;

public class MainWindow extends JFrame implements Observer {

    private GraphicHolder imageManager;
    private ImageManager legend = new ImageManager(Globals.LEGEND_WIDTH, Globals.LEGEND_HEIGHT);
    private LogicController logicController;
    private FileController fileController;

    private JToggleButton gradient;
    private JCheckBoxMenuItem gradientMenuItem = new JCheckBoxMenuItem("Gradient");

    private JToggleButton netButton;
    private JCheckBoxMenuItem netMenuItem = new JCheckBoxMenuItem("Draw net");

    private JLabel statusLabel = new JLabel("status");
    private AboutWindow aboutWindow = new AboutWindow();
    private ConfigurationWindow configurationWindow;

    public MainWindow(GraphicValues graphicValues, int k, int m){
        super("Isolines");
        imageManager = new GraphicHolder(Globals.WIDTH, Globals.HEIGHT);
        JToolBar toolBar = createToolBar();
        JMenuBar menuBar = createMenu();
        setJMenuBar(menuBar);

        add(toolBar, BorderLayout.PAGE_START);
        add(imageManager, BorderLayout.CENTER);
        add(legend, BorderLayout.EAST);

        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setPreferredSize(new Dimension(getWidth(), 16));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);

        configurationWindow = new ConfigurationWindow(graphicValues, k, m);
        setMinimumSize(new Dimension(600, 650));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setLogicController(LogicController logicController) {
        this.logicController = logicController;
        configurationWindow.setLogicController(logicController);
    }

    public void setImageController(ImageController imageController){
        imageManager.setImageController(imageController);
    }

    public void setFileController(FileController fileController) {
        this.fileController = fileController;
    }

    @Override
    public void setImage(BufferedImage image) {
        imageManager.setImage(image);
    }

    @Override
    public void setLegend(BufferedImage legendImg) {
        legend.setImage(legendImg);
    }

    @Override
    public void setCoordinates(DoublePoint coordinates, double value) {
        statusLabel.setText("X: " + Globals.DECIMAL_FORMAT.format(coordinates.getX()) + " Y: " + Globals.DECIMAL_FORMAT.format(coordinates.getY()) + " Z: " + Globals.DECIMAL_FORMAT.format(value));
    }

    @Override
    public void setGraphicValues(GraphicValues graphicValues) {
        configurationWindow.setGraphicsOptions(graphicValues);
    }

    @Override
    public void setNetParameters(int k, int m) {
        configurationWindow.setNetOptions(k, m);
    }

    private JToolBar createToolBar(){
        JToolBar toolBar = new JToolBar();
        gradient = createToggleButton("icons/gradient.png", "Gradient", e -> {
            logicController.gradientWasPressed();
            gradientMenuItem.setSelected(gradient.isSelected());
        });
        JToggleButton net = createToggleButton("icons/net.png", "Draw net", e -> {
            logicController.drawNet();
            netMenuItem.setSelected(gradient.isSelected());
        });
        JButton eraser = createButton("icons/eraser.png", "Erase isolines", e->logicController.eraseIsolines());
        JButton allIsolines = createButton("icons/allIsolines.png", "Draw all isolines", e -> logicController.drawAllLevelIsolines());
        JButton pivotPoints = createButton("icons/points.png", "Pivot points", e -> logicController.pivotPoints());
        JButton openFile = createButton("icons/icons8-open-folder-16.png", "Open config file", e ->{
            new OpenConfigFileHandler(fileController).openConfig();
        });
        JButton config = createButton("icons/icons8-settings-button-24.png", "Graphic Configurations", e ->configurationWindow.setVisible(true));
        JButton about = createButton("icons/icons8-question-mark-in-a-chat-bubble-16.png", "About", e->aboutWindow.show());
        toolBar.add(openFile);
        toolBar.addSeparator();
        toolBar.add(gradient);
        toolBar.add(eraser);
        toolBar.add(net);
        toolBar.add(allIsolines);
        toolBar.add(pivotPoints);
        toolBar.addSeparator();
        toolBar.add(config);
        toolBar.add(about);
        return toolBar;
    }

    private JMenuBar createMenu(){
        JMenuBar menu = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem openConfigs = createMenuItem("Open configuration file", e -> new OpenConfigFileHandler(fileController).openConfig());
        file.add(openConfigs);

        JMenu graphic = new JMenu("Graphic");
        graphic.add(gradientMenuItem);
        gradientMenuItem.addActionListener(e -> {
            gradient.setSelected(gradientMenuItem.isSelected());
            logicController.gradientWasPressed();
        });
        JMenuItem isolines = createMenuItem("Show all isolines", e -> logicController.drawAllLevelIsolines());
        JMenuItem net = createMenuItem("Draw net", e -> logicController.drawNet());
        JMenuItem eraser = createMenuItem("Erase isolines", e -> logicController.eraseIsolines());
        JMenuItem pivots = createMenuItem("Pivot points", e -> logicController.pivotPoints());
        JMenu aboutMenu = new JMenu("About");
        JMenuItem  about = createMenuItem("About", e->aboutWindow.show());
        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem  setting = createMenuItem("Settings", e->configurationWindow.setVisible(true));
        aboutMenu.add(about);
        settingsMenu.add(setting);
        graphic.add(isolines);
        graphic.add(net);
        graphic.add(eraser);
        graphic.add(pivots);

        menu.add(file);
        menu.add(graphic);
        menu.add(setting);
        menu.add(aboutMenu);

        return menu;
    }
    private JButton createButton(String relativePath, String tip, ActionListener actionListener) {
        JButton button = new JButton();
        button.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(relativePath))));
        button.setToolTipText(tip);
        button.addActionListener(actionListener);
        return button;
    }

    private JToggleButton createToggleButton(String relativePath, String tip, ActionListener actionListener){
        JToggleButton button = new JToggleButton();
        button.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(relativePath))));
        button.setToolTipText(tip);
        button.addActionListener(actionListener);
        return button;
    }

    private JMenuItem createMenuItem(String name, ActionListener listener){
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(listener);
        return item;
    }
}
