package com.nsu.fit.leonova.view;

import com.nsu.fit.leonova.globals.GlobalsImage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageManager extends JPanel {

    protected Image insideImage = null;
    protected BufferedImage bufferedImage;
    private int imageWidth;
    private int imageHeight;

    public ImageManager() {
        imageWidth = GlobalsImage.WIDTH + GlobalsImage.DASH_BORDER_WIDTH;
        imageHeight = GlobalsImage.HEIGHT + GlobalsImage.DASH_BORDER_WIDTH;
        bufferedImage = new BufferedImage(imageWidth + 1 , 1 + imageHeight, BufferedImage.TYPE_INT_ARGB);
        setPreferredSize(new Dimension(bufferedImage.getWidth() , bufferedImage.getHeight()));
        drawDashedRect(0,0, imageWidth, imageHeight, 5, GlobalsImage.DASH_BORDER_WIDTH,Color.BLUE, bufferedImage.createGraphics());
    }

    public void setImage(BufferedImage image) {
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setBackground(GlobalsImage.BACKGROUND_COLOR);
        graphics2D.clearRect(0,0,bufferedImage.getWidth(),bufferedImage.getHeight());
        if(image.getHeight() > GlobalsImage.HEIGHT || image.getWidth() > GlobalsImage.WIDTH){
            insideImage = resizeImage(image);
        }
        else {
            insideImage = image;
        }
        graphics2D.drawImage(insideImage,GlobalsImage.DASH_BORDER_WIDTH, GlobalsImage.DASH_BORDER_WIDTH, insideImage.getWidth(null), insideImage.getHeight(null), null);
        drawDashedRect(0,0, imageWidth, imageHeight, 5, GlobalsImage.DASH_BORDER_WIDTH, Color.BLUE,bufferedImage.createGraphics());
        graphics2D.dispose();
        repaint();
    }

    public void clearAll(){
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setBackground(GlobalsImage.BACKGROUND_COLOR);
        graphics2D.clearRect(0,0,bufferedImage.getWidth(),bufferedImage.getHeight());
        drawDashedRect(0,0, imageWidth, imageHeight, 5, GlobalsImage.DASH_BORDER_WIDTH,Color.BLUE,bufferedImage.createGraphics());
        graphics2D.dispose();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(bufferedImage, 0, 0, this);
    }

    protected void drawDashedRect(int x0, int y0, int width, int height, int freq, int borderSize ,Color color, Graphics2D g2){
        g2.setPaint(color);
        Stroke dashed = new BasicStroke(borderSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0, new float[]{freq}, 0);
        g2.setStroke(dashed);
        g2.drawRect(x0,y0, width, height);
        g2.dispose();
    }

    protected Image resizeImage(BufferedImage image){
        int width;
        int height;
        if(image.getWidth() > image.getHeight()){
            width = GlobalsImage.WIDTH;
            double d = image.getWidth() / (double) width;
            height = (int)Math.round(image.getHeight() / d);
        }
        else {
            height = GlobalsImage.HEIGHT;
            double d = image.getHeight() / (double) height;
            width = (int)Math.round(image.getWidth() / d);
        }
        return image.getScaledInstance(width,height, Image.SCALE_FAST);
    }
}
