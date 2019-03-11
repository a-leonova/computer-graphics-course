package com.nsu.fit.leonova;

import com.nsu.fit.leonova.controller.Controller;
import com.nsu.fit.leonova.model.ImageConsumer;
import com.nsu.fit.leonova.model.ImageConsumerImpl;
import com.nsu.fit.leonova.view.MainWindow;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Controller controller = new Controller();
        ImageConsumerImpl imageConsumer = new ImageConsumerImpl();
        MainWindow mainWindow = new MainWindow();
        controller.setImageConsumer(imageConsumer);
        imageConsumer.addObserver(mainWindow);
        mainWindow.setFileManager(controller);
        mainWindow.setImageController(controller);
    }
}
