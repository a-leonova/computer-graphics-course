package com.nsu.fit.leonova.view;

import com.nsu.fit.leonova.controller.FileController;
import com.nsu.fit.leonova.controller.ImageController;
import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.model.graphicProvider.DoublePoint;
import com.nsu.fit.leonova.observers.Observer;
import com.nsu.fit.leonova.controller.LogicController;

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

    public MainWindow(int width, int height){
        super("Isolines");
        imageManager = new GraphicHolder(width, height);
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

       setMinimumSize(new Dimension(600, 650));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setLogicController(LogicController logicController) {
        this.logicController = logicController;
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
        statusLabel.setText("X: " + Globals.DECIMAL_FORMAT.format(coordinates.getX()) + " Y: " + Globals.DECIMAL_FORMAT.format(coordinates.getY()) + "Z: " + Globals.DECIMAL_FORMAT.format(value));
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
        toolBar.add(openFile);
        toolBar.addSeparator();
        toolBar.add(gradient);
        toolBar.add(eraser);
        toolBar.add(net);
        toolBar.add(allIsolines);
        toolBar.add(pivotPoints);
        return toolBar;
    }

    private JMenuBar createMenu(){
        JMenuBar menu = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem openConfigs = createMenuItem("Open configuration file", e -> {});
        file.add(openConfigs);

        JMenu graphic = new JMenu("Graphic");
        graphic.add(gradientMenuItem);
        gradientMenuItem.addActionListener(e -> {
            gradient.setSelected(gradientMenuItem.isSelected());
            logicController.gradientWasPressed();
        });
        JMenuItem isolines = createMenuItem("Show all isolines", e -> logicController.drawAllLevelIsolines());
        graphic.add(isolines);

        menu.add(file);
        menu.add(graphic);

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
