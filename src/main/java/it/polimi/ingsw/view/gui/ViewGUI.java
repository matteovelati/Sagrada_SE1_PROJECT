package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.RemoteGameController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.RemoteGameModel;
import it.polimi.ingsw.model.States;
import it.polimi.ingsw.view.RemoteView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ViewGUI extends Application implements RemoteView, Serializable {
    private Stage mainStage;
    private boolean firstCall = true;

    private boolean online;
    private States state;
    private String user;
    private int choose1;
    private int choose2;
    private boolean endGame;
    private ArrayList<Integer> choices;

    private RemoteGameController network;
    private RemoteGameModel gameModel;

    private StartController startController;
    private SelectWindowController selectWindowController;
    private MatchController matchController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        setOnline(true);
        choices = new ArrayList<>();
        endGame = false;

        Registry registry = LocateRegistry.getRegistry();
        this.network = (RemoteGameController) registry.lookup("network");
        this.gameModel = network.getGameModel();
        UnicastRemoteObject.exportObject(this, 0);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/start.fxml"));
        Parent root = loader.load();

        startController = loader.getController();
        startController.setViewGUI(this);
        startController.init();

        primaryStage.setTitle("Sagrada");
        primaryStage.setScene(new Scene(root, 544, 635));
        primaryStage.setResizable(false);
        mainStage = primaryStage;
        primaryStage.show();
    }

    /**
     *
     * @param gameModel the gamemodel of the match
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void update(RemoteGameModel gameModel) throws RemoteException {
        this.gameModel = gameModel;
        this.run();
    }

    public void run() throws RemoteException{
        state = gameModel.getState();

        switch (state){
            case LOBBY:
                viewLobby();
                break;
            case SELECTWINDOW:
                viewSelectWindow();
                break;
            case SELECTMOVE1:
                System.out.println("ok");
                //viewSelectMove1();
                break;
            /*case ENDROUND:
                viewEndRound();
                break;
            case ENDMATCH:
                viewEndMatch();
                break;
            case SELECTMOVE2:
                viewSelectMove2();
                break;
            case PUTDICEINWINDOW:
                viewPutDiceInWindow();
                break;
            case SELECTDRAFT:
                viewSelectDraft();
                break;
            case SELECTCARD:
                viewSelectCard();
                break;
            case USETOOLCARD:
                viewUseToolCard();
                break;
            case USETOOLCARD2:
                viewUseToolCard2();
                break;
            case USETOOLCARD3:
                viewUseToolCard3();
                break;
            case ERROR:
                viewError();
                break;*/
            default:
                assert false;
        }
    }

    private void viewLobby() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    startController.print("GAMERS IN THE LOBBY:\n");
                    for(Player x: gameModel.getPlayers()){
                        startController.addPrint("- "+ x.getUsername());
                    }
                } catch (IOException e) {
                    //do nothing
                }
            }
        });
    }

    public void viewSelectWindow() throws RemoteException {
        if(firstCall) {
            firstCall = false;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        startController.changeScene(mainStage);
                    } catch (IOException e) {
                        //do nothing
                    }
                }
            });
        }else{
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(actualPlayer()) {
                            selectWindowController.setActualPlayer();
                        }
                    } catch (RemoteException e) {
                        //do nothing
                    }
                }
            });
        }
    }

    /**
     * sets if the client is online or not
     */
    @Override
    public synchronized void setOnline(boolean online){
        this.online = online;
    }

    /**
     * gets if player is online or not
     * @return true if the player is online, false otherwise
     */
    @Override
    public synchronized boolean getOnline(){
        return online;
    }

    /**
     * gets the client's username
     * @return the client's username
     */
    @Override
    public String getUser() {
        return user;
    }

    /**
     * gets choose1
     * @return first choice of the client
     */
    @Override
    public int getChoose1() {
        return choose1;
    }

    /**
     * gets choose2
     * @return second choice of the client
     */
    @Override
    public int getChoose2() {
        return choose2;
    }

    /**
     * gets if the game is ended or not
     * @return true if game is ended, false otherwise
     */
    @Override
    public boolean getEndGame() {
        return endGame;
    }

    /**
     * gets the list of inputs of the client
     * @return an arraylist of client's inputs
     */
    @Override
    public ArrayList<Integer> getChoices(){
        return choices;
    }

    /**
     * print a message
     * @param s the message to be printed
     */
    @Override
    public void print(String s){
    }

    /**
     * prints an error message
     * @param error the error message to be printed
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void printError(String error) throws RemoteException {

    }


    public void setUser(String s) throws RemoteException {
        this.user = s;
        network.update(this);
    }

    public boolean verifyUser(String s) throws RemoteException{
        for(int i=0; i<gameModel.getPlayers().size(); i++){
            if(s.equals(gameModel.getPlayers().get(i).getUsername()))
                return false;
        }
        return true;
    }

    public boolean checkLobby() throws RemoteException {
        if(gameModel.getState().equals(States.LOBBY)) {
            network.addObserver(this);
            return true;
        }else
            return false;
    }

    public boolean reconnecting() throws RemoteException {
        return gameModel.getObservers().contains(null);
    }

    public void setSelectWindowController(SelectWindowController selectWindowController){
        this.selectWindowController = selectWindowController;
    }

    public boolean actualPlayer() throws RemoteException {
        return user.equals(gameModel.getActualPlayer().getUsername());
    }

    public void setChoose1(int i) throws RemoteException {
        this.choose1 = i;
        network.update(this);
    }
}
