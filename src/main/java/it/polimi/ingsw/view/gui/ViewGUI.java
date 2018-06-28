package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.RemoteGameController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.RemoteView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ViewGUI extends Application implements RemoteView, Serializable {
    private Stage mainStage;
    private boolean firstCallWindow = true, firstCallMatch = true;
    private boolean selectWindowScene = false;

    private boolean singlePlayer;
    private boolean returnOnline;
    private boolean online;
    private States state;
    private String user;
    private int choose1;
    private int choose2;
    private boolean endGame;
    private ArrayList<Integer> choices;

    private RemoteGameController network;
    private RemoteGameModel gameModel;

    private boolean startTimerSocket;
    private boolean deleteConnectionSocket;
    private boolean socketConnection;
    private transient Socket socket;


    private transient StartController startController;
    private transient SelectWindowController selectWindowController;
    private transient MatchController matchController;

    @Override
    public boolean getStartTimerSocket() {
        return startTimerSocket;
    }

    @Override
    public boolean getReturnOnline(){
        return online;
    }

    @Override
    public boolean getDeleteConnectionSocket() {
        return deleteConnectionSocket;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        returnOnline = false;
        startTimerSocket = false;
        deleteConnectionSocket = false;
        setOnline(true);
        choices = new ArrayList<>();
        choose1 = 1;
        endGame = false;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/start.fxml"));
        Parent root = loader.load();

        startController = loader.getController();
        startController.setViewGUI(this);
        startController.init();

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
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
                if(actualPlayer())
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
                startController.printLobby();
                for(Player x: gameModel.getPlayers()){
                    startController.addPrint("- "+ x.getUsername());
                }
            } catch (IOException e) {
                //do nothing
            }
        });
    }

    private void viewSelectWindow() {
        Platform.runLater(()-> {
            try{
                if(firstCallWindow){
                    firstCallWindow = false;
                    selectWindowScene = true;
                    startController.changeScene(mainStage);
                }
                else{
                    if(actualPlayer()) {
                        playTimer();
                        selectWindowController.loadWindowPatterns();
                        selectWindowController.showWindowPatterns();
                    }
                }
            }catch (IOException e){
                //do nothing
            }
        });
    }

    private void viewSelectMove1(){
        Platform.runLater(()-> {
            try{
                if(firstCallMatch){
                    selectWindowScene = false;
                    if(selectWindowController == null) {
                        if(actualPlayer()) {
                            firstCallMatch = false;
                            showMatch();
                        }
                    }
                    else {
                        firstCallMatch = false;
                        selectWindowController.changeScene(mainStage);
                    }
                }
                else{
                    matchController.refresh();
                    matchController.refreshOtherPlayerWindow();
                    if(actualPlayer()) {
                        playTimer();
                        matchController.selectMove1View();
                    }
                    else {
                        matchController.waitTurn();
                    }
                }
            }catch (IOException e){
                //do nothing
            }
        });
    }

    private void viewSelectDraft(){
        Platform.runLater(() -> {
            try {
                if(matchController != null) {
                    if (actualPlayer())
                        matchController.selectDraftView();
                    else
                        matchController.waitTurn();
                }
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

    private void viewPutDiceInWindow(){
        Platform.runLater(() -> {
            try {
                if(matchController != null) {
                    if (actualPlayer())
                        matchController.putDiceInWindowView();
                    else
                        matchController.waitTurn();
                }
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

    private void viewSelectMove2(){
        Platform.runLater(() -> {
            try {
                if(matchController != null) {
                    matchController.refreshOtherPlayerWindow();
                    matchController.refresh();
                    if (actualPlayer())
                        matchController.selectMove2View();
                    else
                        matchController.waitTurn();
                }
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

    private void viewEndRound(){
        Platform.runLater(() -> {
            try {
                if(matchController != null) {
                    matchController.endRoundView();
                }
                if (actualPlayer())
                    notifyNetwork();
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

    private void viewSelectCard(){
        Platform.runLater(() -> {
            try {
                if(matchController != null) {
                    if (actualPlayer())
                        matchController.selectToolcardView();
                    else
                        matchController.waitTurn();
                }
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

    private void viewUseToolCard(){
        Platform.runLater(() -> {
            try {
                if(matchController != null) {
                    if (actualPlayer())
                        matchController.useToolcardView();
                    else
                        matchController.waitTurn();
                }
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

    private void viewUseToolCard2(){
        Platform.runLater(() -> {
            try {
                if(matchController != null) {
                    matchController.refreshOtherPlayerWindow();
                    if (actualPlayer())
                        matchController.useToolcard2View();
                    else
                        matchController.waitTurn();
                }
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

    private void viewUseToolCard3(){
        Platform.runLater(() -> {
            try {
                if(matchController != null) {
                    matchController.refreshOtherPlayerWindow();
                    if (actualPlayer())
                        matchController.useToolcard3View();
                    else
                        matchController.waitTurn();
                }
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

    private void viewEndMatch(){
        Platform.runLater(() -> {
            try {
                if(matchController != null) {
                    matchController.refreshOtherPlayerWindow();
                    matchController.endMatchView();
                }
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
        if(!online){
            if(selectWindowScene)
                Platform.runLater(() -> selectWindowController.setInactive());
            else
                Platform.runLater(() -> matchController.setInactive());
        }
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
        Platform.runLater(() -> matchController.error(error));
    }


    void setUser(String s) {
        this.user = s;
    }

    boolean verifyUsername(String s) throws RemoteException{
        for(int i=0; i<gameModel.getPlayers().size(); i++){
            if(s.equals(gameModel.getPlayers().get(i).getUsername()))
                return false;
        }
        return true;
    }

    boolean checkLobby() throws RemoteException {
        return gameModel.getState().equals(States.LOBBY);
    }

    boolean reconnecting() throws RemoteException {
        for(int i=0; i<gameModel.getObservers().size(); i++){
            if((gameModel.getObservers() == null ||gameModel.getObservers().get(i) == null) &&
                    (gameModel.getObserverSocket() == null ||gameModel.getObserverSocket().get(i)==null))
                return true;
        }
        return false;
    }

    void setSelectWindowController(SelectWindowController selectWindowController){
        this.selectWindowController = selectWindowController;
    }

    void setMatchController(MatchController matchController){
        this.matchController = matchController;
    }

    boolean actualPlayer() throws RemoteException {
        return user.equals(gameModel.getActualPlayer().getUsername());
    }

    void setChoose1(int i){
        this.choose1 = i;
    }

    void setChoose2(int i){
        this.choose2 = i;
    }

    int getWindowId(int i, boolean front) throws RemoteException {
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
        int i = player.getWindow().getIdNumber();
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
        Player p = null;
        for(int i=0; i<gameModel.getPlayers().size(); i++) {
            p = gameModel.getPlayers().get(i);
            if(p.getUsername().equals(s)) {
                return p;
            }
        }
        return p;
    }

    String getPlayerUsername(int i) throws RemoteException {
        if(gameModel.getPlayers().get(i).getUsername().equals(user))
            return "next";
        else
            return gameModel.getPlayers().get(i).getUsername();
    }

    //

    int getDraftDiceValue(int i) throws RemoteException {
        return gameModel.getField().getDraft().getDraft().get(i).getValue();
    }

    Colors getDraftDiceColor(int i) throws RemoteException {
        return gameModel.getField().getDraft().getDraft().get(i).getColor();
    }

    ArrayList<Dice> getDraft() throws RemoteException {
        return gameModel.getField().getDraft().getDraft();
    }

    int getNumberOfPlayers() throws RemoteException {
        return gameModel.getPlayers().size();
    }

    States getGameState() throws RemoteException {
        return gameModel.getState();
    }

    boolean checkDraftSize(int i) throws RemoteException {
        return i<gameModel.getField().getDraft().getDraft().size();
    }

    boolean checkWindowEmptyCell(int i, int j) throws RemoteException {
        Player player = findPlayer(user);
        return player.getWindow().getWindow()[i][j].getIsEmpty();
    }

    Colors getWindowDiceColor(int i, int j) throws RemoteException {
        Player player = findPlayer(user);
        return player.getWindow().getWindow()[i][j].getDice().getColor();
    }

    int getWindowDiceValue(int i, int j) throws RemoteException {
        Player player = findPlayer(user);
        return player.getWindow().getWindow()[i][j].getDice().getValue();
    }

    int getRound() throws RemoteException {
        return gameModel.getField().getRoundTrack().getRound();
    }

    ArrayList<Dice> getRoundtrack() throws RemoteException {
        return gameModel.getField().getRoundTrack().getGrid();
    }

    int getSelectedToolcardId() throws RemoteException {
        return gameModel.getActualPlayer().getToolCardSelected().getNumber();
    }

    boolean verifyUserCrashed(String s) throws RemoteException {
        for(Player x : gameModel.getPlayers()){
            if(x.getUsername().equals(s)){
                if(x.getOnline())
                    return false;
                else{
                    for(int i =0; i<gameModel.getObservers().size(); i++){
                        if((gameModel.getObservers()!=null && gameModel.getObservers().get(i)!=null && gameModel.getObservers().get(i).getUser().equals(s))
                                || (gameModel.getObserverSocket()!=null && gameModel.getObserverSocket().get(i)!=null && gameModel.getObserverSocket().get(i).equals(s)))
                            return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    void reAddPlayer() throws RemoteException {
        if(socketConnection){
            //
        }
        else {
            network.reAddObserver(this);
            network.setPlayerOnline(user, true);
        }
    }

    void notifyNetwork() throws RemoteException {
        if(socketConnection){
            //
        }
        else {
            network.update(this);
        }
    }

    public int getNextPlayerWindowId(int i) throws RemoteException {
        if(gameModel.getPlayers().get(i).getUsername().equals(user))
            return 0;
        else
            return gameModel.getPlayers().get(i).getWindow().getIdNumber();
    }

    boolean checkOtherPlayerWindowEmptyCell(String s, int i, int j) throws RemoteException {
        Player player = findPlayer(s);
        return player.getWindow().getWindow()[i][j].getIsEmpty();
    }

    Colors getOtherPlayerDiceColor(String s, int i, int j) throws RemoteException {
        Player player = findPlayer(s);
        return player.getWindow().getWindow()[i][j].getDice().getColor();
    }

    int getOtherPlayerDiceValue(String s, int i, int j) throws RemoteException {
        Player player = findPlayer(s);
        return player.getWindow().getWindow()[i][j].getDice().getValue();
    }

    int getPlayerScore(String s) throws RemoteException {
        Player player = findPlayer(s);
        return player.getFinalScore();
    }

    RemoteGameController getNetwork(){
        return this.network;
    }

    void setRMIConnection(String ipAddress){
        socketConnection = false;
        socket = null;
        try {
            Registry registry = LocateRegistry.getRegistry(ipAddress);
            network = (RemoteGameController) registry.lookup("network");
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            startController.printError("THIS IP ADDRESS DOES NOT EXIST");
        } catch (NotBoundException e){
            startController.printError("OPS... AN ERROR OCCURRED. PLEASE RESTART THE GAME.");
        }
    }

    void setSocketConnection(String ipAddress){
        socketConnection = true;
        try {
            socket = new Socket(ipAddress, 1337);
            ObjectInputStream ob = new ObjectInputStream(socket.getInputStream());
            network = (RemoteGameController) ob.readObject();
        } catch (IOException e) {
            startController.printError("THIS IP ADDRESS DOES NOT EXIST");
        } catch (ClassNotFoundException e) {
            //do nothing
        }
    }

    void createSinglePlayerMatch(){
        singlePlayer=true;

        if(socketConnection) {
            //
        }
        else{

        }
    }

    void createMultiPlayerMatch() throws RemoteException {
        singlePlayer=false;

        if(socketConnection) {
            //
        }
        else{
            if(network.getMultiPlayerStarted()){
                gameModel = network.getGameModel();
            }
            else if(!network.getSinglePlayerStarted()) {
                network.createGameModel( 0);
                this.gameModel = network.getGameModel();
            }
        }
    }

    @Override
    public boolean getSinglePlayer(){
        return singlePlayer;
    }

    boolean getSocketConnection(){
        return socketConnection;
    }

    void setStartTimerSocket(boolean b){
        this.startTimerSocket = b;
    }

    void matchRejoined() throws IOException {
        if(socketConnection){
            returnOnline = true;
            ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
            ob.writeObject(this);
            this.setOnline(true);
        }
        else {
            network.setPlayerOnline(user, true);
            this.setOnline(true);
        }
    }

    void playTimer() throws RemoteException {
        if(socketConnection){
            //
        }
        else{
            network.startTimer(this, null);
        }
        startTimerSocket = false;
    }

    private void showMatch(){
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/match.fxml"));
                Parent match = loader.load();

                matchController = loader.getController();
                matchController.setViewGUI(this);
                matchController.init();
                matchController.waitTurn();
                if (actualPlayer()) {
                    playTimer();
                    matchController.selectMove1View();
                }

                Scene startScene;
                startScene = new Scene(match, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
                mainStage.setScene(startScene);
                mainStage.setMaximized(true);
                mainStage.setFullScreen(true);
                mainStage.show();
                match.requestFocus();
            } catch (IOException e) {
                //do nothing
            }
        });
    }
}
