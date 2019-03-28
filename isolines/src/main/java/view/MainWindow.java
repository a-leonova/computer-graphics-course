package view;

import observers.Observer;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class MainWindow extends JFrame implements Observer {

    private ImageManager imageManager;

    public MainWindow(int width, int height){
        super("Isolines");
        imageManager = new ImageManager(width, height);
    }

    @Override
    public void setImage(BufferedImage image) {
        imageManager.setImage(image);
    }
}
