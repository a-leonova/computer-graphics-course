package com.nsu.fit.leonova.model;

import java.awt.*;

public interface BSpline {
    void addPoint(Point point);
    void removePoint(Point point);
    void pressedPoint(Point point);
    void draggedPoint(Point point);
}
