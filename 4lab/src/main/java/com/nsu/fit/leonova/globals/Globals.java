package com.nsu.fit.leonova.globals;

import com.nsu.fit.leonova.model.bspline.SplineParameters;

import java.awt.*;

public class Globals {
    private static final int M = 50;
    private static final int N = 5;
    private static final int K = 2;

    private static final double A = 0;
    private static final double B = 1;
    private static final double C = 0;
    private static final double D = 6.28;

    public static final int IMAGE_WIDTH = 700;
    public static final int IMAGE_HEIGHT = 700;

    private static final int SW = 400;
    private static final int SH = 400;
    private static final int ZF = 20;
    private static final int ZB = 100;

    public static final int MIN_N = 5;
    public static final int MIN_M = 5;
    public static final int MIN_K = 2;

    public static final double MIN_A = 0.0;
    public static final double MIN_B = 0.1;
    public static final double MIN_C = 0.0;
    public static final double MIN_D = 0.1;
    public static final double MAX_A = 0.9;
    public static final double MAX_B = 1.0;
    public static final double MAX_C = 6.18;
    public static final double MAX_D = 6.28;


    //public static final SplineParameters SPLINE_PARAMETERS = new SplineParameters(N, M, K, A, B, C, D, new Color(255, 255, 0), SW, SH);
    public static final String FIRST_FIGURE_NAME = "Figure #0";

}
