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

    @Override
    public boolean getStartTimerSocket() {
        return startTimerSocket;
    }

    @Override
    public boolean getReturnOnline(){
        return returnOnline;
    }

    public synchronized boolean getBlockSocketConnection() {
        return blockSocketConnection;
    }

    public synchronized void setBlockSocketConnection(boolean blockSocketConnection) {
        this.blockSocketConnection = blockSocketConnection;
    }

    @Override
    public synchronized boolean getDeleteConnectionSocket() {
        return deleteConnectionSocket;
    }

    public synchronized void setDeleteConnectionSocket(boolean x){
        this.deleteConnectionSocket = x;
    }

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
        try {
            this.run();
        } catch (IOException e) {
            //
        }
    }

    public void updateSocket() throws IOException, ClassNotFoundException {
        while(!endGame) {
            if(!getDeleteConnectionSocket() && !getBlockSocketConnection()) {
                ObjectInputStream ob = new ObjectInputStream(socket.getInputStream());
                this.gameModel = (RemoteGameModel) ob.readObject();
                if(getOnline() && this.gameModel.getUpdateSocket()) {
                    if(!gameModel.getState().equals(States.LOBBY))
                        setBlockSocketConnection(true);
                        //setDeleteConnectionSocket(true);*/
                    this.run();
                }
            }
        }
    }

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
                    else
                        selectWindowController.waitTurn();
                }
            }catch (IOException e){
                //do nothing
            }
        });
    }

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
                } catch (RemoteException e) {
                    //do nothing
                }
            });
        }
    }

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
                } catch (RemoteException e) {
                    //do nothing
                }
            });
        }
    }

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
                } catch (RemoteException e) {
                    //do nothing
                }
            });
        }
    }

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
                } catch (RemoteException e) {
                    //do nothing
                }
            });
        }
    }

    private void viewSelectDie(){
        Platform.runLater(() -> {
            if(spMatchController == null)
                startSinglePlayerMatch();
            spMatchController.selectDraftView(true);
        });
    }

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
                } catch (IOException e) {
                    //do nothing
                }
            });
        }
    }

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
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

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
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

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

    private void viewEndMatch(){
        if(singlePlayer){
            Platform.runLater(() -> {
                try {
                    if(spMatchController == null)
                        startSinglePlayerMatch();
                    spMatchController.endMatchView();
                } catch (RemoteException e) {
                    //do nothing
                }
            });
        }
        Platform.runLater(() -> {
            try {
                if(matchController != null) {
                    matchController.refreshOtherPlayerWindow();
                    matchController.endMatchView();
                }
            } catch (RemoteException e) {
                //do nothing
            }
        });
    }

    private void viewError() throws IOException {
        if(singlePlayer && spMatchController == null){
            Platform.runLater(() -> startSinglePlayerMatch());
        }
        if(socketConnection)
            setBlockSocketConnection(false);
        if(actualPlayer()) {
            Platform.runLater(() -> matchController.error("ERROR"));
            notifyNetwork();
        }
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

    void notifyNetwork() throws IOException {
        if(socketConnection){
            if(!gameModel.getState().equals(States.LOBBY) && !gameModel.getState().equals(States.ENDROUND) && !gameModel.getState().equals(States.ERROR))
                setDeleteConnectionSocket(true);
            ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
            ob.writeObject(this);
        }
        else {
            if(singlePlayer)
                network.updateSP(this);
            else
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

    void createSinglePlayerMatch(int level) throws RemoteException {
        singlePlayer=true;

        if(socketConnection) {
            //
        }
        else{
            if(network.getSinglePlayerStarted()){
                gameModel = network.getGameModel();
            }
            else {
                network.createGameModel(level);
                this.gameModel = network.getGameModel();
            }
        }
    }

    void createMultiPlayerMatch() throws RemoteException {
        singlePlayer=false;

        if(socketConnection) {
            if(network.getMultiPlayerStarted()){
                gameModel = network.getGameModel();
            }
            else if(!network.getSinglePlayerStarted()){
                //
            }
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

    @Override
    public boolean getSocketConnection(){
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
            setBlockSocketConnection(false);
        }
        else {
            network.setPlayerOnline(user, true);
            this.setOnline(true);
        }
    }

    void playTimer() throws IOException {
        if(socketConnection){
            startTimerSocket = true;
            //setDeleteConnectionSocket(false);
            ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
            ob.writeObject(this);
            //setDeleteConnectionSocket(true);
            socketTimeOut();
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

    RemoteGameModel getGameModel(){
        return this.gameModel;
    }

    public void socketTimeOut(){
        new Thread(new Runnable() {
            @Override
            public void run() {
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
                    e.printStackTrace();
                }catch (ClassNotFoundException e1){
                    //
                }
            }
        }).start();
    }

}
