package it.polimi.ingsw.view;

import it.polimi.ingsw.model.RemoteGameModel;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemoteView extends Remote {

    /**
     * sets if player is online or not
     * @param online the boolean to be set
     * @throws RemoteException if the reference could not be accessed
     */
    void setOnline(boolean online) throws RemoteException;

    /**
     * gets if it's needed to start the timer
     * @return true if the timer has to be started
     * @throws RemoteException if the reference could not be accessed
     */
    boolean getStartTimerSocket() throws RemoteException;

    /**
     * gets if a player has to be set online
     * @return true if the player has to be set online
     * @throws RemoteException if the reference could not be accessed
     */
    boolean getReturnOnline() throws RemoteException;

    /**
     * gets if the socket timeout connection needs be deleted or not
     * @return true if the socket connection needs to be deleted, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    boolean getDeleteConnectionSocket() throws RemoteException;

    /**
     * gets if player is online or not
     * @return true if the player is online, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    boolean getOnline() throws RemoteException;

    /**
     * gets the player's username
     * @return the player's username
     * @throws RemoteException if the reference could not be accessed
     */
    String getUser() throws RemoteException;

    /**
     * gets choose1
     * @return the first choice of the client
     * @throws RemoteException if the reference could not be accessed
     */
    int getChoose1() throws RemoteException;

    /**
     * gets choose2
     * @return the second choice of the client
     * @throws RemoteException if the reference could not be accessed
     */
    int getChoose2() throws RemoteException;

    /**
     * gets if game is ended or not
     * @return true if game is ended, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    boolean getEndGame() throws RemoteException;

    /**
     * gets arraylist of choices
     * @return an arraylist of integer which contains the client's inputs
     * @throws RemoteException if the reference could not be accessed
     */
    ArrayList<Integer> getChoices() throws RemoteException;

    /**
     * updates each view in the game
     * @param gameModel the gamemodel of the match
     * @throws RemoteException if the reference could not be updated
     */
    void update(RemoteGameModel gameModel) throws RemoteException;

    /**
     * prints a message
     * @param string the message to be printed
     * @throws RemoteException if the reference could not be accessed
     */
    void print(String string) throws RemoteException;

    /**
     * prints an error message
     * @param string the message to be printed
     * @throws RemoteException if the reference could not be accessed
     */
    void printError(String string) throws RemoteException;

    /**
     * gets if has started a singleplayer match
     * @return true if the game is in singleplayer mode
     * @throws RemoteException if the reference could not be accessed
     */
    boolean getSinglePlayer() throws RemoteException;

    /**
     * gets if the client is connected with socket
     * @return true if the client is connected with socket
     * @throws RemoteException if the reference could not be accessed
     */
    boolean getSocketConnection() throws RemoteException;

    /**
     * gets the level of difficulty in a singleplayer match
     * @return the level of difficulty chosen
     * @throws RemoteException if the reference could not be accessed
     */
    int getLevel() throws RemoteException;
}
