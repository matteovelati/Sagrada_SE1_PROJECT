package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameModel;

import java.rmi.RemoteException;

public interface ViewObserver {

    public void update(GameModel gameModel) throws RemoteException;
}
