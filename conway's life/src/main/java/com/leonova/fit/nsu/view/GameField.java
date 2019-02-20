package com.leonova.fit.nsu.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameField extends JPanel {

    private int width;
    private int height;
    private int edge;

    public GameField(int width, int height, int edge){
        super();
        this.edge = edge;
        this.height = height;
        this.width = width;
    }

    private BufferedImage img = new BufferedImage(500,500, BufferedImage.TYPE_INT_BGR);

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        createImageField();
        g.drawImage(img, 0,0,this);
    }


    private void createImageField() {
        Graphics2D g = img.createGraphics();
        g.setPaint (Color.WHITE);
        g.fillRect(0,0,500,500);
        g.setPaint(Color.BLACK);



//        HexagonalGridCreator creator = new HexagonalGridCreator();
//        creator.draw(20,20, g);
//        CellDrawer drawer = new CellDrawer(Globals.WIDTH, Globals.HEIGHT, Globals.EDGE);
//        try {
//            drawer.fillCell(new Position(60, 15), new Color(img.getRGB(30,15)), Color.BLUE, img);
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
    }


}
