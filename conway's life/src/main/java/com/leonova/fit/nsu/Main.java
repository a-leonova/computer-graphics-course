package com.leonova.fit.nsu;

import com.leonova.fit.nsu.model.GameOptions;
import com.leonova.fit.nsu.view.GraphiscOptions;
import com.leonova.fit.nsu.view.WindowView;

public class Main {


    public static void main(String[] args) {
        GraphiscOptions graphiscOptions = new GraphiscOptions(10, 10, 1, 10);
        WindowView view = new WindowView(graphiscOptions);
        view.setVisible(true);
    }
}
