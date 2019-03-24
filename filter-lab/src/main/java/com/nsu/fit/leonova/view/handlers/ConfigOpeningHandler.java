package com.nsu.fit.leonova.view.handlers;

import com.nsu.fit.leonova.controller.FileManager;
import com.nsu.fit.leonova.controller.ImageController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class ConfigOpeningHandler {

    private FileManager fileManager;

    public ConfigOpeningHandler(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void openConfig(){
        JFileChooser jFileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        jFileChooser.setFileFilter(filter);
        if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            fileManager.openConfigFile(file);
        }
    }
}
