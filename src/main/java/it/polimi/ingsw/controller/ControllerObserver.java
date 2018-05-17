package it.polimi.ingsw.controller;

import it.polimi.ingsw.view.cli.RemoteView;
import it.polimi.ingsw.view.cli.ViewCLI;

import java.rmi.RemoteException;

public interface ControllerObserver {

    void update(RemoteView view) throws RemoteException;
    //void update(ViewGUI viewGUI);

}
