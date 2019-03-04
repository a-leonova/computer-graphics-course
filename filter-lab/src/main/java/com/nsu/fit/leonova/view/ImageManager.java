package com.nsu.fit.leonova.view;

import com.nsu.fit.leonova.globals.Globals;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageManager extends JPanel {

    BufferedImage bufferedImage;

    public ImageManager() {
        bufferedImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_BGR);
        setPreferredSize(new Dimension(bufferedImage.getWidth() , bufferedImage.getHeight()));
        drawDashedLine();

        //g2.setStroke(oldStroke);
       // repaint();
        //paint(g2);
         //g2.dispose();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(bufferedImage, 0,0,this);
    }

    public void setImage(BufferedImage image){
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.clearRect(10,10,360,360);
        Image resizedImage = resizeImage(image);
        graphics2D.drawImage(resizedImage,10, 10, resizedImage.getWidth(null), resizedImage.getHeight(null), null);
        drawDashedLine();
        graphics2D.dispose();

    }

    private void drawDashedLine(){
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.setPaint(Color.WHITE);
        Stroke oldStroke = g2.getStroke();
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
        g2.setStroke(dashed);
        g2.drawLine(10, 10, 360, 10);
        g2.drawLine(360, 10, 360, 360);
        g2.drawLine(360, 360, 10, 360);
        g2.drawLine(10, 360, 10, 10);
        g2.dispose();
    }


    private Image resizeImage(BufferedImage image){
        int width;
        int height;
        if(image.getWidth() > image.getHeight()){
            width = Globals.WIDTH;
            double d = image.getWidth() / (double) width;
            height = (int)Math.round(image.getHeight() / d);
        }
        else {
            height = Globals.HEIGHT;
            double d = image.getHeight() / (double) height;
            width = (int)Math.round(image.getWidth() / d);
        }
        Image newImage = image.getScaledInstance(width,height, Image.SCALE_FAST);
        return newImage;
    }

}
