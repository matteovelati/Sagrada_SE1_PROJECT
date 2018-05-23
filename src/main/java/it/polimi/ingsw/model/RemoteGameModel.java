package it.polimi.ingsw.model;

import it.polimi.ingsw.view.RemoteView;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface RemoteGameModel extends Remote {

    public void notifyObservers() throws RemoteException;

    //private List<RemoteView> list = new ArrayList<RemoteView>();

    public List<RemoteView> getObservers() throws RemoteException;

    public void addObserver(RemoteView observer) throws RemoteException;

    public void removeObserver(RemoteView observer) throws RemoteException;

    public States getState() throws RemoteException;

    public ArrayList<Player> getPlayers() throws RemoteException;

    public Field getField() throws RemoteException;

    public Bag getBag() throws RemoteException;

    public ArrayList<SchemeCard> getSchemeCards() throws RemoteException;

    public Player getActualPlayer() throws RemoteException;

    public RoundManager getRoundManager() throws RemoteException;

    public ArrayList<Colors> getAllColors() throws RemoteException;
}
