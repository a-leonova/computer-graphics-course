package com.nsu.fit.leonova.view.windows.worldWindow;

import com.nsu.fit.leonova.controller.WorldController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class SaveFileHandler {
    private WorldController fileManager;

    public SaveFileHandler(WorldController fileManager) {
        this.fileManager = fileManager;
    }

    public void saveFile(){
        JFileChooser jFileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        jFileChooser.setFileFilter(filter);
        if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            fileManager.saveButtonPressed(file);
        }
    }
}
