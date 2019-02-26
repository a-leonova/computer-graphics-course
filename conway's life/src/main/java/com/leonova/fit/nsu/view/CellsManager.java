package com.leonova.fit.nsu.view;

import com.leonova.fit.nsu.model.Position;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

public class CellsManager {

    private GraphicsOptions options;
    private int edgeWithHalfBorder;
    private int insideRadiusWithHalfBorder;
    private int shiftYWithHalfBorder;
    private int widthInPixels;
    private int heightInPixels;

    public CellsManager(GraphicsOptions options) {
        this.options = options;
        recountParams();
    }

    public void setOptions(GraphicsOptions options) {
        this.options = options;
        recountParams();
    }

    private void recountParams(){
        edgeWithHalfBorder = options.getCellEdge() + (int)Math.round(options.getLineWidth() * 0.5);
        insideRadiusWithHalfBorder = (int) Math.round((options.getCellEdge() + (int)Math.round(options.getLineWidth() * 0.5)) * Math.cos(Math.PI / 6));
        shiftYWithHalfBorder = (int) Math.round(edgeWithHalfBorder * Math.sin(Math.PI / 6));
        widthInPixels = 2 * ((int)Math.round(options.getCellEdge() * Math.cos(Math.PI/6)) + options.getLineWidth()) * options.getCellsInRow();
        heightInPixels = 2 * (options.getCellEdge() + options.getLineWidth()) * options.getCellsInColumn();
    }

    public void fillCell(Position startPosition, Color oldColor, Color newColor, BufferedImage image) {
        Queue<Position> pixelsToDraw = new LinkedList<>();
        Color pixelColor = new Color(image.getRGB(startPosition.getX(), startPosition.getY()));
        if(!pixelColor.equals(oldColor)){
            return;
        }
        Graphics2D g = image.createGraphics();
        g.setPaint(newColor);
        pixelsToDraw.add(startPosition);
        while(!pixelsToDraw.isEmpty()){
            Position position = pixelsToDraw.poll();
            pixelColor = new Color(image.getRGB(position.getX(), position.getY()));
            if(pixelColor.equals(oldColor)){
                Position west = new Position(position);
                Position east = new Position(position);

                while(isInside(west) && (new Color(image.getRGB(west.getX(), west.getY())).equals(oldColor))){
                    west.setX(west.getX() + 1);
                }
                while(isInside(east) && (new Color(image.getRGB(east.getX(), east.getY())).equals(oldColor))){
                    east.setX(east.getX() - 1);
                }

                for(int i = east.getX() + 1; i < west.getX(); ++i){
                    g.drawLine(i, west.getY(), i, west.getY());

                    Position northPosition = new Position(i, west.getY() + 1);
                    Position southPosition = new Position(i, west.getY() - 1);

                    if(isInside(northPosition) && (new Color(image.getRGB(northPosition.getX(), northPosition.getY())).equals(oldColor))){
                        pixelsToDraw.add(northPosition);
                    }

                    if(isInside(southPosition) && (new Color(image.getRGB(southPosition.getX(), southPosition.getY())).equals(oldColor))){
                        pixelsToDraw.add(southPosition);
                    }
                }
            }
        }
    }

    //TODO: check cells border!!!
    public Position getSelectedCell(Position position, int border){

        Position relativePosition = new Position(position.getX() - border - Math.round(options.getLineWidth()/2.0f), position.getY() - border);

        //double gridWidth = 2 * (options.getLineWidth() + (int) Math.round(options.getCellEdge() * Math.cos(Math.PI / 6)));
        int insideDiameter = (int)Math.round(options.getCellEdge() * Math.cos(Math.PI/6) * 2);
        double gridWidth = insideDiameter + options.getLineWidth();
        double halfWidth = gridWidth / 2;
        double gridHeight = Math.round(1.5 * options.getCellEdge()) + options.getLineWidth();

        int row = (int)(relativePosition.getY() / gridHeight);
        int column;

        boolean rowIsOdd = row % 2 == 1;
        // Is the row an odd number?

        // Yes: Offset x to match the indent of the row
        if (rowIsOdd){
            column = (int) ((relativePosition.getX() - halfWidth) / gridWidth);
        }
        // No: Calculate normally
        else{
            column = (int) (relativePosition.getX() / gridWidth);
        }

        double relY = relativePosition.getY() - (row * gridHeight);
        double relX;

        if (rowIsOdd){
            relX = (relativePosition.getX() - (column * gridWidth)) - halfWidth;
        }
        else {
            relX = relativePosition.getX() - (column * gridWidth);
        }

        double c = options.getCellEdge() - options.getCellEdge() * Math.sin(Math.PI/6);
        double m = c / (int) Math.round(options.getCellEdge() * Math.cos(Math.PI / 6));

        // LEFT edgeWithHalfBorder
        if (relY < (-m * relX) + c){
            row--;
            if (!rowIsOdd)
                column--;
        }
        // RIGHT edgeWithHalfBorder
        else if (relY < (m * relX) - c){
            row--;
            if (rowIsOdd)
                column++;
        }

        return new Position(row, column);
    }

