package com.nsu.fit.leonova.view.windows.worldWindow;

import com.nsu.fit.leonova.controller.WorldController;
import com.nsu.fit.leonova.model.Point3D;
import com.nsu.fit.leonova.model.world.WorldParameters;
import com.nsu.fit.leonova.observer.WorldObserver;
import com.nsu.fit.leonova.view.ImageManager;
import com.nsu.fit.leonova.view.windows.AboutWindow;
import com.nsu.fit.leonova.view.windows.ErrorWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class WorldWindow extends JFrame implements WorldObserver {
    private ImageManager imageManager;
    private WorldController worldController;
    private FigureSettingsPanel figureSettingsPanel;
    private AboutWindow aboutWindow = new AboutWindow();

    public WorldWindow(int width, int height, WorldController worldController) throws HeadlessException {
        super("Wireframe");
        this.worldController = worldController;
        figureSettingsPanel = new FigureSettingsPanel(worldController);
        imageManager = new ImageManager(width, height);
        imageManager.setMouseListener(new MyMouseAdapter());
        JToolBar toolBar = createToolBar();
        JMenuBar menuBar = createMenuBar();
        add(toolBar, BorderLayout.PAGE_START);
        add(imageManager, BorderLayout.CENTER);
        add(figureSettingsPanel, BorderLayout.EAST);
        setJMenuBar(menuBar);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void setWorldController(WorldController worldController) {
        this.worldController = worldController;
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        JButton settings = createButton(JButton.class, e -> worldController.settingsButtonPressed(),  "icons/icons8-table-of-content-16.png", "Create B-spline");
        JButton openFile = createButton(JButton.class, e -> new OpenConfigFileHandler(worldController).openConfig(),  "icons/icons8-open-folder-16.png", "Open file");
        JButton saveFile = createButton(JButton.class, e -> new SaveFileHandler(worldController).saveFile(),  "icons/icons8-save-16.png", "Save file");
        JButton resetAngles = createButton(JButton.class, e -> worldController.resetAngles(),  "icons/icons8-measurement-tool-16.png", "Reset angles");
        JButton about = createButton(JButton.class, e -> aboutWindow.setVisible(true),  "icons/icons8-question-mark-in-a-chat-bubble-16.png", "About");
        toolBar.add(openFile);
        toolBar.add(saveFile);
        toolBar.add(resetAngles);
        toolBar.add(settings);
        toolBar.add(about);
        return toolBar;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem openConfigs = createButton(JMenuItem.class, e -> new OpenConfigFileHandler(worldController).openConfig(), "Open configuration file");
        JMenuItem saveFile = createButton(JMenuItem.class, e -> new SaveFileHandler(worldController).saveFile(), "Save file");
        file.add(saveFile);
        file.add(openConfigs);

        JMenu world = new JMenu("World");
        JMenuItem resetAngles = createButton(JMenuItem.class, e -> worldController.resetAngles(), "Reset angles");
        JMenuItem settings = createButton(JMenuItem.class, e -> worldController.settingsButtonPressed(), "Settings");
        world.add(settings);
        world.add(resetAngles);

        JMenu aboutMenu = new JMenu("About");
        JMenuItem about = createButton(JMenuItem.class, e -> aboutWindow.setVisible(true), "About");
        aboutMenu.add(about);

        menu.add(file);
        menu.add(world);
        menu.add(aboutMenu);

        return menu;
    }

    private  <T extends AbstractButton> T createButton(Class<T> targetClass, ActionListener listener, String... args) {
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

    @Override
    public void setMainImage(BufferedImage image) {
        imageManager.setImage(image);
    }

    @Override
    public void addFigure(String name) {
        figureSettingsPanel.addFigure(name);
    }

    @Override
    public void removeFigure(int index) {
        figureSettingsPanel.removeFigure(index);
    }

    @Override
    public void renameFigure(String name, int index) {
        figureSettingsPanel.renameFigure(name, index);
    }

    @Override
    public void setInfo(Point3D figureCenter) {
        figureSettingsPanel.setInfo(figureCenter);
    }

    @Override
    public void updateWorldParameters(WorldParameters worldParameters) {
        figureSettingsPanel.updateWorldParameters(worldParameters);
    }

    @Override
    public void showError(String message) {
        new ErrorWindow(message).setVisible(true);
    }

    private class MyMouseAdapter extends MouseAdapter {

        private Point oldPoint;

        @Override
        public void mousePressed(MouseEvent e){
            oldPoint = e.getPoint();
        }

        @Override
        public void mouseDragged(MouseEvent e){
            if(oldPoint != null){
                int dx = oldPoint.x - e.getX();
                int dy = oldPoint.y - e.getY();
                if(dx != 0){
                    worldController.shiftX(dx);
                }
                if(dy != 0){
                    worldController.shiftY(dy);
                }
            }
            oldPoint = e.getPoint();
        }

        @Override
        public void mouseReleased(MouseEvent e){
            oldPoint = null;
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e){
            System.out.println("It's work!");
        }

    }
}
