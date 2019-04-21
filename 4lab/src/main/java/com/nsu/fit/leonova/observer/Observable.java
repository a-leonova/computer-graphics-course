package com.nsu.fit.leonova.observer;

public interface Observable {
    void addObserver(Observer obs);
    void deleteObserver(Observer obs);
}
