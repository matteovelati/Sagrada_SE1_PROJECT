package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.cli.ViewCLI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Timer;

public interface RemoteGameController extends Remote {

    public GameModel getGameModel() throws  RemoteException;
    public void update(RemoteView view) throws RemoteException;
    public void addObserver(RemoteView view) throws RemoteException;
    public void setPlayerOnline(String user, boolean online) throws RemoteException;
    public void startTimer(RemoteView view) throws RemoteException;
    public void reAddObserver(RemoteView view) throws RemoteException;

}
