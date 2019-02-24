package com.leonova.fit.nsu.model;

public class Cell {
    private boolean alive;
    private double impact = 0.0;

    private Position position;

    public Cell(Position position) {
        this.position = position;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public double getImpact() {
        return impact;
    }

    public void setImpact(double impact) {
        this.impact = impact;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
