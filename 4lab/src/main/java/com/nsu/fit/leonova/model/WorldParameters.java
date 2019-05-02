package com.nsu.fit.leonova.model;

public class WorldParameters {
    private int zf = 200;
    private int zb = 2000;
    private int sw = 500;
    private int sh = 500;

    public WorldParameters() {
    }

    public WorldParameters(int zf, int zb, int sw, int sh) {
        this.zf = zf;
        this.zb = zb;
        this.sw = sw;
        this.sh = sh;
    }

    public int getZf() {
        return zf;
    }

    public int getZb() {
        return zb;
    }

    public int getSw() {
        return sw;
    }

    public int getSh() {
        return sh;
    }
}
