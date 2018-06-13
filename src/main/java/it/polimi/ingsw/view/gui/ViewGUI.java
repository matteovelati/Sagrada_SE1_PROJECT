package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.RemoteGameController;
import it.polimi.ingsw.model.Colors;
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
    private boolean firstCallWindow = true, firstCallMatch = true;
    private int selectedWindow;

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
        if(network.getMultiPlayerStarted()){
            gameModel = network.getGameModel();
        }
        else if(!network.getSinglePlayerStarted()) {
            network.createGameModel(this, 0);
            this.gameModel = network.getGameModel();
        }

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

    private void run() throws RemoteException{
        state = gameModel.getState();

        switch (state){
            case LOBBY:
                viewLobby();
                break;
            case SELECTWINDOW:
                viewSelectWindow();
                break;
            case SELECTMOVE1:
                viewSelectMove1();
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
        Platform.runLater(() -> {
            try {
                startController.print("GAMERS IN THE LOBBY:\n");
                for(Player x: gameModel.getPlayers()){
                    startController.addPrint("- "+ x.getUsername());
                }
            } catch (IOException e) {
                //do nothing
            }
        });
    }

    private void viewSelectWindow() {
        if(firstCallWindow) {
            firstCallWindow = false;
            Platform.runLater(() -> {
                try {
                    startController.changeScene(mainStage);
                } catch (IOException e) {
                    //do nothing
                }
            });
        }else{
            Platform.runLater(() -> {
                try {
                    if(actualPlayer()) {
                        selectWindowController.init();
                        selectWindowController.setActualPlayer();
                    }
                } catch (RemoteException e) {
                    //do nothing
                }
            });
        }
    }

    private void viewSelectMove1(){
        if(firstCallMatch){
            firstCallMatch = false;
            Platform.runLater(() -> {
                try {
                    selectWindowController.changeScene(mainStage);
                } catch (IOException e) {
                    //do nothing
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


    public void setUser(String s) {
        this.user = s;
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

    public void setMatchController(MatchController matchController){
        this.matchController = matchController;
    }

    public boolean actualPlayer() throws RemoteException {
        return user.equals(gameModel.getActualPlayer().getUsername());
    }

    public void setChoose1(int i) throws RemoteException {
        this.choose1 = i;
        network.update(this);
    }

    public int getWindowId(int i, boolean front) throws RemoteException {
        if(front)
            return gameModel.getSchemeCards().get(i).getFront().getIdNumber();
        else
            return gameModel.getSchemeCards().get(i).getBack().getIdNumber();
    }

    public int getDiceValue(int i) throws RemoteException {
        return gameModel.getField().getDraft().getDraft().get(i).getValue();
    }

    public Colors getDiceColor(int i) throws RemoteException {
        return gameModel.getField().getDraft().getDraft().get(i).getColor();
    }

    public int getToolcardId(int i) throws RemoteException {
        return gameModel.getField().getToolCards().get(i).getNumber();
    }

    public int getPublicObjId(int i) throws RemoteException {
        return gameModel.getField().getPublicObjectives().get(i).getIdNumber();
    }

    public int getSelectedWindow(){
        return this.selectedWindow;
    }

    public boolean verifyUserCrashed(String s) throws RemoteException {
        for(Player x : gameModel.getPlayers()){
            if(x.getUsername().equals(s)){
                if(x.getOnline())
                    return false;
                else{
                    for(RemoteView y : gameModel.getObservers()){
                        if(y!=null && y.getUser().equals(s))
                            return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void reAddPlayer() throws RemoteException {
        network.reAddObserver(this);
        network.setPlayerOnline(user, true);
    }

    public void refresh() throws RemoteException {
        network.update(this);
    }

    public int nPlayers() throws RemoteException {
        int onPlayers = 0;
        for (Player p : gameModel.getPlayers()){
            if (p.getOnline())
                onPlayers++;
        }
        return onPlayers;
    }

    public void setSelectedWindow(int selectedWindow) throws RemoteException {
        switch (selectedWindow){
            case 1:
                this.selectedWindow = gameModel.getSchemeCards().get(0).getFront().getIdNumber();
                break;
            case 2:
                this.selectedWindow = gameModel.getSchemeCards().get(0).getBack().getIdNumber();
                break;
            case 3:
                this.selectedWindow = gameModel.getSchemeCards().get(1).getFront().getIdNumber();
                break;
            case 4:
                this.selectedWindow = gameModel.getSchemeCards().get(1).getBack().getIdNumber();
                break;
            default:
                assert false;
        }
    }
}
