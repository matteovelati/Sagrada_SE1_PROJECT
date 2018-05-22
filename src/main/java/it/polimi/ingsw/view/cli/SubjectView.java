package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.ControllerObserver;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;
import java.util.List;

public abstract class SubjectView {

    private List<ControllerObserver> list = new ArrayList<ControllerObserver>();

    public void addObserver(ControllerObserver observer) {
        list.add( observer );
    }

    public void removeObserver(ControllerObserver observer) {
        list.remove( observer );
    }

    public void notifyObservers(RemoteView view) {
        for(ControllerObserver observer: list) {
            //observer.update(view);
        }
    }
}
