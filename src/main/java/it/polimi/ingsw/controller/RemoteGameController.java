package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.view.RemoteView;

import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteGameController extends Remote {

    GameModel getGameModel() throws  RemoteException;
    void update(RemoteView view) throws RemoteException;
    void addObserver(RemoteView view) throws RemoteException;
    void setPlayerOnline(String user, boolean online) throws RemoteException;
    void startTimer(RemoteView view, Socket socket) throws RemoteException;
    void startTimerSP(RemoteView view) throws RemoteException;
    void reAddObserver(RemoteView view) throws RemoteException;
    void createGameModel(int level) throws RemoteException;
    void updateSP(RemoteView view) throws RemoteException;
    boolean getSinglePlayerStarted () throws RemoteException;
    boolean getMultiPlayerStarted () throws RemoteException;

}
