package com.nsu.fit.leonova.globals;

import java.text.DecimalFormat;

public class Globals {
    public static final int START_IMAGE_WIDTH = 400;
    public static final int START_IMAGE_HEIGHT = 400;

    public static final int START_LEGEND_WIDTH = 50;
    public static final int START_LEGEND_HEIGHT = 400;

    public static final int MIN_FRAME_WIDTH = START_IMAGE_WIDTH + START_LEGEND_WIDTH + 50;
    public static final int MIN_FRAME_HEIGHT = START_IMAGE_HEIGHT + 10;

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
}
