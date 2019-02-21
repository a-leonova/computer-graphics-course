package com.leonova.fit.nsu.model;

public class GameOptions {

    private double firstImpact = 1.0;
    private double secondImpact = 0.3;
    private double liveBegin = 2.0;
    private double liveEnd = 3.3;
    private double birthBegin = 2.3;
    private double birthEnd = 2.9;

    private boolean modeReplace = true;

    public GameOptions(double firstImpact, double secondImpact, double liveBegin, double liveEnd, double birthBegin, double birthEnd, boolean modeReplace) {
        this.firstImpact = firstImpact;
        this.secondImpact = secondImpact;
        this.liveBegin = liveBegin;
        this.liveEnd = liveEnd;
        this.birthBegin = birthBegin;
        this.birthEnd = birthEnd;
        this.modeReplace = modeReplace;
    }

    public double getFirstImpact() {
        return firstImpact;
    }

    public void setFirstImpact(double firstImpact) {
        this.firstImpact = firstImpact;
    }

    public double getSecondImpact() {
        return secondImpact;
    }

    public void setSecondImpact(double secondImpact) {
        this.secondImpact = secondImpact;
    }

    public double getLiveBegin() {
        return liveBegin;
    }

    public void setLiveBegin(double liveBegin) {
        this.liveBegin = liveBegin;
    }

    public double getLiveEnd() {
        return liveEnd;
    }

    public void setLiveEnd(double liveEnd) {
        this.liveEnd = liveEnd;
    }

    public double getBirthBegin() {
        return birthBegin;
    }

    public void setBirthBegin(double birthBegin) {
        this.birthBegin = birthBegin;
    }

    public double getBirthEnd() {
        return birthEnd;
    }

    public void setBirthEnd(double birthEnd) {
        this.birthEnd = birthEnd;
    }

    public boolean isModeReplace() {
        return modeReplace;
    }

    public void setModeReplace(boolean modeReplace) {
        this.modeReplace = modeReplace;
    }
}
