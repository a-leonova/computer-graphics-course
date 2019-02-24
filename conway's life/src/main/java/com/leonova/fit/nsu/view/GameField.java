package com.leonova.fit.nsu.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameField extends JPanel {

    private GraphicsOptions options;
    private CellsManager cellsManager;
    private BufferedImage img;

    public GameField(GraphicsOptions options) {
        this.options = options;
        cellsManager = new CellsManager(options);

        int width = 2 * ((int)Math.round(options.getCellEdge() * Math.cos(Math.PI/6)) + options.getLineWidth()) * options.getCellsInRow();
        int height = 2 * (options.getCellEdge() + options.getLineWidth()) * options.getCellsInColumn();
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        setPreferredSize(new Dimension(width , height));
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
    }

    public void setOptions(GraphicsOptions options) {
        this.options = options;
        cellsManager.setOptions(options);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        createImageField();
        g.drawImage(img, 0,0,this);
    }


    private void createImageField() {
        Graphics2D g = img.createGraphics();
        g.setPaint (Color.WHITE);
        g.fillRect(0,0,img.getWidth(),img.getHeight());
        g.setPaint(Color.BLACK);

        cellsManager.drawGrid(g);
    }


}
