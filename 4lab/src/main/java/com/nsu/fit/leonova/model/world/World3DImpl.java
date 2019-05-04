package com.nsu.fit.leonova.model.world;

import com.nsu.fit.leonova.globals.Globals;
import com.nsu.fit.leonova.model.*;
import com.nsu.fit.leonova.model.bspline.SplineParameters;
import com.nsu.fit.leonova.model.memento.FigureMemento;
import com.nsu.fit.leonova.model.memento.WorldMemento;
import com.nsu.fit.leonova.observer.BSplineObservable;
import com.nsu.fit.leonova.observer.BSplineObserver;
import com.nsu.fit.leonova.observer.WorldObservable;
import com.nsu.fit.leonova.observer.WorldObserver;
import org.ejml.simple.SimpleMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class World3DImpl implements World3D, WorldObservable, BSplineObservable {
    private List<WorldObserver> worldObservers = new ArrayList<>();
    private List<BSplineObserver> bSplineObservers = new ArrayList<>();
    private List<Figure> figures = new ArrayList<>();
    private Figure currentWorkingFigure;

    private SimpleMatrix worldRotation = MatrixGenerator.identity4();
    private SimpleMatrix shiftWorld = MatrixGenerator.shiftMatrix(0, 0, 500);

    private WorldParameters worldParameters = new WorldParameters();

    private Color backgroundColor = Color.BLACK;

    public World3DImpl() {

    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setWorldRotation(SimpleMatrix worldRotation) {
        this.worldRotation = worldRotation;
    }

    @Override
    public void showSpline3D() {
        BufferedImage image = new BufferedImage(Globals.IMAGE_WIDTH, Globals.IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = image.createGraphics();

        graphics.setPaint(backgroundColor);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

        for (Figure figure : figures) {
            Point3D[][] transformedPoints3D = figure.getTransformedPoints3D();

            List<ConnectedPoints2D> connectedPoints2D = findFigurePointsToDraw(transformedPoints3D, figure.getParameters().getM(),
                    figure.getParameters().getK(), figure.getPointsToRotateCount());

            graphics.setPaint(figure.getParameters().getColor());
            for (ConnectedPoints2D pair : connectedPoints2D) {
                graphics.drawLine(pair.getA().x, pair.getA().y, pair.getB().x, pair.getB().y);
            }
            drawAxis(graphics, figure.getAxis());
        }
        createAndDrawWorldAxis(graphics);
        for (WorldObserver worldObserver : worldObservers) {
            worldObserver.setMainImage(image);
        }
    }

    @Override
    public void rotationForOX(int shift) {
        double d = Math.toRadians(shift * 0.5);
        if (currentWorkingFigure != null) {
            currentWorkingFigure.rotateForOX(d);
        } else {
            worldRotation = MatrixGenerator.rotationMatrix4OX(d).mult(worldRotation);
        }
        showSpline3D();
    }

    @Override
    public void rotationForOY(int shift) {
        double d = Math.toRadians(shift * 0.5);
        if (currentWorkingFigure != null) {
            currentWorkingFigure.rotateForOY(d);
        } else {
            worldRotation = MatrixGenerator.rotationMatrix4OY(d).mult(worldRotation);
        }
        showSpline3D();
    }

    @Override
    public void addPointToCurrentBSpline(Point point) {
        currentWorkingFigure.addPoint(point);
    }

    @Override
    public void removePointFromCurrentBSpline(Point point) {
        currentWorkingFigure.removePoint(point);
    }

    @Override
    public void pressedPointOnCurrentBSpline(Point point) {
        currentWorkingFigure.pressedPoint(point);
    }

    @Override
    public void draggedPointOnCurrentBSpline(Point point) {
        currentWorkingFigure.draggedPoint(point);
    }

    @Override
    public void setFigureRotation(SimpleMatrix rotationMatrix) {
        if (currentWorkingFigure != null) {
            currentWorkingFigure.setRotation(rotationMatrix);
        }
    }

    @Override
    public void showBSplineInfo(int index) {
        if (figures.size() <= index) {
            throw new IllegalArgumentException("Impossible! Size: " + figures.size() + " Index: " + index);
        }
        currentWorkingFigure = figures.get(index);
        figures.get(index).showBspline();
    }

    @Override
    public void addSpline() {
        Figure figure = new Figure(bSplineObservers, figures.size());
        figures.add(figure);
        figure.showBspline();
        currentWorkingFigure = figure;
        for (BSplineObserver obs : bSplineObservers) {
            obs.addSpline(figure.getParameters().getSplineName());
        }
        for (WorldObserver obs : worldObservers) {
            obs.addFigure(figure.getParameters().getSplineName());
        }
    }

    @Override
    public void removeSpline(int index) {
        figures.remove(index);
        for (BSplineObserver obs : bSplineObservers) {
            obs.removeSpline(index);
        }
        for (WorldObserver obs : worldObservers) {
            obs.removeFigure(index);
        }
        if (figures.size() > 0) {
            showBSplineInfo(figures.size() - 1);
        }
    }

    @Override
    public void removeAllSplines() {
        for (int i = 0; i < figures.size(); ++i) {
            for (BSplineObserver obs : bSplineObservers) {
                obs.removeSpline(0);
            }
            for (WorldObserver obs : worldObservers) {
                obs.removeFigure(0);
            }
        }
        figures.clear();
    }

    @Override
    public void setSplineParameters(SplineParameters parameters) {
        if (currentWorkingFigure != null) {
            currentWorkingFigure.setBsplineParameters(parameters);
            for (BSplineObserver obs : bSplineObservers) {
                obs.changeFigureName(parameters.getSplineName(), figures.indexOf(currentWorkingFigure));
            }
            for (WorldObserver obs : worldObservers) {
                obs.renameFigure(parameters.getSplineName(), figures.indexOf(currentWorkingFigure));
            }
        }
    }

    @Override
    public void setFigureCenter(Point3D figureCenter) {
        if (currentWorkingFigure != null) {
            currentWorkingFigure.shift(figureCenter);
        } else {
            shiftWorld = MatrixGenerator.shiftMatrix((int) Math.round(figureCenter.getX()), (int) Math.round(figureCenter.getY()),
                    (int) Math.round(figureCenter.getZ()));
        }
        showSpline3D();
    }

    @Override
    public void setSelectedFigure(int index) {
        Point3D center;
        if (index == -1) {
            currentWorkingFigure = null;
            center = new Point3D(shiftWorld.get(0, 3),
                    shiftWorld.get(1, 3),
                    shiftWorld.get(2, 3));
        } else {
            currentWorkingFigure = figures.get(index);
            center = new Point3D(currentWorkingFigure.getShiftMatrix().get(0, 3),
                    currentWorkingFigure.getShiftMatrix().get(1, 3),
                    currentWorkingFigure.getShiftMatrix().get(2, 3));
        }
        for (WorldObserver obs : worldObservers) {
            obs.setInfo(center);
        }
    }

    @Override
    public void setWorldParameters(WorldParameters wp) {
        worldParameters = wp;
        showSpline3D();
    }

    @Override
    public void scale(double ds) {
        for (Figure figure : figures) {
            figure.changeScale(ds);
        }
    }

    @Override
    public void settingsButtonPressed() {
        showBSplineInfo(0);
        for (BSplineObserver obs : bSplineObservers) {
            obs.openFrame();
        }
    }

    @Override
    public void updateWorldParameters() {
        for (WorldObserver obs : worldObservers) {
            obs.updateWorldParameters(worldParameters);
        }
    }

    @Override
    public WorldMemento getWorldMemento() {
        List<FigureMemento> figureMementos = new ArrayList<>();
        for (Figure figure : figures) {
            figureMementos.add(figure.getMemento());
        }
        Figure figure = figures.get(0);
        SplineParameters splineParameters = figure.getParameters();
        double[][] rotation = new double[][]{
                {worldRotation.get(0, 0), worldRotation.get(0, 1), worldRotation.get(0, 2)},
                {worldRotation.get(1, 0), worldRotation.get(1, 1), worldRotation.get(1, 2)},
                {worldRotation.get(2, 0), worldRotation.get(2, 1), worldRotation.get(2, 2)}};

        return new WorldMemento(splineParameters, worldParameters, rotation, backgroundColor, figureMementos);
    }

    @Override
    public void resetAngles() {
        worldRotation = MatrixGenerator.identity4();
        for (Figure figure : figures) {
            figure.resetAngles();
        }
        showSpline3D();
    }

    @Override
    public void addObserver(WorldObserver obs) {
        worldObservers.add(obs);
    }

    @Override
    public void removeObserver(WorldObserver obs) {
        worldObservers.remove(obs);
    }

    @Override
    public void addObserver(BSplineObserver obs) {
        bSplineObservers.add(obs);
        for (Figure figure : figures) {
            figure.addObserver(obs);
        }
    }

    @Override
    public void removeObserver(BSplineObserver obs) {
        bSplineObservers.remove(obs);
        for (Figure figure : figures) {
            figure.removeObserver(obs);
        }
    }

    private void createAndDrawWorldAxis(Graphics2D graphics2D){
        List<ConnectedPoints3D> connectedPoints3D = new ArrayList<>(3);
        Point3D center = new Point3D(0, 0, 0);
        Point3D axisX = new Point3D(1000000, 0, 0);
        Point3D axisY = new Point3D(0, 1000000, 0);
        Point3D axisZ = new Point3D(0, 0, 1000000);
        connectedPoints3D.add(new ConnectedPoints3D(center, axisX));
        connectedPoints3D.add(new ConnectedPoints3D(center, axisY));
        connectedPoints3D.add(new ConnectedPoints3D(center, axisZ));
        drawAxis(graphics2D, connectedPoints3D);
    }

    private void drawAxis(Graphics2D graphics2D, List<ConnectedPoints3D> connectedPoints3D){
        List<ConnectedPoints2D> points = clipAndTransform(connectedPoints3D);

        if(points.size() == 3){
            graphics2D.setPaint(Color.RED);
            graphics2D.drawLine(points.get(0).getA().x, points.get(0).getA().y, points.get(0).getB().x, points.get(0).getB().y);
            graphics2D.setPaint(Color.GREEN);
            graphics2D.drawLine(points.get(1).getA().x, points.get(1).getA().y, points.get(1).getB().x, points.get(1).getB().y);
            graphics2D.setPaint(Color.BLUE);
            graphics2D.drawLine(points.get(2).getA().x, points.get(2).getA().y, points.get(2).getB().x, points.get(2).getB().y);
        }
    }

    private List<ConnectedPoints2D> clipAndTransform(List<ConnectedPoints3D> connectedPoints3D){
        shiftAndRotateWorld(connectedPoints3D);
        Iterator<ConnectedPoints3D> it = connectedPoints3D.iterator();
        while (it.hasNext()) {
            ConnectedPoints3D c = it.next();
            Vector3D a = new Vector3D(c.getA().getX(), c.getA().getY(), c.getA().getZ());
            Vector3D b = new Vector3D(c.getB().getX(), c.getB().getY(), c.getB().getZ());
            boolean inCube = Utils.clipLineInCube(a, b, -worldParameters.getSw() / 2.0, worldParameters.getSw() / 2.0,
                    -worldParameters.getSh() / 2.0, worldParameters.getSh() / 2.0,
                    worldParameters.getZf(), worldParameters.getZb());
            if (inCube) {
                c.setA(new Point3D(a.get(0), a.get(1), a.get(2)));
                c.setB(new Point3D(b.get(0), b.get(1), b.get(2)));
            }
            else {
                it.remove();
            }
        }
        return perspective(connectedPoints3D);
    }

    private List<ConnectedPoints2D> findFigurePointsToDraw(Point3D[][] points, int m, int k, int pointToRotateSize) {
        List<ConnectedPoints3D> connectedPoints3D = new ArrayList<>();

        for (int i = 1; i < pointToRotateSize; ++i) {
            for (int j = 0; j < m; j++) {
                connectedPoints3D.add(new ConnectedPoints3D(points[i - 1][j], points[i][j]));
            }
        }

        for (int i = 0; i < pointToRotateSize; i += k) {
            for (int j = 1; j < m; j++) {
                connectedPoints3D.add(new ConnectedPoints3D(points[i][j - 1], points[i][j]));
            }
        }

        return clipAndTransform(connectedPoints3D);
    }

    private List<ConnectedPoints2D> perspective(List<ConnectedPoints3D> connectedPoints3D) {
        List<ConnectedPoints2D> connectedPoints2D = new ArrayList<>();
        SimpleMatrix perspectiveMtx = MatrixGenerator.projectionMatrix(worldParameters.getZf());
        for (ConnectedPoints3D c : connectedPoints3D) {
            SimpleMatrix coordinatesA = new SimpleMatrix(new double[][]{{c.getA().getX()},
                    {c.getA().getY()},
                    {c.getA().getZ()},
                    {1}});
            SimpleMatrix coordinatesB = new SimpleMatrix(new double[][]{{c.getB().getX()},
                    {c.getB().getY()},
                    {c.getB().getZ()},
                    {1}});

            coordinatesA = perspectiveMtx.mult(coordinatesA);
            coordinatesB = perspectiveMtx.mult(coordinatesB);
            coordinatesA = coordinatesA.divide(coordinatesA.get(3, 0));
            coordinatesB = coordinatesB.divide(coordinatesB.get(3, 0));

            connectedPoints2D.add(
                    new ConnectedPoints2D(
                            new Point((int) Math.round(coordinatesA.get(0, 0)) + Globals.IMAGE_WIDTH / 2, (int) Math.round(coordinatesA.get(1, 0)) + Globals.IMAGE_HEIGHT / 2),
                            new Point((int) Math.round(coordinatesB.get(0, 0)) + Globals.IMAGE_WIDTH / 2, (int) Math.round(coordinatesB.get(1, 0)) + Globals.IMAGE_HEIGHT / 2)));
        }
        return connectedPoints2D;
    }

    private void shiftAndRotateWorld(List<ConnectedPoints3D> connectedPoints3D) {
        for (ConnectedPoints3D c : connectedPoints3D) {
            SimpleMatrix coordinatesA = new SimpleMatrix(new double[][]{{c.getA().getX()},
                    {c.getA().getY()},
                    {c.getA().getZ()},
                    {1}});
            SimpleMatrix coordinatesB = new SimpleMatrix(new double[][]{{c.getB().getX()},
                    {c.getB().getY()},
                    {c.getB().getZ()},
                    {1}});

            coordinatesA = worldRotation.mult(coordinatesA);
            coordinatesB = worldRotation.mult(coordinatesB);
            coordinatesA = shiftWorld.mult(coordinatesA);
            coordinatesB = shiftWorld.mult(coordinatesB);

            c.setA(new Point3D(coordinatesA.get(0, 0), coordinatesA.get(1, 0), coordinatesA.get(2, 0)));
            c.setB(new Point3D(coordinatesB.get(0, 0), coordinatesB.get(1, 0), coordinatesB.get(2, 0)));
        }
    }
}
