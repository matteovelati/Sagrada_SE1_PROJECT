package it.polimi.ingsw.model;

import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.cli.RemoteView;
import it.polimi.ingsw.view.cli.ViewObserver;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public abstract class SubjectGameModel {

    private List<RemoteView> list = new ArrayList<RemoteView>();

    public List<RemoteView> getObservers(){
        return list;
    }

    public void addObserver(RemoteView observer) throws RemoteException {
        list.add( observer );
    }

    public void removeObserver(RemoteView observer) {
        list.remove( observer );
    }

    /*public void notifyObservers(RemoteGameModel gameModel) throws RemoteException {
        for(ViewObserver observer: list) {
            observer.update(gameModel);
        }
    }*/
}
