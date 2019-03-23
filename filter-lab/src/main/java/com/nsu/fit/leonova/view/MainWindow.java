package com.nsu.fit.leonova.view;

import com.nsu.fit.leonova.controller.FileManager;
import com.nsu.fit.leonova.controller.ImageController;
import com.nsu.fit.leonova.model.FiltersType;
import com.nsu.fit.leonova.observer.Observer;
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

    public MainWindow() throws IOException {
        super("Minimal photoshop");

        JToolBar toolBar = createToolBar();
        add(toolBar);
        getContentPane().add(toolBar, BorderLayout.PAGE_START);
        getContentPane().add(imagesHolder, BorderLayout.CENTER);
        //getContentPane().add(graphicsHolder, BorderLayout.CENTER);
        getContentPane().add(graphicsHolder, BorderLayout.SOUTH);
        //getContentPane().add(new JLabel("!!!!!!!!!!!1"), BorderLayout.SOUTH);

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

        JButton newFileButton = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-new-file-16.png"))),
                "New", e -> {
                    imagesHolder.removeAllImages();
                    select.setSelected(false);
                    imagesHolder.setSelected(false);
        });

        JButton openFileButton = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-open-folder-16.png"))),
                "Open image", e -> {
                    JFileChooser jFileChooser = new JFileChooser();
                    if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        File file = jFileChooser.getSelectedFile();
                        fileManager.openImage(file);
                    }
                    select.setSelected(false);
                    imagesHolder.setSelected(false);
        });

        JButton saveButton = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-save-16.png"))),
                "Save image", e ->{
                    JFileChooser jFileChooser = new JFileChooser();
                    if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        File file = jFileChooser.getSelectedFile();
                        fileManager.saveImage(file);
                    }
                });

        JButton desaturate = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/greyGradation.png"))),
                "Desaturation", e -> imageController.filterImage(FiltersType.DESATURATION, null));

        JButton invert = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/negative.png"))),
                "Invert", e -> imageController.filterImage(FiltersType.INVERT, null));

        JButton orderedDithering = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/orderedDithering.png"))),
                "Ordered dithering", e -> orderDitheringParamsWindow.setVisible(true));

        JButton fsDithering = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/floydSteinberg.png"))),
                "Floyd-Stainberg dithering", e -> fsDitheringParamsWindow.setVisible(true));

        JButton zoom = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/x2.png"))),
                "Zoom x2", e -> imageController.filterImage(FiltersType.ZOOM, null));

        JButton roberts = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/roberts.png"))),
                "Find edges (Roberts)", e ->{
                    edgeFilterParamsWindow.setType(FiltersType.ROBERTS);
                    edgeFilterParamsWindow.setVisible(true);
                });

        JButton sobel = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/sobel.png"))),
                "Find edges (Sobel)", e -> {
                    edgeFilterParamsWindow.setType(FiltersType.SOBEL);
                    edgeFilterParamsWindow.setVisible(true);
                });

        JButton blur = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/blur.png"))),
                "Blur", e -> imageController.filterImage(FiltersType.BLUR, null));

        JButton sharp = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-triangle-16.png"))),
                "Sharp", e -> imageController.filterImage(FiltersType.SHARPEN, null));

        JButton emboss = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/embossed.png"))),
                "Emboss", e -> imageController.filterImage(FiltersType.EMBOSS, null));

        JButton waterColor = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/aqua.png"))),
                "Watercolor", e -> imageController.filterImage(FiltersType.WATERCOLOR, null));

        JButton gamma = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/gamma.png"))),
                "Gamma", e -> gammaParamsWindow.setVisible(true));

        JButton right = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/right.png"))),
                "Copy right", e -> imageController.workingImageAsFiltered());

        JButton left = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/left.png"))),
                "Copy left", e -> imageController.filteredImageAsWorking());

        select = new JToggleButton();
        select.setIcon(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/select.png"))));
        select.setToolTipText("Select");
        select.addActionListener(e -> imagesHolder.setSelected(select.isSelected()));

        JButton rotation = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/rotation.png"))),
                "Rotation", e -> rotationParamsWindow.setVisible(true));

        JButton openConfig = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/configFile.png"))),
                "Open configuration", e -> {
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

        JButton applyVR = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-play-16.png"))),
                "Apply volume rendering",
                e -> volumeRenderingParamsWindow.setVisible(true));
        JButton aboutButton = createButton(new ImageIcon(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("icons/icons8-question-mark-in-a-chat-bubble-16.png"))), "About", e ->aboutWindow.show());

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

    private JButton createButton(ImageIcon icon, String tip, ActionListener actionListener){
        JButton button = new JButton();
        button.setIcon(icon);
        button.setToolTipText(tip);
        button.addActionListener(actionListener);
        return button;
    }

}
