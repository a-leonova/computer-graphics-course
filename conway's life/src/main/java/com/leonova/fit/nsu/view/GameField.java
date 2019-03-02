package com.leonova.fit.nsu.view;

import com.leonova.fit.nsu.controller.GameController;
import com.leonova.fit.nsu.model.Cell;
import com.leonova.fit.nsu.model.Position;
import com.leonova.fit.nsu.globals.GlobalConsts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;

public class GameField extends JPanel implements MouseMotionListener {

    private GraphicsOptions graphicsOptions;
    private CellsManager cellsManager;
    private GameController gameController;
    private BufferedImage img;
    private int shiftFromBorder = 10;
    private JLayeredPane pane;

    private Position lastCell = null;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public GameField(GraphicsOptions graphicsOptions)  {
        this.graphicsOptions = graphicsOptions;
        cellsManager = new CellsManager(graphicsOptions);

        int insideRadiusWithHalfBorder = (int) Math.round((graphicsOptions.getCellEdge() + (int)Math.round(graphicsOptions.getLineWidth() * 0.5)) * Math.cos(Math.PI / 6));
        int width = 2 * insideRadiusWithHalfBorder * graphicsOptions.getColumns() + shiftFromBorder * 2;
        int halfBorder = (int)Math.round(graphicsOptions.getLineWidth() * 0.5);
        int edgeWithHalfBorder = graphicsOptions.getCellEdge() + halfBorder;
        int shiftYWithHalfBorder = (int) Math.round(edgeWithHalfBorder * Math.sin(Math.PI / 6));
        int a = (edgeWithHalfBorder + shiftYWithHalfBorder);
        int height = a * graphicsOptions.getRows() + shiftFromBorder * 2 + graphicsOptions.getCellEdge() - shiftYWithHalfBorder + halfBorder;
        pane = new JLayeredPane();
        pane.setPreferredSize(new Dimension(width, height));
        add(pane);
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);

        addMouseMotionListener(this);
        setPreferredSize(new Dimension(width , height));
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(img.getRGB(e.getX(), e.getY()) != GlobalConsts.borderColor.getRGB()){
                    lastCell = cellsManager.getSelectedCell(new Position(e.getX(), e.getY()), shiftFromBorder);
                    //TODO: index out of border in controller
                    gameController.pressCell(lastCell);
                }
            }
        });
        createImageField();
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
    }

    public void setGraphicsOptions(GraphicsOptions graphicsOptions) {
        this.graphicsOptions = graphicsOptions;
        cellsManager.setOptions(graphicsOptions);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(img, 0,0,this);
    }

    public void repaint(HashSet<Cell> cells, GraphicsOptions graphicsOptions){
        img.createGraphics().clearRect(0,0, img.getWidth(), img.getHeight());
    }

    public void updateGraphicField(HashSet<Cell> changedCells){
        for(Cell cell : changedCells){
            Position pixels = cellsManager.modelPositionToPixelsPosition(cell.getPosition(), shiftFromBorder);
            //Color oldColor = new Color(img.getRGB(pixels.getX(), pixels.getY()));
            if(cell.isAlive()){
                cellsManager.repaintCell(pixels, GlobalConsts.borderColor, GlobalConsts.aliveCell, img);
            }
            else{
                cellsManager.repaintCell(pixels, GlobalConsts.borderColor, GlobalConsts.deadCell, img);
            }
            if(graphicsOptions.isShowImpact()){
                paintImpact(pixels, String.format("%.1f", cell.getImpact()));
            }
        }
        repaint();
    }


    private void paintImpact(Position position, String impact){
        Graphics2D g = img.createGraphics();

        if(graphicsOptions.getCellEdge() >= GlobalConsts.minEdgeForCellToShowImpact){

            Font myFont = new Font("Serif",Font.PLAIN, GlobalConsts.impactSize);
            g.setFont(myFont);

            g.setPaint(GlobalConsts.impactColor);
            g.drawString(impact, position.getX() - GlobalConsts.impactSize/2, position.getY() + GlobalConsts.impactSize/2);

        }
    }


    private void createImageField() {
        Graphics2D g = img.createGraphics();
        g.setPaint (Color.WHITE);
        g.fillRect(0,0,img.getWidth(),img.getHeight());
        g.setPaint(Color.BLACK);

        cellsManager.drawGrid(shiftFromBorder, g);
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        Position newCell = cellsManager.getSelectedCell(new Position(e.getX(), e.getY()), shiftFromBorder);
        if(!newCell.equals(lastCell) && img.getRGB(e.getX(), e.getY()) != GlobalConsts.borderColor.getRGB()){
            gameController.pressCell(newCell);
            lastCell = newCell;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
