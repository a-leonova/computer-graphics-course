package com.leonova.fit.nsu.controller;

import com.leonova.fit.nsu.view.GraphicsOptions;

import java.io.File;

public interface FileManager {
    void save(File file, GraphicsOptions graphicsOptions);
    void open(File file);
}
