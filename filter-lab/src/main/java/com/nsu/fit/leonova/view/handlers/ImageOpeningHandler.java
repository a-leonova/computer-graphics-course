package com.nsu.fit.leonova.view.handlers;

import com.nsu.fit.leonova.controller.FileManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class ImageOpeningHandler {
    private FileManager fileManager;

    public ImageOpeningHandler(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void openImage(){
        JFileChooser jFileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        jFileChooser.setCurrentDirectory(workingDirectory);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("IMAGES FILES", "bmp", "png");
        jFileChooser.setFileFilter(filter);
        if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            fileManager.openImage(file);
        }
    }
}
