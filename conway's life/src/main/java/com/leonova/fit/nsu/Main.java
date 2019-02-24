package com.leonova.fit.nsu;

import com.leonova.fit.nsu.view.GraphicsOptions;
import com.leonova.fit.nsu.view.WindowView;

public class Main {


    public static void main(String[] args) {
        GraphicsOptions graphicsOptions = new GraphicsOptions(20, 20, 5, 20);
        WindowView view = new WindowView(graphicsOptions);
        view.setVisible(true);
    }
}
