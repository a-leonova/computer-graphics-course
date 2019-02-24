package com.leonova.fit.nsu.observer;

public interface Observable {
    void addObserver(Observer obs);
    void deleteObserver(Observer obs);
}
