package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.view.RemoteView;

import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteGameController extends Remote {

    /**
     * gets the gamemodel of the match
     * @return the gamemodel of the match
     * @throws RemoteException if the reference could not be accessed
     */
    GameModel getGameModel() throws  RemoteException;

    /**
     * tries to send a ping to each client and remove them from the game if no answer is returned (only multiplayer)
     * modifies the gamemodel and finally changes it's state based on the current state and client's actions
     * @param view the view of the actual player
     * @throws RemoteException if the reference could not be accessed
     */
    void update(RemoteView view) throws RemoteException;

    /**
     * adds an RMI observer at the beginning
     * @param view the observer to be added
     * @throws RemoteException if the reference could not be accessed
     */
    void addObserver(RemoteView view) throws RemoteException;

    /**
     * finds and changes the status of the player passed
     * @param user the player who needs to change status
     * @param online the boolean to be set, true means online, false means offline
     * @throws RemoteException if the reference could not be accessed
     */
    void setPlayerOnline(String user, boolean online) throws RemoteException;

    /**
     * starts the timer for the entire turn of a player
     * when expired, it checks if he has lost connection or if he's only inactive anyway setting him as offline
     * @param view the remoteview of the client who is the actualplayer
     * @param socket if not equal to null, means the client is connected with socket, otherwise in RMI
     * @throws RemoteException if the reference could not be accessed
     */
    void startTimer(RemoteView view, Socket socket) throws RemoteException;

    /**
     * starts the timer for singleplayer match
     * every 3 seconds it tries to send a ping to each client and remove them from the game if no answer is returned
     * @param view the remoteview of the client
     * @throws RemoteException if the reference could not be accessed
     */
    void startTimerSP(RemoteView view) throws RemoteException;

    /**
     * adds again an RMI observer after he has lost connection
     * @param view the observer to be added
     * @throws RemoteException if the reference could not be accessed
     */
    void reAddObserver(RemoteView view) throws RemoteException;

    /**
     * creates a gamemodel of the match
     * @param level if equal to 0 creates a multiplayer gamemodel, otherwise a singleplayer gamemodel
     * @throws RemoteException if the reference could not be accessed
     */
    void createGameModel(int level) throws RemoteException;

    /**
     * gets if has been started a singleplayer match
     * @return true if a singleplayer match has been started, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    boolean getSinglePlayerStarted () throws RemoteException;

    /**
     * gets if has been started a multiplayer match
     * @return true if a multiplayer match has been started, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    boolean getMultiPlayerStarted () throws RemoteException;

}
