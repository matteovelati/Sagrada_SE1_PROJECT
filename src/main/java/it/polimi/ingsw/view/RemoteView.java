package it.polimi.ingsw.view;

import it.polimi.ingsw.model.RemoteGameModel;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemoteView extends Remote {

    public String getUser() throws RemoteException;

    public int getChoose1() throws RemoteException;

    public int getChoose2() throws RemoteException;

    public boolean getEndGame() throws RemoteException;

    public ArrayList<Integer> getChoices() throws RemoteException;

    public void update(RemoteGameModel gameModel) throws RemoteException;

    public void print(String string) throws RemoteException;
}
