package com.nsu.fit.leonova.view.handlers;

import com.nsu.fit.leonova.controller.FileManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class SaveImageHandler {
    private FileManager fileManager;

    public SaveImageHandler(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void saveFile(){
        JFileChooser jFileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("IMAGES FILES", "bmp", "png");
        jFileChooser.setFileFilter(filter);
        if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            fileManager.saveImage(file);
        }
    }
}
