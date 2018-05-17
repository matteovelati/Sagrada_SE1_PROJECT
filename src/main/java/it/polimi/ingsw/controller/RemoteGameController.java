package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.view.cli.RemoteView;
import it.polimi.ingsw.view.cli.SubjectView;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteGameController extends Remote {

    public GameModel getGameModel() throws  RemoteException;
    public void update(RemoteView view) throws RemoteException;
    public void addObserver(RemoteView view) throws RemoteException;
}
