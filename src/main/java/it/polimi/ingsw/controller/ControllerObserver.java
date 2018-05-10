package it.polimi.ingsw.controller;

public interface ControllerObserver {

    void update(ViewCLI viewCLI);
    void update(ViewGUI viewGUI);

}
