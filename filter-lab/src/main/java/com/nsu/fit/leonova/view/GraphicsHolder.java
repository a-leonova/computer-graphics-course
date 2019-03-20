package com.nsu.fit.leonova.view;

import com.nsu.fit.leonova.globals.GlobalsImage;

import javax.swing.*;
import java.awt.*;

public class GraphicsHolder extends JPanel {

    private GraphicManager absorption = new GraphicManager(105, 100, 100);
    private GraphicManager emission = new GraphicManager(105, 100, 100);

    public GraphicsHolder(){
        setLayout(new FlowLayout(FlowLayout.LEFT, GlobalsImage.BORDER, GlobalsImage.BORDER));
        add(absorption);
        add(emission);
    }

    public void drawManyGraphic(double[][] emissions, int width, int height){
        emission.drawManyGraphic(emissions, width, height);
    }

    public void drawOneGraphic(double[] graphic){
        absorption.drawOneGraphic(graphic, Color.RED);
    }

}
