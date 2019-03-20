package com.nsu.fit.leonova.controller;

import java.io.File;

public interface FileManager {
    void openImage(File file);
    void openConfigFile(File file);
    void saveImage(File file);
}
