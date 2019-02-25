package com.leonova.fit.nsu.view;

import com.leonova.fit.nsu.controller.GameController;
import com.leonova.fit.nsu.model.Cell;
import com.leonova.fit.nsu.model.Position;
import com.leonova.fit.nsu.globals.GlobalConsts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;

public class GameField extends JPanel {

    private GraphicsOptions options;
    private CellsManager cellsManager;
    private GameController gameController;
    private BufferedImage img;
    private int shiftFromBorder = 10;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public GameField(GraphicsOptions options) {
        this.options = options;
        cellsManager = new CellsManager(options);

        int width = 2 * ((int)Math.round(options.getCellEdge() * Math.cos(Math.PI/6)) + options.getLineWidth()) * options.getCellsInRow();
        int height = 2 * (options.getCellEdge() + options.getLineWidth()) * options.getCellsInColumn();
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);

        setPreferredSize(new Dimension(width , height));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(img.getRGB(e.getX(), e.getY()) != GlobalConsts.borderColor.getRGB()){
                    Position cellCoordinate = cellsManager.getSelectedCell(new Position(e.getX(), e.getY()));
                    gameController.pressCell(cellCoordinate);
                }
            }
        });
        createImageField();
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
    }

    public void setOptions(GraphicsOptions options) {
        this.options = options;
        cellsManager.setOptions(options);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(img, 0,0,this);
    }

    public void updateGraphicField(HashSet<Cell> changedCells){
        //TODO:implementation
        for(Cell cell : changedCells){
            Position pixels = modelPositionToPixelsPosition(cell.getPosition());
             Color oldColor = new Color(img.getRGB(pixels.getX(), pixels.getY()));
            if(cell.isAlive() && !oldColor.equals(GlobalConsts.aliveCell)){
                cellsManager.fillCell(pixels, oldColor, GlobalConsts.aliveCell, img);
            }
            else if (!cell.isAlive() && !oldColor.equals(GlobalConsts.deadCell)){
                cellsManager.fillCell(pixels, oldColor, GlobalConsts.deadCell, img);
            }
            if(options.isShowImpact()){
                paintImpact(pixels, String.valueOf(cell.getImpact()));
            }
        }
        repaint();
    }

    public void stopDisplayImpact(HashSet<Cell> cells) {
        options.setShowImpact(false);
        updateGraphicField(cells);
    }

    public void displayImpact(HashSet<Cell> cells) {
        options.setShowImpact(false);
        updateGraphicField(cells);
    }

    private void paintImpact(Position position, String impact){
        img.createGraphics().drawString(impact, position.getX(), position.getY());
    }

    private Position modelPositionToPixelsPosition(Position position){
        //shift in odd

        int insideRadiusWithBorder = cellsManager.getInsideRadiusWithBorder();
        int x0 = 2 * insideRadiusWithBorder * (1 + position.getY()) + shiftFromBorder;
        //int y0 = (1 + (int)Math.round(1.5 * position.getX())) * (options.getLineWidth() + options.getCellEdge()) + shiftFromBorder;
        int y0 = (options.getLineWidth() + options.getCellEdge()) + ((int)Math.round(1.5 * options.getCellEdge()) + options.getLineWidth()) * position.getX() + shiftFromBorder;
        if(position.getX() % 2 == 0){
            x0 -= insideRadiusWithBorder;
        }

//        int x0 = (options.getCellEdge() + options.getLineWidth()) + (options.getCellEdge() + options.getLineWidth()) * 2 * position.getY() + shiftFromBorder;
//        int y0 = insideRadiusWithBorder + insideRadiusWithBorder * 2 * position.getX() + shiftFromBorder;
//        if(position.getX() % 2 == 1){
//            y0 -= insideRadiusWithBorder;
//        }
        return new Position(x0, y0);
    }


    private void createImageField() {
        Graphics2D g = img.createGraphics();
        g.setPaint (Color.WHITE);
        g.fillRect(0,0,img.getWidth(),img.getHeight());
        g.setPaint(Color.BLACK);

        cellsManager.drawGrid(shiftFromBorder, g);
    }


}
