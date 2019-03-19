package com.nsu.fit.leonova.view;

import com.nsu.fit.leonova.controller.FileManager;
import com.nsu.fit.leonova.controller.ImageController;
import com.nsu.fit.leonova.model.FiltersType;
import com.nsu.fit.leonova.observer.Observer;
import com.nsu.fit.leonova.view.parametersWindow.FSDitheringParamsWindow;
import com.nsu.fit.leonova.view.parametersWindow.OrderDitheringParamsWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainWindow extends JFrame implements Observer {
    private ImagesHolder imagesHolder = new ImagesHolder();
    private FileManager fileManager;
    private ImageController imageController;

    private OrderDitheringParamsWindow orderDitheringParamsWindow = new OrderDitheringParamsWindow();
    private FSDitheringParamsWindow fsDitheringParamsWindow = new FSDitheringParamsWindow();

    public MainWindow() throws IOException {
        super("Minimal photoshop");

        JToolBar toolBar = createToolBar();
        add(toolBar);
        getContentPane().add(toolBar, BorderLayout.PAGE_START);
        getContentPane().add(imagesHolder, BorderLayout.CENTER);

        setSize(1000, 700);
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
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();

        JButton newFileButton = new JButton();
        newFileButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-new-file-16.png"))));
        newFileButton.setToolTipText("New");

        JButton openFileButton = new JButton();
        openFileButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-open-folder-16.png"))));
        openFileButton.setToolTipText("Open image");
        openFileButton.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                fileManager.openImage(file);
            }
        });

        JButton saveButton = new JButton();
        saveButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-save-16.png"))));
        saveButton.setToolTipText("Save image");

        JButton desaturate = new JButton();
        desaturate.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/greyGradation.png"))));
        desaturate.setToolTipText("Desaturation");
        desaturate.addActionListener(e -> imageController.filterImage(FiltersType.DESATURATION, null));

        JButton invert = new JButton();
        invert.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/negative.png"))));
        invert.setToolTipText("Invert");
        invert.addActionListener(e -> imageController.filterImage(FiltersType.INVERT, null));

        JButton orderedDithering = new JButton();
        orderedDithering.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/orderedDithering.png"))));
        orderedDithering.setToolTipText("Ordered dithering");
        orderedDithering.addActionListener(e -> orderDitheringParamsWindow.setVisible(true));

        JButton fsDithering = new JButton();
        fsDithering.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/floydSteinberg.png"))));
        fsDithering.setToolTipText("Floyd-Stainberg dithering");
        fsDithering.addActionListener(e -> fsDitheringParamsWindow.setVisible(true));

        JButton zoom = new JButton();
        zoom.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/x2.png"))));
        zoom.setToolTipText("Zoom x2");
        zoom.addActionListener(e -> imageController.filterImage(FiltersType.ZOOM, null));

        JButton roberts = new JButton();
        roberts.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/roberts.png"))));
        roberts.setToolTipText("Find edges (Roberts)");
        //TODO: window
        roberts.addActionListener(e -> imageController.filterImage(FiltersType.ROBERTS, null));

        JButton sobel = new JButton();
        sobel.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/sobel.png"))));
        sobel.setToolTipText("Find edges (Sobel)");
        //TODO: window
        sobel.addActionListener(e -> imageController.filterImage(FiltersType.SOBEL, null));

        JButton blur = new JButton();
        blur.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/blur.png"))));
        blur.setToolTipText("Blur");
        blur.addActionListener(e -> imageController.filterImage(FiltersType.BLUR, null));

        JButton sharp = new JButton();
        sharp.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-triangle-16.png"))));
        sharp.setToolTipText("Sharp");
        sharp.addActionListener(e -> imageController.filterImage(FiltersType.SHARPEN, null));

        JButton emboss = new JButton();
        emboss.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/embossed.png"))));
        emboss.setToolTipText("Emboss");
        emboss.addActionListener(e -> imageController.filterImage(FiltersType.EMBOSS, null));

        JButton waterColor = new JButton();
        waterColor.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/aqua.png"))));
        waterColor.setToolTipText("Watercolor");
        waterColor.addActionListener(e -> imageController.filterImage(FiltersType.WATERCOLOR, null));

        JButton gamma = new JButton();
        gamma.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/gamma.png"))));
        gamma.setToolTipText("Gamma");
        //TODO: window
        gamma.addActionListener(e -> imageController.filterImage(FiltersType.GAMMA, null));

        JButton right = new JButton();
        right.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/right.png"))));
        right.setToolTipText("Copy right");

        JButton left = new JButton();
        left.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/left.png"))));
        left.setToolTipText("Copy left");
        left.addActionListener(e -> imageController.filteredImageAsWorking());

        JButton select = new JButton();
        select.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/select.png"))));
        select.setToolTipText("Select");


        JButton rotation = new JButton();
        rotation.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/rotation.png"))));
        rotation.setToolTipText("Rotation");
        //TODO: window
        rotation.addActionListener(e -> imageController.filterImage(FiltersType.ROTATION, null));

        JButton openConfig = new JButton();
        openConfig.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/configFile.png"))));
        openConfig.setToolTipText("Open configuration");
        openConfig.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser();
            if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                fileManager.openConfigFile(file);
            }
        });

        JToggleButton absorption = new JToggleButton();
        absorption.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/absorption.png"))));
        absorption.setToolTipText("Absorption");
        absorption.addActionListener(e -> imageController.absorptionWasPressed());

        JToggleButton emission = new JToggleButton();
        emission.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/emission.png"))));
        emission.setToolTipText("Emission");
        emission.addActionListener(e -> imageController.emissionWasPressed());

        JButton applyVR = new JButton();
        applyVR.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-play-16.png"))));
        applyVR.setToolTipText("Apply volume rendering");
        //TODO: window
        applyVR.addActionListener(e -> imageController.filterImage(FiltersType.VOLUME_RENDERING, null));


        JButton aboutButton = new JButton();
        aboutButton.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-question-mark-in-a-chat-bubble-16.png"))));
        aboutButton.setToolTipText("About");

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

}
