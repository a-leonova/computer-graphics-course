package com.nsu.fit.leonova.model.bspline;

import java.awt.*;

public interface BSplineCreator {
    void addPoint(Point point);
    void removePoint(Point point);
    void pressedPoint(Point point);
    void draggedPoint(Point point);
}
