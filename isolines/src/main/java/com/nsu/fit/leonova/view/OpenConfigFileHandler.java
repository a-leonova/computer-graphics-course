package com.nsu.fit.leonova.view;

import com.nsu.fit.leonova.controller.FileController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class OpenConfigFileHandler {
    private FileController fileManager;

    public OpenConfigFileHandler(FileController fileManager) {
        this.fileManager = fileManager;
    }

    public void openConfig(){
        JFileChooser jFileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        jFileChooser.setCurrentDirectory(workingDirectory);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        jFileChooser.setFileFilter(filter);
        if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            fileManager.openFile(file);
        }
    }
}
