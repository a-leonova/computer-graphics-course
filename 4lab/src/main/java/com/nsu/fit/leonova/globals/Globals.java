package com.nsu.fit.leonova.globals;

import com.nsu.fit.leonova.model.bspline.SplineParameters;

import java.awt.*;

public class Globals {
    public static final int BSPLINE_WIDTH = 500;
    public static final int BSPLINE_HEIGH = 200;
    public static final int M = 50;
    public static final int N = 5;
    public static final int K = 2;

    public static final int A = 2;
    public static final int B = 2;
    public static final int C = 2;
    public static final int D = 2;

    public static final int IMAGE_WIDTH = 700;
    public static final int IMAGE_HEIGHT = 700;

    public static final int FOV = 120;
    public static final double ASPECT = IMAGE_WIDTH / (double)IMAGE_HEIGHT;

    public static final int SW = 400;
    public static final int SH = 400;
    public static final int ZF = 20;
    public static final int ZB = 100;

    public static final SplineParameters SPLINE_PARAMETERS = new SplineParameters(N, M, K, A, B, C, D, new Color(255, 255, 0), ZF, ZB, SW, SH);


}
