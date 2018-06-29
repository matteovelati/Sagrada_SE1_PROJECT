package it.polimi.ingsw.model;

import it.polimi.ingsw.view.RemoteView;

import java.awt.*;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface RemoteGameModel extends Remote {


    public boolean getUpdateSocket();

    public void setUpdateSocket(boolean updateSocket);
    /**
     * gets the list of SOCKET observers in game
     * @return the list of SOCKET observers in game
     * @throws RemoteException if the reference could not be accessed
     */
    ArrayList<Socket> getObserverSocket() throws RemoteException;
    /**
     * adds again an observer after he has lost connection
     * @param observer the observer to be added
     * @throws RemoteException if the reference could not be accessed
     */
    void reAddObserver(RemoteView observer) throws RemoteException;

    /**
     * gets the list of RMI observers in game
     * @return the list of RMI observers in game
     * @throws RemoteException if the reference could not be accessed
     */
    List<RemoteView> getObservers() throws RemoteException;

    /**
     * adds an observer at the beginning
     * @param observer the observer to be addded
     * @throws RemoteException if the reference could not be accessed
     */
    void addObserver(RemoteView observer) throws RemoteException;

    /**
     * removes an observer from the observers's list (setting him as 'null')
     * @param observer the observer to be removed
     * @throws RemoteException if the reference could not be accessed
     */
    void removeObserver(RemoteView observer) throws RemoteException;

    /**
     * gets the actual state
     * @return the actual state
     * @throws RemoteException if the reference could not be accessed
     */
    States getState() throws RemoteException;

    /**
     * gets the players in game
     * @return an arraylist of players in game
     * @throws RemoteException if the reference could not be accessed
     */
    ArrayList<Player> getPlayers() throws RemoteException;

    /**
     * gets the field of the match
     * @return the field of the match
     * @throws RemoteException if the reference could not be accessed
     */
    Field getField() throws RemoteException;

    /**
     * gets the bag of the match
     * @return the bag od the match
     * @throws RemoteException if the reference could not be accessed
     */
    Bag getBag() throws RemoteException;

    /**
     * gets the schemecards of the match
     * @return a list of 3 schemecars
     * @throws RemoteException if the reference could not be accessed
     */
    ArrayList<SchemeCard> getSchemeCards() throws RemoteException;

    /**
     * gets the actual player of the match
     * @return the actual player
     * @throws RemoteException if the reference could not be accessed
     */
    Player getActualPlayer() throws RemoteException;

    /**
     * gets the roundmanager of the match
     * @return the roundmanager of the match
     * @throws RemoteException if the reference could not be accessed
     */
    RoundManager getRoundManager() throws RemoteException;

    /**
     * gets all the colors of the Colors's enumeration
     * @return a list which contains 5 colors
     * @throws RemoteException if the reference could not be accessed
     */
    ArrayList<Colors> getAllColors() throws RemoteException;
}
