package it.polimi.ingsw.controller;

import it.polimi.ingsw.view.RemoteView;

import java.rmi.RemoteException;

public interface ControllerObserver {

    void update(RemoteView view) throws RemoteException;
    //void update(ViewGUI viewGUI);

}
