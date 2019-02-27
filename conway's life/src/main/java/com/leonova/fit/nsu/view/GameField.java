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

    private GraphicsOptions options;
    private CellsManager cellsManager;
    private GameController gameController;
    private BufferedImage img;
    private int shiftFromBorder = 10;
    private JLayeredPane pane;

    private Position lastCell = null;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public GameField(GraphicsOptions options)  {
        this.options = options;
        cellsManager = new CellsManager(options);

        int insideRadiusWithHalfBorder = (int) Math.round((options.getCellEdge() + (int)Math.round(options.getLineWidth() * 0.5)) * Math.cos(Math.PI / 6));
        int width = 2 * insideRadiusWithHalfBorder * options.getCellsInRow() + shiftFromBorder * 2;
        int halfBorder = (int)Math.round(options.getLineWidth() * 0.5);
        int edgeWithHalfBorder = options.getCellEdge() + halfBorder;
        int shiftYWithHalfBorder = (int) Math.round(edgeWithHalfBorder * Math.sin(Math.PI / 6));
        int a = (edgeWithHalfBorder + shiftYWithHalfBorder);
        int height = a * options.getCellsInColumn() + shiftFromBorder * 2 + options.getCellEdge() - shiftYWithHalfBorder + halfBorder;
        pane = new JLayeredPane();
        pane.setPreferredSize(new Dimension(width, height));
        add(pane);
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
       // pane.add(img, 0);

        addMouseMotionListener(this);
        setPreferredSize(new Dimension(width , height));
        this.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//
//            }

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

    public void setOptions(GraphicsOptions options) {
        this.options = options;
        cellsManager.setOptions(options);
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
            if(options.isShowImpact()){
                paintImpact(pixels, String.format("%.1f", cell.getImpact()));
            }
        }
        repaint();
    }


    private void paintImpact(Position position, String impact){
        Graphics2D g = img.createGraphics();

        if(options.getCellEdge() >= GlobalConsts.minEdgeForCellToShowImpact){

            Font myFont = new Font("Serif",Font.PLAIN, GlobalConsts.impactSize);
            g.setFont(myFont);

            g.setPaint(GlobalConsts.impactColor);
//            Rectangle2D rectangle2d = g.getFontMetrics().getStringBounds(impact, g);
//            System.out.println(rectangle2d.getX() + " " + rectangle2d.getCenterY() + " " + rectangle2d.getWidth() + " "+ rectangle2d.getHeight());

            //System.out.println("Impact: " + g.getFontMetrics().str(impact));
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
        if(!newCell.equals(lastCell)){
            gameController.pressCell(newCell);
            lastCell = newCell;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //System.out.println("moved");
    }
}
