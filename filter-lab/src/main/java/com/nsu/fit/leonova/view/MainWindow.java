package com.nsu.fit.leonova.view;

import com.nsu.fit.leonova.controller.FileManager;
import com.nsu.fit.leonova.controller.ImageController;
import com.nsu.fit.leonova.model.FiltersType;
import com.nsu.fit.leonova.observer.Observer;
import com.nsu.fit.leonova.view.handlers.ConfigOpeningHandler;
import com.nsu.fit.leonova.view.handlers.ImageOpeningHandler;
import com.nsu.fit.leonova.view.handlers.SaveImageHandler;
import com.nsu.fit.leonova.view.parametersWindow.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainWindow extends JFrame implements Observer {

    private JToggleButton select;

    private ImagesHolder imagesHolder = new ImagesHolder();
    private GraphicsHolder graphicsHolder = new GraphicsHolder();
    private FileManager fileManager;
    private ImageController imageController;

    private OrderDitheringParamsWindow orderDitheringParamsWindow = new OrderDitheringParamsWindow();
    private FSDitheringParamsWindow fsDitheringParamsWindow = new FSDitheringParamsWindow();
    private EdgeFilterParamsWindow edgeFilterParamsWindow = new EdgeFilterParamsWindow();
    private GammaParamsWindow gammaParamsWindow = new GammaParamsWindow();
    private RotationParamsWindow rotationParamsWindow = new RotationParamsWindow();
    private VolumeRenderingParamsWindow volumeRenderingParamsWindow = new VolumeRenderingParamsWindow();
    private AboutWindow aboutWindow = new AboutWindow();

    private JToggleButton emission = new JToggleButton();
    private JToggleButton absorption = new JToggleButton();

    private JCheckBoxMenuItem emissionMenu = new JCheckBoxMenuItem("Emission");
    private JCheckBoxMenuItem absorptionMenu = new JCheckBoxMenuItem("Absorption");

    public MainWindow() throws IOException {
        super("Minimal photoshop");

        JToolBar toolBar = createToolBar();
        JMenuBar menuBar = createMenu();

        add(toolBar);
        setJMenuBar(menuBar);
        getContentPane().add(toolBar, BorderLayout.PAGE_START);
        getContentPane().add(imagesHolder, BorderLayout.CENTER);
        getContentPane().add(graphicsHolder, BorderLayout.SOUTH);

        setSize(1000, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void setImageController(ImageController imageController) {
        this.imageController = imageController;
        imagesHolder.setImageController(imageController);
        orderDitheringParamsWindow.setController(imageController);
        fsDitheringParamsWindow.setController(imageController);
        edgeFilterParamsWindow.setController(imageController);
        gammaParamsWindow.setController(imageController);
        rotationParamsWindow.setController(imageController);
        volumeRenderingParamsWindow.setController(imageController);
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();

        JButton newFileButton = createButton("icons/icons8-new-file-16.png",
                "New", e -> {
                    imagesHolder.removeAllImages();
                    select.setSelected(false);
                    imagesHolder.setSelected(false);
        });

        JButton openFileButton = createButton("icons/icons8-open-folder-16.png",
                "Open image", e -> {
                    new ImageOpeningHandler(fileManager).openImage();
                    select.setSelected(false);
                    imagesHolder.setSelected(false);
        });

        JButton saveButton = createButton("icons/icons8-save-16.png",
                "Save image", e -> new SaveImageHandler(fileManager).saveFile());

        JButton desaturate = createButton("icons/greyGradation.png",
                "Desaturation", e -> imageController.filterImage(FiltersType.DESATURATION, null));

        JButton invert = createButton("icons/negative.png",
                "Invert", e -> imageController.filterImage(FiltersType.INVERT, null));

        JButton orderedDithering = createButton("icons/orderedDithering.png",
                "Ordered dithering", e -> orderDitheringParamsWindow.setVisible(true));

        JButton fsDithering = createButton("icons/floydSteinberg.png",
                "Floyd-Stainberg dithering", e -> fsDitheringParamsWindow.setVisible(true));

        JButton zoom = createButton("icons/x2.png",
                "Zoom x2", e -> imageController.filterImage(FiltersType.ZOOM, null));

        JButton roberts = createButton("icons/roberts.png",
                "Find edges (Roberts)", e ->{
                    edgeFilterParamsWindow.setType(FiltersType.ROBERTS);
                    edgeFilterParamsWindow.setVisible(true);
                });

        JButton sobel = createButton("icons/sobel.png",
                "Find edges (Sobel)", e -> {
                    edgeFilterParamsWindow.setType(FiltersType.SOBEL);
                    edgeFilterParamsWindow.setVisible(true);
                });

        JButton blur = createButton("icons/blur.png",
                "Blur", e -> imageController.filterImage(FiltersType.BLUR, null));

        JButton sharp = createButton("icons/icons8-triangle-16.png",
                "Sharp", e -> imageController.filterImage(FiltersType.SHARPEN, null));

        JButton emboss = createButton("icons/embossed.png",
                "Emboss", e -> imageController.filterImage(FiltersType.EMBOSS, null));

        JButton waterColor = createButton("icons/aqua.png",
                "Watercolor", e -> imageController.filterImage(FiltersType.WATERCOLOR, null));

        JButton gamma = createButton("icons/gamma.png",
                "Gamma", e -> gammaParamsWindow.setVisible(true));

        JButton right = createButton("icons/right.png",
                "Copy right", e -> imageController.workingImageAsFiltered());

        JButton left = createButton("icons/left.png",
                "Copy left", e -> imageController.filteredImageAsWorking());

        select = new JToggleButton();
        select.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/select.png"))));
        select.setToolTipText("Select");
        select.addActionListener(e -> imagesHolder.setSelected(select.isSelected()));

        JButton rotation = createButton("icons/rotation.png",
                "Rotation", e -> rotationParamsWindow.setVisible(true));

        JButton openConfig = createButton("icons/configFile.png",
                "Open configuration", e -> new ConfigOpeningHandler(fileManager).openConfig());

        absorption.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/absorption.png"))));
        absorption.setToolTipText("Absorption");
        absorption.addActionListener(e ->{
            imageController.absorptionWasPressed();
            absorptionMenu.setSelected(absorption.isSelected());
        });

        emission.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/emission.png"))));
        emission.setToolTipText("Emission");
        emission.addActionListener(e ->{
            imageController.emissionWasPressed();
            emissionMenu.setSelected(emission.isSelected());
        });

        JButton applyVR = createButton("icons/icons8-play-16.png",
                "Apply volume rendering",
                e -> volumeRenderingParamsWindow.setVisible(true));
        JButton aboutButton = createButton("icons/icons8-question-mark-in-a-chat-bubble-16.png", "About", e ->aboutWindow.show());

        toolBar.add(newFileButton);
        toolBar.add(openFileButton);
        toolBar.add(saveButton);
        toolBar.add(saveButton);

        toolBar.addSeparator();
        toolBar.add(right);
        toolBar.add(left);
        toolBar.add(select);

        toolBar.addSeparator();
        toolBar.add(desaturate);
        toolBar.add(invert);

        toolBar.addSeparator();
        toolBar.add(orderedDithering);
        toolBar.add(fsDithering);
        toolBar.addSeparator();
        toolBar.add(zoom);

        toolBar.addSeparator();
        toolBar.add(roberts);
        toolBar.add(sobel);

        toolBar.addSeparator();
        toolBar.add(blur);
        toolBar.add(sharp);
        toolBar.add(emboss);
        toolBar.add(waterColor);
        toolBar.add(gamma);
        toolBar.add(rotation);

        toolBar.addSeparator();
        toolBar.add(openConfig);
        toolBar.add(absorption);
        toolBar.add(emission);
        toolBar.add(applyVR);

        toolBar.addSeparator();
        toolBar.add(aboutButton);
        return toolBar;

    }

    private JMenuBar createMenu(){
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem newFile = createMenuItem("New file", e -> {
            imagesHolder.removeAllImages();
            select.setSelected(false);
            imagesHolder.setSelected(false);
        });
        JMenuItem open = createMenuItem("Open", e -> {
                new ImageOpeningHandler(fileManager).openImage();
        select.setSelected(false);
        imagesHolder.setSelected(false);
        });
        JMenuItem save = createMenuItem("Save", e -> new SaveImageHandler(fileManager).saveFile());
        fileMenu.add(newFile);
        fileMenu.add(open);
        fileMenu.add(save);

        JMenu filtersMenu = new JMenu("Filters");
        JMenu dithering = new JMenu("Dithering");
        JMenu edges = new JMenu("Finding edges");
        JMenu volumeRendering = new JMenu("Volume rendering");


        JMenuItem zoom = createMenuItem("Zoom", e ->  imageController.filterImage(FiltersType.ZOOM, null));
        JMenuItem blur = createMenuItem("Blur", e -> imageController.filterImage(FiltersType.BLUR, null));
        JMenuItem sharp = createMenuItem("Sharp", e -> imageController.filterImage(FiltersType.SHARPEN, null));
        JMenuItem gray = createMenuItem("Gray scaled", e -> imageController.filterImage(FiltersType.DESATURATION, null));
        JMenuItem invert = createMenuItem("Invert", e -> imageController.filterImage(FiltersType.INVERT, null));
        JMenuItem emboss = createMenuItem("Emboss", e -> imageController.filterImage(FiltersType.EMBOSS, null));
        JMenuItem gamma = createMenuItem("Gamma", e -> gammaParamsWindow.setVisible(true));
        JMenuItem roberts = createMenuItem("Robert's filter", e -> {
            edgeFilterParamsWindow.setType(FiltersType.ROBERTS);
            edgeFilterParamsWindow.setVisible(true);
        });
        JMenuItem sobel = createMenuItem("Sobel", e -> {
            edgeFilterParamsWindow.setType(FiltersType.SOBEL);
            edgeFilterParamsWindow.setVisible(true);
        });
        JMenuItem rotation = createMenuItem("Rotation", e -> rotationParamsWindow.setVisible(true));
        JMenuItem watercolor = createMenuItem("Watercolor", e -> imageController.filterImage(FiltersType.WATERCOLOR, null));
        JMenuItem ordered = createMenuItem("Ordered Dithering", e -> orderDitheringParamsWindow.setVisible(true));
        JMenuItem fsDithering = createMenuItem("FSDithering", e -> fsDitheringParamsWindow.setVisible(true));
        JMenuItem applyVR = createMenuItem("Apply volume rendering", e -> volumeRenderingParamsWindow.setVisible(true));
        JMenuItem openConfig = createMenuItem("Open Config", e -> new ConfigOpeningHandler(fileManager).openConfig());

        absorptionMenu.addActionListener(e ->{
            imageController.absorptionWasPressed();
            absorption.setSelected(absorptionMenu.isSelected());
        });
        emissionMenu.addActionListener(e ->{
            imageController.emissionWasPressed();
            emission.setSelected(emissionMenu.isSelected());
        });

        JMenuItem copyRight = createMenuItem("Copy center to right", e -> imageController.workingImageAsFiltered());
        JMenuItem copyLeft = createMenuItem("Copy right to center", e -> imageController.filteredImageAsWorking());

        dithering.add(ordered);
        dithering.add(fsDithering);

        volumeRendering.add(applyVR);
        volumeRendering.add(openConfig);
        volumeRendering.add(absorptionMenu);
        volumeRendering.add(emissionMenu);

        edges.add(roberts);
        edges.add(sobel);

        filtersMenu.add(copyLeft);
        filtersMenu.add(copyRight);
        filtersMenu.add(gray);
        filtersMenu.add(invert);
        filtersMenu.add(zoom);
        filtersMenu.add(blur);
        filtersMenu.add(sharp);
        filtersMenu.add(emboss);
        filtersMenu.add(gamma);
        filtersMenu.add(rotation);
        filtersMenu.add(watercolor);
        filtersMenu.add(dithering);
        filtersMenu.add(edges);
        filtersMenu.add(volumeRendering);

        JMenu about = new JMenu("About");
        about.addActionListener(e -> aboutWindow.show());

        menuBar.add(fileMenu);
        menuBar.add(filtersMenu);
        menuBar.add(about);

        return menuBar;
    }

    @Override
    public void setFilteredImage(BufferedImage image) {
        imagesHolder.setFilteredImage(image);
    }

    @Override
    public void setWorkingImage(BufferedImage image) {
        imagesHolder.setWorkingImage(image);
    }

    @Override
    public void setSourceImage(BufferedImage image) {
        imagesHolder.setSourceImage(image);
    }

    @Override
    public void removeAllImages() {
        imagesHolder.removeAllImages();
    }

    @Override
    public void setOneGraphic(double[] graphic) {
        graphicsHolder.drawOneGraphic(graphic);
    }

    @Override
    public void setManyGraphics(double[][] graphics, int width, int height) {
        graphicsHolder.drawManyGraphic(graphics, width, height);
    }

    @Override
    public void errorOccurred(String message) {
        new ErrorShowingWindow(message).show();
    }

    private JButton createButton(String relativePath, String tip, ActionListener actionListener) {
        JButton button = new JButton();
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
