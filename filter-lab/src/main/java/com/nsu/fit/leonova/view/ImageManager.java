package com.nsu.fit.leonova.view;

import com.nsu.fit.leonova.globals.Globals;
import jdk.nashorn.internal.objects.Global;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageManager extends JPanel {

    BufferedImage bufferedImage;

    public ImageManager() {
        bufferedImage = new BufferedImage(Globals.WIDTH + Globals.DASH_BORDER_WIDTH , Globals.HEIGHT+ Globals.DASH_BORDER_WIDTH, BufferedImage.TYPE_INT_BGR);
        Graphics2D graphics2D = bufferedImage.createGraphics();
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
        graphics2D.clearRect(0,0,360,360);
        Image resizedImage = resizeImage(image);
        graphics2D.drawImage(resizedImage,0, 0, resizedImage.getWidth(null), resizedImage.getHeight(null), null);
        drawDashedLine();
        graphics2D.dispose();

    }

    private void drawDashedLine(){
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.setPaint(Color.WHITE);
        Stroke dashed = new BasicStroke(Globals.DASH_BORDER_WIDTH, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
        g2.setStroke(dashed);
        g2.drawLine(0, 0, Globals.WIDTH, 0);
        g2.drawLine(Globals.WIDTH, 0 ,Globals.WIDTH, Globals.HEIGHT);
        g2.drawLine(Globals.WIDTH, Globals.HEIGHT, 0, Globals.HEIGHT);
        g2.drawLine(0, Globals.HEIGHT, 0, 0);
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
