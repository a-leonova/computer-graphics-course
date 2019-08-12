package com.nsu.fit.leonova.observer;

public interface BSplineObservable {
    void addObserver(BSplineObserver obs);
    void removeObserver(BSplineObserver obs);
}