    public void drawGrid(int shiftFromBorder, Graphics2D graphics){

        int x0 = shiftFromBorder + options.getLineWidth() + (int) Math.round(options.getCellEdge() * Math.cos(Math.PI / 6));
        int y0 = shiftFromBorder + options.getCellEdge() + options.getLineWidth();
        int insideDiameter = (int)Math.round(options.getCellEdge() * Math.cos(Math.PI/6) * 2);
        int insideRadius = (int) Math.round(options.getCellEdge() * Math.cos(Math.PI / 6));
        int startX;

        graphics.setStroke(new BasicStroke(options.getLineWidth() - 1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for(int h = 0; h < options.getCellsInRow(); ++h){
            if(h % 2 == 1){
                startX = (int) (x0 + insideRadius + Math.round(options.getLineWidth()/2.0));
            }
            else{
                startX = x0;
            }
            for(int w = 0; w < options.getCellsInColumn() - (h % 2 == 0 ? 0 : 1); ++w){
                int currentX = startX + (insideDiameter + options.getLineWidth()) * w;
                // int currentX = startX + 2 * w * insideRadiusWithHalfBorder;
                int currentY = (y0 + (int)Math.ceil((1.5 * options.getCellEdge() + options.getLineWidth())) * h);
                drawCell(new Position(currentX, currentY), graphics);
            }
        }
    }

    private void drawCell(Position center, Graphics2D graphics){
        drawLine(center.getX(), (center.getY() + edgeWithHalfBorder), (center.getX() + insideRadiusWithHalfBorder), (center.getY() + shiftYWithHalfBorder), graphics);
        drawLine((center.getX() + insideRadiusWithHalfBorder), (center.getY() + shiftYWithHalfBorder), (center.getX() + insideRadiusWithHalfBorder), (center.getY() - shiftYWithHalfBorder), graphics);
        drawLine((center.getX() + insideRadiusWithHalfBorder), (center.getY() - shiftYWithHalfBorder), center.getX(), (center.getY() - edgeWithHalfBorder), graphics);
        drawLine(center.getX(), (center.getY() - edgeWithHalfBorder), (center.getX() - insideRadiusWithHalfBorder), (center.getY() - shiftYWithHalfBorder), graphics);
        drawLine((center.getX() - insideRadiusWithHalfBorder), (center.getY() - shiftYWithHalfBorder), (center.getX() - insideRadiusWithHalfBorder), (center.getY() + shiftYWithHalfBorder), graphics);
        drawLine((center.getX() - insideRadiusWithHalfBorder), (center.getY() + shiftYWithHalfBorder), center.getX(), center.getY() + edgeWithHalfBorder, graphics);
    }

    private void drawLine(int x0, int y0, int x1, int y1, Graphics2D g){

        if(options.getLineWidth() == 1){
            g.drawLine(x0, y0, x1,y1);
            return;
        }

        boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
        if(steep){
            int t = y0;
            y0 = x0;
            x0 = t;

            t = x1;
            x1 = y1;
            y1 = t;
        }

        if(x0 > x1){
            int t = y0;
            y0 = y1;
            y1 = t;

            t = x1;
            x1 = x0;
            x0 = t;
        }

        int dx = x1 - x0;
        int dy = Math.abs(y1 - y0);
        int error = dx / 2;

        int ystep = (y0 < y1) ? 1 : -1;
        int y = y0;
        for (int x = x0; x <= x1; x++) {
            int drawX = steep ? y : x;
            int drawY = steep ? x : y;
            g.drawLine(drawX, drawY, drawX, drawY);
            error -= dy;
            if (error < 0) {
                y += ystep;
                error += dx;
            }
        }
    }

    private boolean isInside(Position position){
        return position.getX() >= 0 && position.getX() < widthInPixels && position.getY() >= 0 && position.getY() < heightInPixels;
    }
}
