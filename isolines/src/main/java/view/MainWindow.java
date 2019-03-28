package view;

import controller.LogicController;
import observers.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainWindow extends JFrame implements Observer {

    private ImageManager imageManager;
    private ImageManager legend = new ImageManager(10, 500);
    private LogicController logicController;

    public MainWindow(int width, int height){
        super("Isolines");
        imageManager = new ImageManager(width, height);
        add(imageManager, BorderLayout.CENTER);
        add(legend, BorderLayout.EAST);
        //pack();
    }

    public void setLogicController(LogicController logicController) {
        this.logicController = logicController;
//        logicController.createGraphic();
//        logicController.createLegend();
    }

    @Override
    public void setImage(BufferedImage image) {
        imageManager.setImage(image);
    }

    @Override
    public void setLegend(BufferedImage legendImg) {
        legend.setImage(legendImg);
    }
}
