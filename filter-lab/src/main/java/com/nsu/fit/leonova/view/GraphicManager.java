package com.nsu.fit.leonova.view;

import com.nsu.fit.leonova.globals.GlobalsGraphics;
import com.nsu.fit.leonova.globals.GlobalsImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphicManager extends JPanel {
    private BufferedImage bufferedImage;
    private int k;

    public GraphicManager(int width, int height, int k) {
        this.k = k;
        bufferedImage = new BufferedImage(width+ GlobalsImage.DASH_BORDER_WIDTH , height+ GlobalsImage.DASH_BORDER_WIDTH, BufferedImage.TYPE_INT_ARGB);
        setPreferredSize(new Dimension(bufferedImage.getWidth() , bufferedImage.getHeight()));
        drawDashedRect(0,0, width, height, 5, Color.RED, bufferedImage.createGraphics());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(bufferedImage, 0, 0, this);
    }

    public void drawOneGraphic(double[] graphic, Color color){
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setPaint(Color.WHITE);
        graphics2D.clearRect(0,0,bufferedImage.getWidth(),bufferedImage.getHeight());

        drawGraphic(graphic, graphics2D, color);

        drawDashedRect(0,0, bufferedImage.getWidth(), bufferedImage.getHeight(), 5, Color.BLUE, bufferedImage.createGraphics());
        graphics2D.dispose();
        repaint();
    }

    public void drawManyGraphic(double[][] emissions, int width, int height){
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setPaint(Color.WHITE);
        graphics2D.clearRect(0,0,bufferedImage.getWidth(),bufferedImage.getHeight());

        Color color;

        for(int j = 0; j < width; ++j){
            if(j == 0){
                color = Color.RED;
            }
            else if (j == 1){
                color = Color.GREEN;
            }
            else {
                color = Color.BLUE;
            }
            drawGraphic(emissions[j], graphics2D, color);
//            int startX = 0;
//            double startY = emissions[startX][j];
//            for(int i = 0; i < width; ++i){
//                double newY = emissions[i][j];
//                graphics2D.drawLine(startX + j, (int)Math.round(startY * k), i, (int)Math.round(newY * k));
//                startY = newY;
//                startX = i;
//            }
        }
        drawDashedRect(0,0, GlobalsImage.WIDTH, GlobalsImage.HEIGHT, 5, Color.BLUE, bufferedImage.createGraphics());
        graphics2D.dispose();
        repaint();
    }

    private void drawGraphic(double[] graphic, Graphics2D graphics2D, Color color){
        int startX = 0;
        double startY = graphic[startX] * k;
        graphics2D.setPaint(color);
        for(int i = 1 ; i < graphic.length; ++i){
            double newY = graphic[i] * k;
            graphics2D.drawLine(startX, bufferedImage.getHeight() - (int)Math.round(startY), i, bufferedImage.getHeight() - (int)Math.round(newY));
            startX = i;
            startY = newY;
        }
    }

    private void drawDashedRect(int x0, int y0, int width, int height, int freq, Color color, Graphics2D g2){
        g2.setPaint(color);
        Stroke dashed = new BasicStroke(GlobalsImage.DASH_BORDER_WIDTH, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{freq}, 0);
        g2.setStroke(dashed);
        g2.drawRect(x0,y0, width, height);
        g2.dispose();
    }
}
