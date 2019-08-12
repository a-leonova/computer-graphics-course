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
import com.nsu.fit.leonova.view.windows.ErrorWindow;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class MainWindow extends JFrame implements Observer {

    private GraphicHolder imageManager;
    private ImageManager legend = new ImageManager(Globals.START_LEGEND_WIDTH, Globals.START_LEGEND_HEIGHT);
    private LogicController logicController;
    private FileController fileController;

    private JToggleButton gradient;
    private JCheckBoxMenuItem gradientMenuItem = new JCheckBoxMenuItem("Gradient");

    private JToggleButton netButton;
    private JCheckBoxMenuItem netMenuItem = new JCheckBoxMenuItem("Draw net");

    private JLabel statusLabel = new JLabel("status");
    private AboutWindow aboutWindow = new AboutWindow();
    private ConfigurationWindow configurationWindow;

    private int oldFrameWidth, oldWindowHeight;

    public MainWindow(GraphicValues graphicValues, int k, int m) {
        super("Isolines");
        imageManager = new GraphicHolder(Globals.START_IMAGE_WIDTH, Globals.START_IMAGE_HEIGHT);
        oldFrameWidth = 600;
        oldWindowHeight = 650;
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

        this.addComponentListener(new MyComponentAdapter());

        configurationWindow = new ConfigurationWindow(graphicValues, k, m);
        setMinimumSize(new Dimension(Globals.MIN_FRAME_WIDTH, Globals.MIN_FRAME_HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setLogicController(LogicController logicController) {
        this.logicController = logicController;
        configurationWindow.setLogicController(logicController);
    }

    public void setImageController(ImageController imageController) {
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

    @Override
    public void error(String message) {
        new ErrorWindow(message).show();
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        gradient = createButton(JToggleButton.class, e -> {
            logicController.gradientWasPressed();
            gradientMenuItem.setSelected(gradient.isSelected());
        }, "icons/gradient.png", "Gradient");
        netButton = createButton(JToggleButton.class, e -> {
            logicController.drawNet();
            netMenuItem.setSelected(gradient.isSelected());
        }, "icons/net.png", "Draw net");
        JButton eraser = createButton(JButton.class, e -> logicController.eraseIsolines(), "icons/eraser.png", "Erase isolines");
        JButton allIsolines = createButton(JButton.class, e -> logicController.drawAllLevelIsolines(), "icons/allIsolines.png", "Draw all isolines");
        JButton pivotPoints = createButton(JButton.class, e -> logicController.pivotPoints(), "icons/points.png", "Pivot points");
        JButton openFile = createButton(JButton.class, e -> {
            new OpenConfigFileHandler(fileController).openConfig();
        }, "icons/icons8-open-folder-16.png", "Open config file");
        JButton config = createButton(JButton.class, e -> configurationWindow.setVisible(true), "icons/icons8-settings-button-24.png", "Graphic Configurations");
        JButton about = createButton(JButton.class, e -> aboutWindow.show(), "icons/icons8-question-mark-in-a-chat-bubble-16.png", "About");
        toolBar.add(openFile);
        toolBar.addSeparator();
        toolBar.add(gradient);
        toolBar.add(eraser);
        toolBar.add(netButton);
        toolBar.add(allIsolines);
        toolBar.add(pivotPoints);
        toolBar.addSeparator();
        toolBar.add(config);
        toolBar.add(about);
        return toolBar;
    }

    private JMenuBar createMenu() {
        JMenuBar menu = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem openConfigs = createButton(JMenuItem.class, e -> new OpenConfigFileHandler(fileController).openConfig(), "Open configuration file");
        file.add(openConfigs);

        JMenu graphic = new JMenu("Graphic");
        graphic.add(gradientMenuItem);
        gradientMenuItem.addActionListener(e -> {
            gradient.setSelected(gradientMenuItem.isSelected());
            logicController.gradientWasPressed();
        });
        JMenuItem isolines = createButton(JMenuItem.class, e -> logicController.drawAllLevelIsolines(), "Show all isolines");
        netMenuItem = createButton(JCheckBoxMenuItem.class, e -> {
            netButton.setSelected(netMenuItem.isSelected());
            logicController.drawNet();
        }, "Draw net");
        JMenuItem eraser = createButton(JMenuItem.class, e -> logicController.eraseIsolines(), "Erase isolines");
        JMenuItem pivots = createButton(JMenuItem.class, e -> logicController.pivotPoints(), "Pivot points");
        JMenu aboutMenu = new JMenu("About");
        JMenuItem about = createButton(JMenuItem.class, e -> aboutWindow.show(), "About");
        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem setting = createButton(JMenuItem.class, e -> configurationWindow.setVisible(true), "Settings");
        aboutMenu.add(about);
        settingsMenu.add(setting);
        graphic.add(isolines);
        graphic.add(netMenuItem);
        graphic.add(eraser);
        graphic.add(pivots);

        menu.add(file);
        menu.add(graphic);
        menu.add(settingsMenu);
        menu.add(aboutMenu);

        return menu;
    }

    public <T extends AbstractButton> T createButton(Class<T> targetClass, ActionListener listener, String... args) {
        try {
            T button = targetClass.newInstance();
            button.addActionListener(listener);
            if (args.length == 2) {
                button.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(args[0]))));
                button.setToolTipText(args[1]);
            }
            if (args.length == 1) {
                button.setText(args[0]);
            }
            return button;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class MyComponentAdapter extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            Rectangle rectangle = e.getComponent().getBounds();
            if (rectangle.width == oldFrameWidth && rectangle.height == oldWindowHeight) {
                return;
            }
            int newImageWidth = imageManager.getImageWidth() + (rectangle.width - oldFrameWidth);
            int newImageHeight = imageManager.getImageHeight() + (rectangle.height - oldWindowHeight);
            if (logicController != null) {
                logicController.resizeImage(newImageWidth, newImageHeight);
            }
            oldFrameWidth = rectangle.width;
            oldWindowHeight = rectangle.height;
        }
    }
}
