package com.nsu.fit.leonova.observer;

public interface WorldObservable {
    void addObserver(WorldObserver obs);
    void removeObserver(WorldObserver obs);
}
