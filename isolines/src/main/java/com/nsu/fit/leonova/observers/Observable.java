package com.nsu.fit.leonova.observers;

public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
}
