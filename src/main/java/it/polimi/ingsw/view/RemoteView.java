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
    public void setOnline(boolean online) throws RemoteException;

    /**
     * gets if player is online or not
     * @return true if the player is online, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    public boolean getOnline() throws RemoteException;

    /**
     * gets the player's username
     * @return the player's username
     * @throws RemoteException if the reference could not be accessed
     */
    public String getUser() throws RemoteException;

    /**
     * gets choose1
     * @return the first choice of the client
     * @throws RemoteException if the reference could not be accessed
     */
    public int getChoose1() throws RemoteException;

    /**
     * gets choose2
     * @return the second choice of the client
     * @throws RemoteException if the reference could not be accessed
     */
    public int getChoose2() throws RemoteException;

    /**
     * gets if game is ended or not
     * @return true if game is ended, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    public boolean getEndGame() throws RemoteException;

    /**
     * gets arraylist of choices
     * @return an arraylist of integer which contains the client's inputs
     * @throws RemoteException if the reference could not be accessed
     */
    public ArrayList<Integer> getChoices() throws RemoteException;

    /**
     * updates each view in the game
     * @param gameModel the gamemodel of the match
     * @throws RemoteException if the reference could not be updated
     */
    public void update(RemoteGameModel gameModel) throws RemoteException;

    /**
     * prints a message
     * @param string the message to be printed
     * @throws RemoteException if the reference could not be accessed
     */
    public void print(String string) throws RemoteException;

    /**
     * prints an error message
     * @param string the message to be printed
     * @throws RemoteException if the reference could not be accessed
     */
    public void printError(String string) throws RemoteException;
}
