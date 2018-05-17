package it.polimi.ingsw.model;

import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.cli.RemoteView;
import it.polimi.ingsw.view.cli.ViewObserver;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public abstract class SubjectGameModel {

    private List<ViewObserver> list = new ArrayList<ViewObserver>();

    public void addObserver(ViewObserver observer) {
        list.add( observer );
    }

    public void removeObserver(ViewObserver observer) {
        list.remove( observer );
    }

    public void notifyObservers(GameModel gameModel) throws RemoteException {
        for(ViewObserver observer: list) {
            observer.update(gameModel);
        }
    }
}
