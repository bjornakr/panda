package no.atferdssenteret.panda.model;

import java.util.Observer;

public interface Observable {
    public void addObserver(Observer o);
    public void notifyObservers();
}
