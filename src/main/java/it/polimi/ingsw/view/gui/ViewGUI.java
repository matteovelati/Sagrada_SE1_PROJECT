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

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ViewGUI extends Application implements RemoteView, Serializable {
    private transient Stage mainStage;
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
    private boolean restart;
    private int level;

    private RemoteGameController network;
    private RemoteGameModel gameModel;

    private boolean startTimerSocket;
    private boolean deleteConnectionSocket;
    private boolean socketConnection;
    private boolean blockSocketConnection;
    private transient Socket socket;


    private transient StartController startController;
    private transient SelectWindowController selectWindowController;
    private transient MatchController matchController;
    private transient SPMatchController spMatchController;

    /**
     * gets if it's needed to start the timer
     * @return true if the timer has to be started
     */
    @Override
    public boolean getStartTimerSocket() {
        return startTimerSocket;
    }

    /**
     * gets if a player has to be set online
     * @return true if the player has to be set online
     */
    @Override
    public boolean getReturnOnline(){
        return returnOnline;
    }

    /**
     * gets if the socket connection has to be blocked or not
     * @return true if the socket conncetion has to be blocked
     */
    public synchronized boolean getBlockSocketConnection() {
        return blockSocketConnection;
    }

    /**
     * sets if the socket connection has to be blocked or not
     * @param blockSocketConnection the boolean to be set
     */
    public synchronized void setBlockSocketConnection(boolean blockSocketConnection) {
        this.blockSocketConnection = blockSocketConnection;
    }

    /**
     * gets if the socket timeout connection needs be deleted or not
     * @return true if the socket connection needs to be deleted, false otherwise
     */
    @Override
    public synchronized boolean getDeleteConnectionSocket() {
        return deleteConnectionSocket;
    }

    /**
     * sets if the socket timeout connection needs be deleted or not
     * @param x the boolean to be set
     */
    public synchronized void setDeleteConnectionSocket(boolean x){
        this.deleteConnectionSocket = x;
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set. The primary stage will be embedded in
     * the browser if the application was launched as an applet.
     * Applications may create other stages, if needed, but they will not be
     * primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        returnOnline = false;
        startTimerSocket = false;
        deleteConnectionSocket = false;
        blockSocketConnection = false;
        setOnline(true);
        choices = new ArrayList<>();
        choices.add(-1);
        endGame = false;
        restart = false;

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
     * updates each view in the game
     * @param gameModel the gamemodel of the match
     */
    @Override
    public void update(RemoteGameModel gameModel) {
        this.gameModel = gameModel;
        try {
            this.run();
        } catch (IOException e) {
            //
        }
    }

    /**
     * modifies the view based on the current state
     * check if the server has been shut down
     * @throws IOException if an I/O error occurs while reading stream header
     * @throws ClassNotFoundException if class of a serialized object cannot be found
     */
    public void updateSocket() throws IOException, ClassNotFoundException {
        while(!endGame) {
            if(!getDeleteConnectionSocket() && !getBlockSocketConnection()) {
                try {
                    ObjectInputStream ob = new ObjectInputStream(socket.getInputStream());
                    this.gameModel = (RemoteGameModel) ob.readObject();
                    if (getOnline() && this.gameModel.getUpdateSocket()) {
                        if (!gameModel.getState().equals(States.LOBBY))
                            setBlockSocketConnection(true);
                        this.run();
                    }
                }catch (SocketException e){
                    Platform.runLater(()->{
                        if(selectWindowScene)
                            selectWindowController.serverDown();
                        else
                            matchController.serverDown();
                    });
                }
            }
        }
    }

    /**
     * modifies the view based on the current state
     * check if the server has been shut down
     * @throws IOException if an I/O error occurs while reading stream header
     * @throws ClassNotFoundException if class of a serialized object cannot be found
     */
    public void updateSocketSP() throws IOException, ClassNotFoundException{
        while(!endGame) {
            try {
                try {
                    ObjectInputStream ob = new ObjectInputStream(socket.getInputStream());
                    this.gameModel = (RemoteGameModel) ob.readObject();
                }catch(StreamCorruptedException e){
                    //do nothing
                }
                if (this.gameModel.getUpdateSocket()) {
                    new Thread(() -> {
                        try {
                            run();
                        } catch (IOException e) {
                            //do nothing
                        }
                    }).start();
                }
            }
            catch (SocketException e){
                Platform.runLater(()->{
                    if(selectWindowScene)
                        selectWindowController.serverDown();
                    else
                        spMatchController.serverDown();
                });
            }
        }
    }

    /**
     * modifies the view based on the current state
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void run() throws IOException {
        returnOnline = false;
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
                viewError();
                break;
            case ENDROUND:
                viewEndRound();
                break;
            case SELECTCARD:
                viewSelectCard();
                break;
            case SELECTDIE:
                viewSelectDie();
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
            case RESTART:
                viewRestart();
                break;
            default:
                assert false;
        }
    }

    /**
     * shows the players in the lobby
     */
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

    /**
     * shows the 2 schemecards (4 window)
     */
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
                    else
                        selectWindowController.waitTurn();
                }
            }catch (IOException e){
                //do nothing
            }
        });
    }

    /**
     * asks the client to select one action between:
     * pick a dice, use a toolcard, end turn
     */
    private void viewSelectMove1(){
        if(singlePlayer){
            Platform.runLater(() -> {
                try {
                    if (firstCallMatch) {
                        selectWindowScene = false;
                        firstCallMatch = false;
                        if(spMatchController == null)
                            startSinglePlayerMatch();
                        spMatchController.selectMove1View();
                    }
                    else {
                        spMatchController.refresh();
                        spMatchController.selectMove1View();
                        }
                } catch (RemoteException e) {
                    //do nothing
                }
            });
        }
        else {
            Platform.runLater(() -> {
                try {
                    if (firstCallMatch) {
                        selectWindowScene = false;
                        if (selectWindowController == null) {
                            if (actualPlayer()) {
                                firstCallMatch = false;
                                showMatch();
                            }
                            else
                                setBlockSocketConnection(false);
                        } else {
                            firstCallMatch = false;
                            selectWindowController.changeScene(mainStage);
                        }
                    } else {
                        matchController.refresh();
                        matchController.refreshOtherPlayerWindow();
                        if (actualPlayer()) {
                            playTimer();
                            matchController.selectMove1View();
                        } else {
                            matchController.waitTurn();
                        }
                    }
                } catch (IOException e) {
                    //do nothing
                }
            });
        }
    }

    /**
     * asks the client to select a die from the draft
     */
    private void viewSelectDraft(){
        if(singlePlayer){
            Platform.runLater(() -> {
                if(spMatchController == null)
                    startSinglePlayerMatch();
                spMatchController.selectDraftView(false);
            });
        }
        else {
            Platform.runLater(() -> {
                try {
                    if (matchController != null) {
                        if (actualPlayer()) {
                            if(socketConnection)
                                socketTimeOut();
                            matchController.selectDraftView();
                        }
                        else
                            matchController.waitTurn();
                    }
                    else
                        setBlockSocketConnection(false);
                } catch (RemoteException e) {
                    //do nothing
                }
            });
        }
    }

    /**
     * asks the client to select a space in the window to put the die in
     */
    private void viewPutDiceInWindow(){
        if(singlePlayer){
            Platform.runLater(() -> {
                if(spMatchController == null)
                    startSinglePlayerMatch();
                spMatchController.putDiceInWindowView();
            });
        }
        else {
            Platform.runLater(() -> {
                try {
                    if (matchController != null) {
                        if (actualPlayer()) {
                            if(socketConnection)
                                socketTimeOut();
                            matchController.putDiceInWindowView();
                        }
                        else
                            matchController.waitTurn();
                    }
                    else
                        setBlockSocketConnection(false);
                } catch (RemoteException e) {
                    //do nothing
                }
            });
        }
    }

    /**
     * asks the client to select one action between:
     * pick a dice/use a toolcard, end turn
     */
    private void viewSelectMove2(){
        if(singlePlayer){
            Platform.runLater(() -> {
                try {
                    if(spMatchController == null)
                        startSinglePlayerMatch();
                    spMatchController.refresh();
                    spMatchController.selectMove2View();
                } catch (RemoteException e) {
                    //do nothing
                }
            });
        }
        else {
            Platform.runLater(() -> {
                try {
                    if (matchController != null) {
                        matchController.refreshOtherPlayerWindow();
                        matchController.refresh();
                        if (actualPlayer()) {
                            if(socketConnection)
                                socketTimeOut();
                            matchController.selectMove2View();
                        }
                        else
                            matchController.waitTurn();
                    }
                    else
                        setBlockSocketConnection(false);
                } catch (RemoteException e) {
                    //do nothing
                }
            });
        }
    }

    /**
     * asks the client to select a toolcard
     */
    private void viewSelectCard(){
        if(singlePlayer){
            Platform.runLater(() -> {
                if(spMatchController == null)
                    startSinglePlayerMatch();
                spMatchController.selectToolcardView();
            });
        }
        else {
            Platform.runLater(() -> {
                try {
                    if (matchController != null) {
                        if (actualPlayer()) {
                            if(socketConnection)
                                socketTimeOut();
                            matchController.selectToolcardView();
                        }
                        else
                            matchController.waitTurn();
                    }
                    else
                        setBlockSocketConnection(false);
                } catch (RemoteException e) {
                    //do nothing
                }
            });
        }
    }

    /**
     * asks the client to select a die from the draft
     */
    private void viewSelectDie(){
        Platform.runLater(() -> {
            if(spMatchController == null)
                startSinglePlayerMatch();
            spMatchController.selectDraftView(true);
        });
    }

    /**
     * shows to the client the specific messages of the toolcard selected
     */
    private void viewUseToolCard(){
        if(singlePlayer){
            Platform.runLater(() -> {
                try {
                    if(spMatchController == null)
                        startSinglePlayerMatch();
                    spMatchController.useToolcardView();
                } catch (IOException e) {
                    //do nothing
                }
            });
        }
        else {
            Platform.runLater(() -> {
                try {
                    if (matchController != null) {
                        if (actualPlayer()) {
                            if(socketConnection)
                                socketTimeOut();
                            matchController.useToolcardView();
                        }
                        else
                            matchController.waitTurn();
                    }
                    else
                        setBlockSocketConnection(false);
                } catch (IOException e) {
                    //do nothing
                }
            });
        }
    }

    /**
     * shows to the client the specific messages of the toolcard selected
     */
    private void viewUseToolCard2(){
        if(singlePlayer){
            Platform.runLater(() -> {
                try {
                    if(spMatchController == null)
                        startSinglePlayerMatch();
                    spMatchController.useToolcard2View();
                } catch (RemoteException e) {
                    //do nothing
                }
            });
        }
        Platform.runLater(() -> {
            try {
                if(matchController != null) {
                    matchController.refreshOtherPlayerWindow();
                    if (actualPlayer()) {
                        if(socketConnection)
                            socketTimeOut();
                        matchController.useToolcard2View();
                    }
                    else
                        matchController.waitTurn();
                }
                else
                    setBlockSocketConnection(false);
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

    /**
     * shows to the client the specific messages of the toolcard selected
     */
    private void viewUseToolCard3(){
        if(singlePlayer){
            Platform.runLater(() -> {
                try {
                    if(spMatchController == null)
                        startSinglePlayerMatch();
                    spMatchController.useToolcard3View();
                } catch (RemoteException e) {
                    //do nothing
                }
            });
        }
        Platform.runLater(() -> {
            try {
                if(matchController != null) {
                    matchController.refreshOtherPlayerWindow();
                    if (actualPlayer()) {
                        if(socketConnection)
                            socketTimeOut();
                        matchController.useToolcard3View();
                    }
                    else
                        matchController.waitTurn();
                }
                else
                    setBlockSocketConnection(false);
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

    /**
     * shows the end of a round
     */
    private void viewEndRound(){
        if(singlePlayer){
            Platform.runLater(() -> {
                try {
                    if(spMatchController == null)
                        startSinglePlayerMatch();
                    spMatchController.endRoundView();
                    notifyNetwork();
                } catch (IOException e) {
                    //do nothing
                }
            });
        }
        else {
            Platform.runLater(() -> {
                try {
                    if (matchController != null) {
                        matchController.endRoundView();
                    }
                    setBlockSocketConnection(false);
                    if (actualPlayer()) {
                        notifyNetwork();
                    }
                } catch (IOException e) {
                    //do nothing
                }
            });
        }
    }

    /**
     * shows the final scores of each player
     */
    private void viewEndMatch(){
        if(singlePlayer){
            Platform.runLater(() -> {
                try {
                    if(spMatchController == null)
                        startSinglePlayerMatch();
                    spMatchController.endMatchView();
                } catch (IOException e) {
                    //do nothing
                }
            });
        }
        else {
            Platform.runLater(() -> {
                try {
                    if (matchController != null) {
                        setBlockSocketConnection(false);
                        matchController.refreshOtherPlayerWindow();
                        matchController.endMatchView();
                    }
                } catch (IOException e) {
                    //do nothing
                }
            });
        }
    }

    /**
     * asks the client if he wants to restart the game or not
     */
    private void viewRestart(){
        if(singlePlayer)
            Platform.runLater(() -> spMatchController.restartView());
        else
            Platform.runLater(() -> matchController.restartView());
    }

    /**
     * shows the client a message error
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewError() throws IOException {
        if(singlePlayer && spMatchController == null){
            Platform.runLater(this::startSinglePlayerMatch);
        }
        if(socketConnection) {
            setBlockSocketConnection(false);
            if (actualPlayer()) {
                if (singlePlayer)
                    Platform.runLater(() -> spMatchController.error("ERROR"));
                else
                    Platform.runLater(() -> matchController.error("ERROR"));
            }
        }
        if(actualPlayer())
            notifyNetwork();
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
        if(singlePlayer) {
            Platform.runLater(() -> {
                if(spMatchController == null)
                    startSinglePlayerMatch();
                spMatchController.error(error);
            });
        }
        else
            Platform.runLater(() -> matchController.error(error));
    }

    /**
     * sets the username of this client's view
     * @param s the name to be set
     */
    void setUser(String s) {
        this.user = s;
    }

    /**
     * checks if the username inserted already exists
     * @param s the username inserted
     * @return true if doesn't exist the same username, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    boolean verifyUsername(String s) throws RemoteException{
        for(int i=0; i<gameModel.getPlayers().size(); i++){
            if(s.equals(gameModel.getPlayers().get(i).getUsername()))
                return false;
        }
        return true;
    }

    /**
     * checks if the actual state is LOBBY
     * @return true if the actual state is LOBBY, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    boolean checkLobby() throws RemoteException {
        return gameModel.getState().equals(States.LOBBY);
    }

    /**
     * checks if the user is trying to reconnecting or not
     * @return true if the user is reconnecting, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    boolean reconnecting() throws RemoteException {
        if(singlePlayer){
            return !((!gameModel.getObservers().contains(null)) || (gameModel.getObserverSocket() != null));
        }
        else {
            for (int i = 0; i < gameModel.getObservers().size(); i++) {
                if ((gameModel.getObservers() == null || gameModel.getObservers().get(i) == null) &&
                        (gameModel.getObserverSocket() == null || gameModel.getObserverSocket().get(i) == null))
                    return true;
            }
            return false;
        }
    }

    /**
     * sets the selectWindowController of the game
     * @param selectWindowController the selectWindowController to be set
     */
    void setSelectWindowController(SelectWindowController selectWindowController){
        this.selectWindowController = selectWindowController;
    }

    /**
     * sets the matchController of the game
     * @param matchController the matchController to be set
     */
    void setMatchController(MatchController matchController){
        this.matchController = matchController;
    }

    /**
     * checks if this client is the actual player or not
     * @return true if this client is the actual player, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    boolean actualPlayer() throws RemoteException {
        return user.equals(gameModel.getActualPlayer().getUsername());
    }


    /**
     * sets first choice of the client
     * @param i the choice of the client
     */
    void setChoose1(int i){
        this.choose1 = i;
    }

    /**
     * sets the second choice of the client
     * @param i the choice of the client
     */
    void setChoose2(int i){
        this.choose2 = i;
    }

    /**
     * gets the idNumber of the window
     * @param i the index of the schemecard selected
     * @param front a boolean to know if select the front or the back of the schemecard
     * @return the idNumber of the window selected
     * @throws RemoteException if the reference could not be accessed
     */
    int getWindowId(int i, boolean front) throws RemoteException {
        if(front)
            return gameModel.getSchemeCards().get(i).getFront().getIdNumber();
        else
            return gameModel.getSchemeCards().get(i).getBack().getIdNumber();
    }

    /**
     * gets the die in the draft at index index
     * @param index the index of the die in the draft
     * @return the selected die
     * @throws RemoteException if the reference could not be accessed
     */
    String getDraftDice(int index) throws RemoteException {
        return gameModel.getField().getDraft().getDraft().get(index).getColor().toString() + gameModel.getField().getDraft().getDraft().get(index).getValue();
    }

    /**
     * gets the die in the roundtrack at index index
     * @param index the index of the die in the roundtrack
     * @return the selected die
     * @throws RemoteException if the reference could not be accessed
     */
    String getRoundtrackDice(int index) throws RemoteException{
        return gameModel.getField().getRoundTrack().getGrid().get(index).getColor().toString() + gameModel.getField().getRoundTrack().getGrid().get(index).getValue();
    }

    /**
     * gets the window of the player found
     * @param username the username of the player to be found
     * @return the window of the player found
     * @throws RemoteException if the reference could not be accessed
     */
    int getPlayerWindow(String username) throws RemoteException {
        Player player = findPlayer(username);
        return player.getWindow().getIdNumber();
    }

    /**
     * gets the idNumber of a generic card
     * @param type the kind of card to be analyzed
     * @param i the index of the card
     * @return the idNumber of the card found
     * @throws RemoteException if the reference could not be accessed
     */
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

    /**
     * gets the favor tokens of the player found
     * @param username the username of the player to be found
     * @return the number of favor tokens of the player found
     * @throws RemoteException if the reference could not be accessed
     */
    int getTokens(String username) throws RemoteException {
        Player player = findPlayer(username);
        return player.getTokens();
    }

    /**
     * finds the correspondence between the name of this view and the player in the model
     * @param s the name to be searched
     * @return the player whose name is equal to the string 's'
     * @throws RemoteException if the reference could not be accessed
     */
    private Player findPlayer(String s) throws RemoteException {
        for(int i=0; i<gameModel.getPlayers().size(); i++) {
            Player p = gameModel.getPlayers().get(i);
            if(p.getUsername().equals(s)) {
                return p;
            }
        }
        return gameModel.getPlayers().get(0);
    }

    /**
     * gets the username of the player at index i
     * @param i the index of the player
     * @return the username of the player
     * @throws RemoteException if the reference could not be accessed
     */
    String getPlayerUsername(int i) throws RemoteException {
        if(gameModel.getPlayers().get(i).getUsername().equals(user))
            return "next";
        else
            return gameModel.getPlayers().get(i).getUsername();
    }

    /**
     * gets the value of the die at index i in the draft
     * @param i the index of the die in the draft
     * @return the value of the die at index i in the draft
     * @throws RemoteException if the reference could not be accessed
     */
    int getDraftDiceValue(int i) throws RemoteException {
        return gameModel.getField().getDraft().getDraft().get(i).getValue();
    }

    /**
     * gets the color of the die at index i in the draft
     * @param i the index of the die in the draft
     * @return the color of the die at index i in the draft
     * @throws RemoteException if the reference could not be accessed
     */
    Colors getDraftDiceColor(int i) throws RemoteException {
        return gameModel.getField().getDraft().getDraft().get(i).getColor();
    }

    /**
     * gets the list of dice in the draft
     * @return the list of dice in the draft
     * @throws RemoteException if the reference could not be accessed
     */
    ArrayList<Dice> getDraft() throws RemoteException {
        return gameModel.getField().getDraft().getDraft();
    }

    /**
     * gets the number of players in the game
     * @return the number of players in the game
     * @throws RemoteException if the reference could not be accessed
     */
    int getNumberOfPlayers() throws RemoteException {
        return gameModel.getPlayers().size();
    }

    /**
     * gets the actual state of the gamemodel
     * @return the actual state of the gamemodel
     * @throws RemoteException if the reference could not be accessed
     */
    States getGameState() throws RemoteException {
        return gameModel.getState();
    }

    /**
     * checks if the size of the draft is bigger than i
     * @param i the number to check
     * @return true if the size of the draft is bigger than i, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    boolean checkDraftSize(int i) throws RemoteException {
        return i<gameModel.getField().getDraft().getDraft().size();
    }

    /**
     * checks if the space at index [i,j] of the player's window is empty or not
     * @param i the row
     * @param j the column
     * @return true if the space is empty, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    boolean checkWindowEmptyCell(int i, int j) throws RemoteException {
        Player player = findPlayer(user);
        return player.getWindow().getWindow()[i][j].getIsEmpty();
    }

    /**
     * gets the color of the space at index [i,j] of the player's window
     * @param i the row
     * @param j the column
     * @return the color of the space
     * @throws RemoteException if the reference could not be accessed
     */
    Colors getWindowDiceColor(int i, int j) throws RemoteException {
        Player player = findPlayer(user);
        return player.getWindow().getWindow()[i][j].getDice().getColor();
    }

    /**
     * gets the value of the space at index [i,j] of the player's window
     * @param i the row
     * @param j the column
     * @return the value of the space
     * @throws RemoteException if the reference could not be accessed
     */
    int getWindowDiceValue(int i, int j) throws RemoteException {
        Player player = findPlayer(user);
        return player.getWindow().getWindow()[i][j].getDice().getValue();
    }

    /**
     * gets the actual round of the game
     * @return the actual round
     * @throws RemoteException if the reference could not be accessed
     */
    int getRound() throws RemoteException {
        return gameModel.getField().getRoundTrack().getRound();
    }

    /**
     * gets the roundtrack of the game
     * @return the roundtrack of the game
     * @throws RemoteException if the reference could not be accessed
     */
    ArrayList<Dice> getRoundtrack() throws RemoteException {
        return gameModel.getField().getRoundTrack().getGrid();
    }

    /**
     * gets the idNumber of the toolcard selected
     * @return the idNumber of the toolcard selected
     * @throws RemoteException if the reference could not be accessed
     */
    int getSelectedToolcardId() throws RemoteException {
        return gameModel.getActualPlayer().getToolCardSelected().getNumber();
    }

    /**
     * verifies if some client has lost connection to the main server
     * @param s the name of the client to be verified
     * @return true if the client has lost connection, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    boolean verifyUserCrashed(String s) throws RemoteException {
        for(Player x : gameModel.getPlayers()){
            if(x.getUsername().equals(s)){
                if(x.getOnline())
                    return false;
                else{
                    if(singlePlayer){
                        for(RemoteView y : gameModel.getObservers()){
                            if(y!=null && y.getUser().equals(s))
                                return false;
                        }
                        return true;
                    }
                    else {
                        for (int i = 0; i < gameModel.getObservers().size(); i++) {
                            if ((gameModel.getObservers() != null && gameModel.getObservers().get(i) != null && gameModel.getObservers().get(i).getUser().equals(s))
                                    || (gameModel.getObserverSocket() != null && gameModel.getObserverSocket().get(i) != null && gameModel.getPlayers().get(i).getUsername().equals(s)))
                                return false;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * adds again an RMI or SOCKET observer after he has lost connection
     * @throws IOException Any exception thrown by the underlying OutputStream.
     */
    void reAddPlayer() throws IOException {
        if(socketConnection){
            ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
            ob.writeObject(user);
        }
        else {
            network.reAddObserver(this);
            network.setPlayerOnline(user, true);
        }
    }

    /**
     * based on the type of connection, it calls an update to the Server
     * @throws IOException if an I/O error occurs while reading stream header
     */
    void notifyNetwork() throws IOException {
        if(socketConnection) {
            if (singlePlayer) {
                ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
                ob.writeObject(this);
            } else {
                if (!gameModel.getState().equals(States.LOBBY) && !gameModel.getState().equals(States.ENDROUND) && !gameModel.getState().equals(States.ERROR) && !gameModel.getState().equals(States.ENDMATCH))
                    setDeleteConnectionSocket(true);
                try {
                    ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
                    ob.writeObject(this);
                }catch (SocketException e){
                    Platform.runLater(()->{
                        if(selectWindowScene)
                            selectWindowController.serverDown();
                        else
                            matchController.serverDown();
                    });
                }
            }
        }
        else
            network.update(this);
    }

    /**
     * checks if the space at index [i,j] of window of the player whose name is equal to 's' is empty or not
     * @param s the username to be searched
     * @param i the row
     * @param j the column
     * @return true if the space is empty, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    boolean checkOtherPlayerWindowEmptyCell(String s, int i, int j) throws RemoteException {
        Player player = findPlayer(s);
        return player.getWindow().getWindow()[i][j].getIsEmpty();
    }

    /**
     * checks if the player 's' is online or not
     * @param s the username to be searched
     * @return true if 's' is online, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    boolean checkOtherPlayerOnline(String s) throws RemoteException{
        Player player = findPlayer(s);
        return player.getOnline();
    }

    /**
     * checks if the player 's' is the actual player or not
     * @param s the username to be searched
     * @return true if 's' is the actual player, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    boolean checkOtherPlayerActual(String s) throws RemoteException{
        return (gameModel.getActualPlayer().getUsername().equals(s));
    }

    /**
     * gets the color of the die at index [i,j] of the player 's'
     * @param i the row
     * @param j the column
     * @return the color of the die
     * @throws RemoteException if the reference could not be accessed
     */
    Colors getOtherPlayerDiceColor(String s, int i, int j) throws RemoteException {
        Player player = findPlayer(s);
        return player.getWindow().getWindow()[i][j].getDice().getColor();
    }

    /**
     * gets the value of the die at index [i,j] of the player 's'
     * @param i the row
     * @param j the column
     * @return the value of the die
     * @throws RemoteException if the reference could not be accessed
     */
    int getOtherPlayerDiceValue(String s, int i, int j) throws RemoteException {
        Player player = findPlayer(s);
        return player.getWindow().getWindow()[i][j].getDice().getValue();
    }

    /**
     * gets the final score of the player 's'
     * @param s the username to be searched
     * @return the final score of the player found
     * @throws RemoteException if the reference could not be accessed
     */
    int getPlayerScore(String s) throws RemoteException {
        Player player = findPlayer(s);
        return player.getFinalScore();
    }

    /**
     * gets the gamecontroller of the match
     * @return the gamecontroller of the match
     */
    RemoteGameController getNetwork(){
        return this.network;
    }

    /**
     * establishes a RMI connection
     * @param ipAddress the IPaddress to connects with
     */
    void setRMIConnection(String ipAddress){
        socketConnection = false;
        socket = null;
        try {
            Registry registry = LocateRegistry.getRegistry(ipAddress);
            network = (RemoteGameController) registry.lookup("network");
            UnicastRemoteObject.exportObject(this, 0);
            verifyServerConnection();
        } catch (RemoteException e) {
            startController.printError("THIS IP ADDRESS DOES NOT EXIST");
            startController.setWrongIP(true);
        } catch (NotBoundException e){
            startController.printError("OPS... AN ERROR OCCURRED. PLEASE RESTART THE GAME.");
            startController.setWrongIP(true);
        }
    }

    /**
     * Only for RMI clients.
     * Every 2 seconds verifies if the Server is up.
     * If not, shuts down the client.
     */
    private void verifyServerConnection(){
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    network.getMultiPlayerStarted();
                    verifyServerConnection();
                }catch (RemoteException e){
                    Platform.runLater(()->{
                        if(selectWindowScene)
                            selectWindowController.serverDown();
                        else if(singlePlayer)
                            spMatchController.serverDown();
                        else
                            matchController.serverDown();
                    });
                }
            }
        },2000);
    }

    /**
     * establishes a socket connection
     * @param ipAddress the IPaddress to connects with
     */
    void setSocketConnection(String ipAddress){
        socketConnection = true;
        try {
            socket = new Socket(ipAddress, 1337);
            ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
            obj.writeObject(this);
            ObjectInputStream ob = new ObjectInputStream(socket.getInputStream());
            network = (RemoteGameController) ob.readObject();
            gameModel = network.getGameModel();
        } catch (IOException e) {
            startController.printError("THIS IP ADDRESS DOES NOT EXIST");
            startController.setWrongIP(true);
        } catch (ClassNotFoundException e) {
            startController.setWrongIP(true);
        }
    }

    /**
     * creates a singleplayer match
     * @throws RemoteException if the reference could not be accessed
     */
    void createSinglePlayerMatch() throws RemoteException {
        if(network.getSinglePlayerStarted()){
            gameModel = network.getGameModel();
        }
        else {
            network.createGameModel(level);
            this.gameModel = network.getGameModel();
        }
    }

    /**
     * creates a multiplayer match
     * @throws RemoteException if the reference could not be accessed
     */
    void createMultiPlayerMatch() throws RemoteException {
        if(network.getMultiPlayerStarted()){
            gameModel = network.getGameModel();
        }
        else if(!network.getSinglePlayerStarted()) {
            network.createGameModel( 0);
            this.gameModel = network.getGameModel();
        }
    }

    /**
     * gets if has started a singleplayer match
     * @return true if the game is in singleplayer mode
     */
    @Override
    public boolean getSinglePlayer(){
        return singlePlayer;
    }

    /**
     * sets if has started a singleplayer match
     * @param singlePlayer the boolean to be set
     */
    public void setSinglePlayer(boolean singlePlayer){
        this.singlePlayer = singlePlayer;
    }

    /**
     * gets if the client is connected with socket
     * @return true if the client is connected with socket
     */
    @Override
    public boolean getSocketConnection(){
        return socketConnection;
    }

    /**
     * allow the player to rejoin the match setting him online again
     * @throws IOException Any exception thrown by the underlying OutputStream.
     */
    void matchRejoined() throws IOException {
        if(socketConnection){
            returnOnline = true;
            ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
            ob.writeObject(this);
            this.setOnline(true);
            setBlockSocketConnection(false);
        }
        else {
            network.setPlayerOnline(user, true);
            this.setOnline(true);
        }
    }

    /**
     * based on the type of connection, starts a new timer on the server
     * @throws IOException Any exception thrown by the underlying OutputStream.
     */
    void playTimer() throws IOException {
        if(socketConnection){
            startTimerSocket = true;
            ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
            ob.writeObject(this);
            socketTimeOut();
        }
        else{
            network.startTimer(this, null);
        }
        startTimerSocket = false;
    }

    /**
     * creates a new match
     */
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

    /**
     * starts a singleplayer match
     */
    private void startSinglePlayerMatch(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/spmatch.fxml"));
            Parent match = loader.load();

            spMatchController = loader.getController();
            spMatchController.setViewGUI(this);
            spMatchController.init();

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
    }

    /**
     * gets the gamemodel of the match
     * @return the gamemodel of the match
     */
    RemoteGameModel getGameModel(){
        return this.gameModel;
    }

    /**
     * if the timer expired, set the player offline
     */
    public void socketTimeOut(){
        new Thread(() -> {
            try {
                ObjectInputStream ob = new ObjectInputStream(socket.getInputStream());
                gameModel = (RemoteGameModel) ob.readObject();
                setDeleteConnectionSocket(false);
                for(Player x : gameModel.getPlayers()){
                    if(x.getUsername().equals(user) && !x.getOnline()) {
                        setOnline(false);
                    }
                }
                setBlockSocketConnection(false);
            }catch (IOException e){
                //do nothing
            }catch (ClassNotFoundException e1){
                //
            }
        }).start();
    }

    /**
     * gets if this client wants to restart the game
     * @return true if the client wants to restart the game, false otherwise
     */
    public boolean getRestart() {
        return restart;
    }

    /**
     * sets if the client wants to restart the game
     * @param restart the boolean to be set
     */
    public void setRestart(boolean restart) {
        this.restart = restart;
    }

    /**
     * gets the level of difficulty of a the match
     * @return 0 if multiplayer, an int between 1 and 5 if singleplayer
     */
   @Override
    public int getLevel(){
        return level;
    }

    /**
     * sets the level of difficulty of a the match
     * 0 if multiplayer, an int between 1 and 5 if singleplayer
     */
    public void setLevel(int level){
        this.level = level;
    }
}
