package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.RemoteGameModel;

import java.rmi.RemoteException;

public interface ViewObserver {

    public void update(RemoteGameModel gameModel) throws RemoteException;
}
