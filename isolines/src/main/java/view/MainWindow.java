package view;

import controller.LogicController;
import observers.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainWindow extends JFrame implements Observer {

    private ImageManager imageManager;
    private LogicController logicController;

    public MainWindow(int width, int height){
        super("Isolines");
        imageManager = new ImageManager(width, height);
        add(imageManager, BorderLayout.CENTER);
        pack();
    }

    public void setLogicController(LogicController logicController) {
        this.logicController = logicController;
    }

    @Override
    public void setImage(BufferedImage image) {
        imageManager.setImage(image);
    }
}
