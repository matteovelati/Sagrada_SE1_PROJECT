package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.RemoteGameController;
import it.polimi.ingsw.model.*;
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

    private boolean online;
    private States state;
    private String user;
    private int choose1;
    private int choose2;
    private boolean endGame;
    private ArrayList<Integer> choices;

    private RemoteGameController network;
    private RemoteGameModel gameModel;

    private transient StartController startController;
    private transient SelectWindowController selectWindowController;
    private transient MatchController matchController;

    @Override
    public boolean getStartTimerSocket() {
        return false;
    }

    @Override
    public boolean getReturnOnline(){
        return false;
    }

    @Override
    public boolean getDeleteConnectionSocket() {
        return false;
    }

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
            network.createGameModel( 0);
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
        root.requestFocus();
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
            case SELECTDRAFT:
                viewSelectDraft();
                break;
            case PUTDICEINWINDOW:
                viewPutDiceInWindow();
                break;
            case SELECTMOVE2:
                viewSelectMove2();
                break;
            case ERROR:
                network.update(this);
                break;
            case ENDROUND:
                viewEndRound();
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
            case ENDMATCH:
                viewEndMatch();
                break;
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
                        selectWindowController.loadWindowPatterns();
                        selectWindowController.showWindowPatterns();
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
        }else{
            Platform.runLater(() -> {
                try {
                    matchController.refresh();
                    matchController.refreshOtherPlayerWindow();
                    if(actualPlayer()) {
                        matchController.selectMove1View();
                    }
                    else {
                        matchController.waitTurn();
                    }
                } catch (RemoteException e) {
                    //do nothing
                }

            });
        }
    }

    private void viewSelectDraft(){
        Platform.runLater(() -> {
            try {
                if(actualPlayer())
                    matchController.selectDraftView();
                else
                    matchController.waitTurn();
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

    private void viewPutDiceInWindow(){
        Platform.runLater(() -> {
            try {
                if(actualPlayer())
                    matchController.putDiceInWindowView();
                else
                    matchController.waitTurn();
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

    private void viewSelectMove2(){
        Platform.runLater(() -> {
            try {
                matchController.refreshOtherPlayerWindow();
                matchController.refresh();
                if(actualPlayer())
                    matchController.selectMove2View();
                else
                    matchController.waitTurn();
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

    private void viewEndRound(){
        Platform.runLater(() -> {
            try {
                matchController.endRoundView();
                if(actualPlayer())
                    notifyNetwork();
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

    private void viewSelectCard(){
        Platform.runLater(() -> {
            try {
                if(actualPlayer())
                    matchController.selectToolcardView();
                else
                    matchController.waitTurn();
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

    private void viewUseToolCard(){
        Platform.runLater(() -> {
            try {
                if(actualPlayer())
                    matchController.useToolcardView();
                else
                    matchController.waitTurn();
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

    private void viewUseToolCard2(){
        Platform.runLater(() -> {
            try {
                matchController.refreshOtherPlayerWindow();
                if(actualPlayer())
                    matchController.useToolcard2View();
                else
                    matchController.waitTurn();
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

    private void viewUseToolCard3(){
        Platform.runLater(() -> {
            try {
                matchController.refreshOtherPlayerWindow();
                if(actualPlayer())
                    matchController.useToolcard3View();
                else
                    matchController.waitTurn();
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

    private void viewEndMatch(){
        Platform.runLater(() -> {
            try {
                matchController.refreshOtherPlayerWindow();
                matchController.endMatchView();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
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
        Platform.runLater(() ->{
            matchController.error(error);
        });
    }


    public void setUser(String s) {
        this.user = s;
    }

    public boolean verifyUsername(String s) throws RemoteException{
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

    public void setChoose1(int i){
        this.choose1 = i;
    }

    public void setChoose2(int i){
        this.choose2 = i;
    }

    public int getWindowId(int i, boolean front) throws RemoteException {
        if(front)
            return gameModel.getSchemeCards().get(i).getFront().getIdNumber();
        else
            return gameModel.getSchemeCards().get(i).getBack().getIdNumber();
    }

    String getDraftDice(int index) throws RemoteException {
        return gameModel.getField().getDraft().getDraft().get(index).getColor().toString() + gameModel.getField().getDraft().getDraft().get(index).getValue();
    }

    int getPlayerWindow(String username) throws RemoteException {
        Player player = findPlayer(username);
        return player.getWindow().getIdNumber();
    }

    String getCardId(String type, int i) throws RemoteException{
        String id = null;

        if(type.equals("toolcards"))
            id = String.valueOf(gameModel.getField().getToolCards().get(i).getNumber());
        else if(type.equals("public_obj"))
            id = String.valueOf(gameModel.getField().getPublicObjectives().get(i).getIdNumber());
        else if(type.equals("private_obj")) {
            Player p = findPlayer(user);
            id = p.getPrivateObjectives().get(i).getColor().toString();
        }

        return id;
    }

    int getTokens(String username) throws RemoteException {
        Player player = findPlayer(username);
        return player.getTokens();
    }

    private Player findPlayer(String s) throws RemoteException {
        for(Player p : gameModel.getPlayers()) {
            if(p.getUsername().equals(s)) {
                return p;
            }
        }
        return null;
    }

    public String getPlayerUsername(int i) throws RemoteException {
        if(gameModel.getPlayers().get(i).getUsername().equals(user))
            return "next";
        else
            return gameModel.getPlayers().get(i).getUsername();
    }

    //

    public int getDraftDiceValue(int i) throws RemoteException {
        return gameModel.getField().getDraft().getDraft().get(i).getValue();
    }

    public Colors getDraftDiceColor(int i) throws RemoteException {
        return gameModel.getField().getDraft().getDraft().get(i).getColor();
    }

    public ArrayList<Dice> getDraft() throws RemoteException {
        return gameModel.getField().getDraft().getDraft();
    }

    public int getNumberOfPlayers() throws RemoteException {
        return gameModel.getPlayers().size();
    }

    public States getGameState() throws RemoteException {
        return gameModel.getState();
    }

    public boolean checkDraftSize(int i) throws RemoteException {
        return i<gameModel.getField().getDraft().getDraft().size();
    }

    public boolean checkWindowEmptyCell(int i, int j) throws RemoteException {
        Player player = findPlayer(user);
        return player.getWindow().getWindow()[i][j].getIsEmpty();
    }

    public Colors getWindowDiceColor(int i, int j) throws RemoteException {
        Player player = findPlayer(user);
        return player.getWindow().getWindow()[i][j].getDice().getColor();
    }

    public int getWindowDiceValue(int i, int j) throws RemoteException {
        Player player = findPlayer(user);
        return player.getWindow().getWindow()[i][j].getDice().getValue();
    }

    public int getRound() throws RemoteException {
        return gameModel.getField().getRoundTrack().getRound();
    }

    public ArrayList<Dice> getRoundtrack() throws RemoteException {
        return gameModel.getField().getRoundTrack().getGrid();
    }

    public int getSelectedToolcardId() throws RemoteException {
        return gameModel.getActualPlayer().getToolCardSelected().getNumber();
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

    public void notifyNetwork() throws RemoteException {
        network.update(this);
    }

    public int getNextPlayerWindowId(int i) throws RemoteException {
        if(gameModel.getPlayers().get(i).getUsername().equals(user))
            return 0;
        else
            return gameModel.getPlayers().get(i).getWindow().getIdNumber();
    }

    public boolean checkOtherPlayerWindowEmptyCell(String s, int i, int j) throws RemoteException {
        Player player = findPlayer(s);
        return player.getWindow().getWindow()[i][j].getIsEmpty();
    }

    public Colors getOtherPlayerDiceColor(String s, int i, int j) throws RemoteException {
        Player player = findPlayer(s);
        return player.getWindow().getWindow()[i][j].getDice().getColor();
    }

    public int getOtherPlayerDiceValue(String s, int i, int j) throws RemoteException {
        Player player = findPlayer(s);
        return player.getWindow().getWindow()[i][j].getDice().getValue();
    }

    public int getPlayerScore(String s) throws RemoteException {
        Player player = findPlayer(s);
        return player.getFinalScore();
    }
}
