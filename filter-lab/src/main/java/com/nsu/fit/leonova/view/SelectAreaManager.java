package com.nsu.fit.leonova.view;

import com.nsu.fit.leonova.globals.Globals;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SelectAreaManager extends JPanel {

    private static final int SIZE = 70;

    private BufferedImage bufferedImage;

    public SelectAreaManager() {
        bufferedImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
        for(int i = 0 ; i < bufferedImage.getWidth();++i){
            for(int j = 0; j < bufferedImage.getHeight(); ++j){
                bufferedImage.setRGB(i,j, new Color(0,0,0,255).getRGB());
            }
        }
        setPreferredSize(new Dimension(bufferedImage.getWidth() , bufferedImage.getHeight()));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(bufferedImage, 0,0,this);
    }

    public void removeEveryThing(){
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.clearRect(0,0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g2.dispose();
    }

    public void drawSelectedArea(Point position){
        removeEveryThing();
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.setPaint(Color.BLACK);
        Stroke dashed = new BasicStroke(SIZE, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3}, 0);
        g2.setStroke(dashed);

        int x0 = position.x - SIZE/2;
        int y0 = position.y - SIZE/2;

        g2.drawLine(x0, y0, x0 + SIZE, y0);
        g2.drawLine(x0 + SIZE, y0, x0 + SIZE, y0 + SIZE);
        g2.drawLine(x0 + SIZE, y0 + SIZE, x0, y0 + SIZE);
        g2.drawLine(x0, y0 + SIZE, x0, y0);
        g2.dispose();
    }
}
