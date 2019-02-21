package com.leonova.fit.nsu.view;

import com.leonova.fit.nsu.Utils;
import com.leonova.fit.nsu.draft.Globals;
import com.leonova.fit.nsu.model.Position;

import java.awt.*;

public class HexagonalGridDrawer {


    private GraphiscOptions options;

    public HexagonalGridDrawer(GraphiscOptions options) {
        this.options = options;
    }

    public void setOptions(GraphiscOptions options) {
        this.options = options;
    }

    public void draw(Position startPosition, Graphics2D graphics){
        double startX;
        double startY = startPosition.getY();
        double shiftX = Utils.insideRadius(options.getCellEdge());
        double shiftY = Utils.getLengthFromCenterYToRight(options.getCellEdge());

        for(int h = 0; h < Globals.HEIGHT; ++h){
            if(h % 2 == 1){
                startX = startPosition.getX() + shiftX;
            }
            else{
                startX = startPosition.getX();
            }
            for(int w = 0; w < Globals.WIDTH - (h % 2 == 0 ? 0 : 1); ++w){

                double currentX = startX + 2 * w * shiftX;
                double currentY = startY + 1.5 * h * Globals.EDGE;

                drawLine((int)currentX, (int)(currentY + Globals.EDGE), (int)(currentX + shiftX), (int)(currentY + shiftY), graphics);
                drawLine((int)(currentX + shiftX), (int)(currentY + shiftY), (int)(currentX + shiftX), (int)(currentY - shiftY), graphics);
                drawLine((int)(currentX + shiftX), (int)(currentY - shiftY), (int)currentX, (int)(currentY - Globals.EDGE), graphics);
                drawLine((int)currentX, (int)(currentY - Globals.EDGE), (int)(currentX - shiftX), (int)(currentY - shiftY), graphics);
                drawLine((int)(currentX - shiftX), (int)(currentY - shiftY), (int)(currentX - shiftX), (int)(currentY + shiftY), graphics);
                drawLine((int)(currentX - shiftX), (int)(currentY + shiftY), (int)currentX, (int)currentY + Globals.EDGE, graphics);

            }
        }
    }

    private void drawLine(int x0, int y0, int x1, int y1, Graphics g){

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
            int drawX =  steep ? y : x;
            int drawY = steep ? x : y;
            g.drawLine(drawX,drawY,drawX,drawY);
            error -= dy;
            if (error < 0)
            {
                y += ystep;
                error += dx;
            }
        }

    }

}
