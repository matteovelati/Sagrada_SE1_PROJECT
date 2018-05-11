package it.polimi.ingsw.controller;

import it.polimi.ingsw.view.cli.ViewCLI;

public interface ControllerObserver {

    void update(ViewCLI viewCLI);
    //void update(ViewGUI viewGUI);

}
