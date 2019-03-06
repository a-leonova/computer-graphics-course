package com.nsu.fit.leonova.view;

import com.nsu.fit.leonova.globals.Globals;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImagesHolder extends JPanel implements MouseMotionListener, MouseListener {

    private boolean selected = true;
    private ImageManager sourceImage = new ImageManager();
    private ImageManager chosenArea = new ImageManager();
    private ImageManager filteredArea = new ImageManager();

    public ImagesHolder() throws IOException {
        setLayout(new FlowLayout(FlowLayout.LEFT, Globals.BORDER, Globals.BORDER));

        BufferedImage image = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("Picture.bmp")));
        sourceImage.setImage(image);
        add(sourceImage);
        add(chosenArea);
        add(filteredArea);

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void setSelected(boolean selected) {
        if(!selected){
            sourceImage.removeSelectedArea();
        }
        this.selected = selected;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println(e.getX() + " " + e.getY());
        if(selected && checkPositionInSourceArea(e.getPoint())){
            sourceImage.mouseDragged(e.getPoint());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(selected && checkPositionInSourceArea(e.getPoint())){
            sourceImage.mousePressed(e.getPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private boolean checkPositionInSourceArea(Point position){
        int x = sourceImage.getX() + sourceImage.getInsideImageWidth() - Globals.SELECTED_AREA_SIZE/2;
        int y = sourceImage.getY() + sourceImage.getInsideImageHeight() - Globals.SELECTED_AREA_SIZE/2;
        Point rightBottomCorner = new Point(x, y);
        Point leftTopCorner = new Point(sourceImage.getX() + Globals.SELECTED_AREA_SIZE/2, sourceImage.getY()+ Globals.SELECTED_AREA_SIZE/2);
        if(position.x < leftTopCorner.x || position.y < leftTopCorner.y || position.x > rightBottomCorner.x || position.y > rightBottomCorner.y){
            System.out.println("OUT");
            return false;
        }
        System.out.println("IN");
        return true;
        //return false;
    }
}
