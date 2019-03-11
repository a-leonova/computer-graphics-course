package com.nsu.fit.leonova.view;

import com.nsu.fit.leonova.globals.Globals;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageManager extends JPanel {

    protected Image insideImage = null;
    protected BufferedImage bufferedImage;

    public ImageManager() {
        bufferedImage = new BufferedImage(Globals.WIDTH + Globals.DASH_BORDER_WIDTH , Globals.HEIGHT+ Globals.DASH_BORDER_WIDTH, BufferedImage.TYPE_INT_ARGB);
        setPreferredSize(new Dimension(bufferedImage.getWidth() , bufferedImage.getHeight()));
        drawDashedRect(0,0, Globals.WIDTH, Globals.HEIGHT, 5, Color.BLUE, bufferedImage.createGraphics());
    }

    public void setImage(BufferedImage image) {
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.clearRect(0,0,360,360);
        insideImage = resizeImage(image);
        graphics2D.drawImage(insideImage,0, 0, insideImage.getWidth(null), insideImage.getHeight(null), null);
        drawDashedRect(0,0, Globals.WIDTH, Globals.HEIGHT, 5, Color.BLUE,bufferedImage.createGraphics());
        graphics2D.dispose();
        repaint();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(bufferedImage, 0, 0, this);
    }


    public int getInsideImageWidth(){

        if(insideImage != null){
            return insideImage.getWidth(null);
        }
        else{
            return 0;
        }
    }

    public int getInsideImageHeight() {
        if(insideImage != null){
            return insideImage.getHeight(null);
        }
        else {
            return 0;
        }
    }


    protected void drawDashedRect(int x0, int y0, int width, int height, int freq, Color color, Graphics2D g2){
        g2.setPaint(color);
        Stroke dashed = new BasicStroke(Globals.DASH_BORDER_WIDTH, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{freq}, 0);
        g2.setStroke(dashed);
        g2.drawRect(x0,y0, width, height);
        g2.dispose();
    }

    protected Image resizeImage(BufferedImage image){
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
