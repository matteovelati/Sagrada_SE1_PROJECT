package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.RemoteView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

import static it.polimi.ingsw.model.States.*;

public class GameController extends UnicastRemoteObject implements RemoteGameController {

    private GameModel gameModel;
    private int actualPlayer;
    private int check;
    private boolean roundEnded;
    private States beforeError;
    private transient Timer t;
    private transient Timer tSP;
    private boolean singlePlayerStarted;
    private boolean multiPlayerStarted;
    private boolean gameEnded;
    private transient ServerSocket serverSocket;

    /**
     * creates a GameController object which sets some booleans to the default value
     * @param serverSocket a new server socket
     * @throws RemoteException if the reference could not be accessed
     */
    public GameController(ServerSocket serverSocket) throws RemoteException{
        gameEnded = false;
        singlePlayerStarted = false;
        multiPlayerStarted = false;
        this.serverSocket = serverSocket;
    }

    /**
     * creates a new socket conncetion and adds the client to the match
     * if he's the first to join the game, it also creates the model of the match
     * @throws IOException if an I/O error occurs while reading stream header
     * @throws ClassNotFoundException if class of a serialized object cannot be found
     */
    public void addSocketConnection() throws IOException, ClassNotFoundException {
        while (true){
            Socket socket = serverSocket.accept();
            ObjectInputStream obj2 = new ObjectInputStream(socket.getInputStream());
            RemoteView view = (RemoteView) obj2.readObject();
            if(getMultiPlayerStarted()){
                ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
                ob.writeObject(this);
                if(gameModel.getState().equals(LOBBY))
                    gameModel.addObserverSocket(socket);
                else{
                    ObjectInputStream obj = new ObjectInputStream(socket.getInputStream());
                    String user = (String) obj.readObject();
                    gameModel.reAddObserverSocket(socket, user);
                    setPlayerOnline(user, true);
                }
                socketListener(socket);
            }
            else if(!getSinglePlayerStarted()){
                if (view.getSinglePlayer())
                    createGameModel(view.getLevel());
                else
                    createGameModel(0);
                gameModel.addObserverSocket(socket);
                ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
                ob.writeObject(this);
                socketListener(socket);
                if(view.getSinglePlayer())
                    startTimerSP(view);
            }
            else{
                ObjectOutputStream obj3 = new ObjectOutputStream(socket.getOutputStream());
                obj3.writeObject(this);
                try {
                    ObjectInputStream obj = new ObjectInputStream(socket.getInputStream());
                    String user = (String) obj.readObject();
                    gameModel.reAddObserverSocket(socket, user);
                    setPlayerOnline(user, true);
                    socketListener(socket);
                    updateSP(view);
                }catch (SocketException e){
                    System.out.println("A NEW PLAYER TRIED TO JOIN THE GAME WITH SOCKET CONNECTION");
                }
            }
            if(gameEnded)
                break;
        }
    }

    /**
     * keeps open a thread to communicate with the client
     * @param socket client's connection
     */
    public void socketListener(final Socket socket){
        new Thread(() -> {
            while(true){
                try {
                    ObjectInputStream obj = new ObjectInputStream(socket.getInputStream());
                    RemoteView view = (RemoteView) obj.readObject();
                    if(view.getDeleteConnectionSocket()){
                        ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
                        ob.writeObject(gameModel);
                    }
                    if(view.getStartTimerSocket())
                        startTimer(view, socket);
                    else {
                        if(view.getReturnOnline())
                            setPlayerOnline(view.getUser(), true);
                        else {
                            if(view.getSinglePlayer()){
                                updateSP(view);
                            }
                            else
                                update(view);
                        }
                    }
                } catch (SocketTimeoutException e) {
                    if (!gameModel.getState().equals(LOBBY)) {
                        int tmp = gameModel.getObserverSocket().indexOf(socket);
                        if(tmp != -1) {
                            gameModel.getPlayers().get(tmp).setOnline(false);
                            gameModel.removeObserverSocket(socket);
                            if (gameModel.getPlayers().get(tmp).getUsername().equals(gameModel.getActualPlayer().getUsername()))
                                endTurn(true);
                        }
                    }
                }
                catch (IOException e) {
                    //do nothing
                }
                catch (ClassNotFoundException e) {
                    //do nothing
                }
                if(gameEnded)
                    break;
            }

        }).start();
    }

    /**
     * gets if has been started a singleplayer match
     * @return true if a singleplayer match has been started, false otherwise
     */
    @Override
    public boolean getSinglePlayerStarted(){
        return singlePlayerStarted;
    }

    /**
     * gets if has been started a multiplayer match
     * @return true if a multiplayer match has been started, false otherwise
     */
    @Override
    public boolean getMultiPlayerStarted(){
        return multiPlayerStarted;
    }

    /**
     * finds and changes the status of the player passed
     * @param user the player who needs to change status
     * @param online the boolean to be set, true means online, false means offline
     */
    @Override
    public void setPlayerOnline(String user, boolean online){
        for(Player x : gameModel.getPlayers()){
            if(x.getUsername().equals(user)){
                x.setOnline(online);
            }
        }
    }

    /**
     * adds again an RMI observer after he has lost connection
     * @param view the observer to be added
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void reAddObserver(RemoteView view) throws RemoteException{
        gameModel.reAddObserver(view);
    }

    /**
     * adds an RMI observer at the beginning
     * @param view the observer to be added
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void addObserver(RemoteView view) {
        gameModel.addObserver(view);
    }

    /**
     * gets the gamemodel of the match
     * @return the gamemodel of the match
     */
    @Override
    public GameModel getGameModel(){
        return gameModel;
    }

    /**
     * starts the timer for the entire turn of a player
     * when expired, it checks if he has lost connection or if he's only inactive anyway setting him as offline
     * @param view the remoteview of the client who is the actualplayer
     * @param socket if not equal to null, means the client is connected with socket, otherwise in RMI
     */
    @Override
    public void startTimer(final RemoteView view, final Socket socket){
        t = new Timer();
        t.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        if(socket == null) {
                            try {
                                view.setOnline(false);
                                setPlayerOnline(view.getUser(), false);
                                verifyObserver();
                                if(gameModel.getState().equals(SELECTWINDOW))
                                    selectWindow(view, false);
                                else
                                    endTurn(false);
                            } catch (RemoteException e) {
                                try {
                                    verifyObserver();
                                    if(gameModel.getState().equals(SELECTWINDOW))
                                        selectWindow(view, false);
                                    else
                                        endTurn(false);
                                } catch (RemoteException e1) {
                                    //
                                }
                            }
                        }
                        else {
                            try {
                                setPlayerOnline(view.getUser(), false);
                                ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
                                ob.writeObject(gameModel);
                                verifyObserver();
                                if(gameModel.getState().equals(SELECTWINDOW))
                                    selectWindow(view, false);
                                else
                                    endTurn(false);
                            }catch (IOException e1){
                                try {
                                    setPlayerOnline(view.getUser(), true);
                                    verifyObserver();
                                    if(gameModel.getState().equals(SELECTWINDOW))
                                        selectWindow(view, false);
                                    else
                                        endTurn(false);
                                } catch (RemoteException e) {
                                    //do nothing
                                }
                            }
                        }
                    }
                }, 30000
        );
    }

    /**
     * starts the timer for singleplayer match
     * every 8 seconds it tries to send a ping to each client and remove them from the game if no answer is returned
     * @param view the remoteview of the client
     */
    @Override
    public void startTimerSP(final RemoteView view){
        tSP = new Timer();
        tSP.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        verifyObserver();
                        if (!singlePlayerStarted)
                            tSP.cancel();
                        else
                            startTimerSP(view);
                    }
                }, 8000
        );
    }

    /**
     * creates a gamemodel of the match
     * @param level if equal to 0 creates a multiplayer gamemodel, otherwise a singleplayer gamemodel
     */
    @Override
    public void createGameModel(int level){
        gameModel = GameModel.getInstance(level);
        if (level == 0)
            multiPlayerStarted = true;
        else
            singlePlayerStarted = true;
    }

    /**
     * tries to send a ping to each client and remove them from the game if no answer is returned
     * modifies the gamemodel and finally changes it's state based on the current state and client's actions
     * @param view the view of the actual player
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void update(RemoteView view) throws RemoteException {

        verifyObserver();

        switch(gameModel.getState()){

            case RESTART:
                break;

            case LOBBY:
                lobby(view);
                break;

            case SELECTWINDOW:
                selectWindow(view, true);
                break;

            case SELECTMOVE1:
                selectMove1(view);
                break;

            case SELECTDRAFT:
                selectDraft(view);
                break;

            case PUTDICEINWINDOW:
                putDiceInWindow(view);
                break;

            case SELECTMOVE2:
                selectMove2(view);
                break;

            case SELECTCARD:
                selectCard(view);
                break;

            case USETOOLCARD:
                useToolCard(view);
                break;

            case USETOOLCARD2:
                useToolCard2(view);
                break;

            case USETOOLCARD3:
                useToolCard3(view);
                break;

            case ENDROUND:
                endRound();
                break;

            case ENDMATCH:
                endMatch();
                break;

            case ERROR:
                gameModel.setState(beforeError);
                break;

            default:
                break;
        }
    }

    /**
     * modifies the gamemodel and finally changes it's state based on the current state and client's actions
     * @param view the view of the actual player
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void updateSP(RemoteView view) throws RemoteException{

        switch(gameModel.getState()){

            case RESTART:
                break;

            case LOBBY:
                lobby(view);
                break;

            case SELECTWINDOW:
                selectWindow(view, false);
                break;

            case SELECTMOVE1:
                selectMove1(view);
                break;

            case SELECTDRAFT:
                selectDraft(view);
                break;

            case PUTDICEINWINDOW:
                putDiceInWindow(view);
                break;

            case SELECTMOVE2:
                selectMove2(view);
                break;

            case SELECTCARD:
                selectCard(view);
                break;

            case SELECTDIE:
                selectDie(view);
                break;

            case USETOOLCARD:
                useToolCard(view);
                break;

            case USETOOLCARD2:
                useToolCard2(view);
                break;

            case USETOOLCARD3:
                tSP.cancel();
                useToolCard3(view);
                startTimerSP(view);
                break;

            case ENDROUND:
                tSP.cancel();
                endRound();
                startTimerSP(view);
                break;

            case ENDMATCH:
                endMatch();
                break;

            case ERROR:
                tSP.cancel();
                gameModel.setState(beforeError);
                startTimerSP(view);
                break;

            default:
                break;
        }
    }

    /**
     * reset every singleton class and some booleans to default values so that a new game can be started
     */
    private void endMatch(){
        Bag.reset();
        RoundTrack.reset();
        Draft.reset();
        RoundManager.reset();
        Field.reset();
        GameModel.reset();
        gameEnded = false;
        multiPlayerStarted = false;
        singlePlayerStarted = false;
        actualPlayer = 0;
        gameModel.setState(RESTART);
    }

    /**
     * initial state in which the connection of at least two players is expected
     * when it happens, a timer is started which, once it has expired, or when 4 players have joined,
     * will start the game changing the state in SELECTWINDOW
     * @param view the remoteview of the client
     * @throws RemoteException if the reference could not be accessed
     */
    private void lobby(RemoteView view) throws RemoteException {

        if (singlePlayerStarted){
            gameModel.setPlayers(new Player(view.getUser(), gameModel.getAllColors().remove(0)));
            gameModel.getPlayers().get(0).setPrivateObjectives(gameModel.getAllColors().remove(0));
            gameModel.setDraft();
            gameModel.setSchemeCards();
            gameModel.setState(SELECTWINDOW);
        }
        else {
            gameModel.setPlayers(new Player(view.getUser(), gameModel.getAllColors().remove(0)));
            if (gameModel.getPlayers().size() == 2) {
                startTimerLobby();
            }
            if (gameModel.getPlayers().size() == 4) {
                t.cancel();
                gameModel.setDraft();
                gameModel.setSchemeCards();
                gameModel.setState(SELECTWINDOW);
            } else {
                gameModel.setState(LOBBY);
            }
        }
    }

    /**
     * asks the players to select their window pattern for the match
     * then changes the state to SELECTMOVE1 if the client has chose properly
     * @param view the remoteview of the client
     * @param stopTimer a boolean to know if the timer has to be stopped or not
     * @throws RemoteException if the reference could not be accessed
     */
    private void selectWindow(RemoteView view, boolean stopTimer) throws RemoteException {

        if(stopTimer)
            t.cancel();
        int choose = 1;
        try{
            choose = view.getChoose1();
        }catch (IOException e){
            //do nothing
        }
        if(choose > 0 && choose < 5) {
            gameModel.playerSetWindow(choose);
            do {
                actualPlayer = ChangePlayer.clockwise(actualPlayer, gameModel.getPlayers().size());
                gameModel.setActualPlayer(actualPlayer);
            }while(!gameModel.getActualPlayer().getOnline());

            if (gameModel.getActualPlayer().getWindow() != null) {
                gameModel.setState(SELECTMOVE1);
            }
            else {
                gameModel.getSchemeCards().remove(0);
                gameModel.getSchemeCards().remove(0);
                gameModel.setState(SELECTWINDOW);
            }

        }else{
            beforeError = gameModel.getState();
            if (!view.getSocketConnection())
                view.printError("Input error!");
            gameModel.setState(ERROR);
        }
    }

    /**
     * asks the client if he wants to pick a dice, use a toolcard or end his turn
     * changing the state to SELECTDRAFT, SELECTCARD, or calculating next player
     * @param view the remoteview of the client
     * @throws RemoteException if the reference could not be accessed
     */
    private void selectMove1(RemoteView view) throws RemoteException{

        gameModel.getRoundManager().setFirstMove(view.getChoose1());
        check = 1;

        if(gameModel.getRoundManager().getFirstMove() == 1)
            gameModel.setState(SELECTDRAFT);
        else if(gameModel.getRoundManager().getFirstMove() == 2)
            gameModel.setState(SELECTCARD);
        else if(gameModel.getRoundManager().getFirstMove() == 0)
            endTurn(true);
        else
            checkError(view, view.getChoose1());

    }

    /**
     * asks the client to pick a dice from the draft
     * then change the state to PUTDICEINWINDOW if he has chose properly
     * @param view the remoteview of the client
     * @throws RemoteException if the reference could not be accessed
     */
    private void selectDraft(RemoteView view) throws RemoteException{

        if(view.getChoose1() > 0 && view.getChoose1() <= gameModel.getField().getDraft().getDraft().size()) {
            gameModel.playerPickDice(view.getChoose1() - 1);
            gameModel.setState(PUTDICEINWINDOW);
        }
        else
            checkError(view, view.getChoose1());

    }

    /**
     * ask the client the row and column to put the selected die
     * then change the state to SELECTMOVE2 or calculates the next player
     * @param view the remoteview of the client
     * @throws RemoteException if the reference could not be accessed
     */
    private void putDiceInWindow(RemoteView view) throws RemoteException{

        if(view.getChoose1() > 0 && view.getChoose1() < 5) {
            if(view.getChoose2() > 0 && view.getChoose2() < 6) {
                if (gameModel.playerPutDice(view.getChoose1() - 1, view.getChoose2() - 1)) {
                   if (gameModel.getRoundManager().getFirstMove() == 1)
                        gameModel.setState(SELECTMOVE2);
                    else if (gameModel.getRoundManager().getFirstMove() == 2)
                        endTurn(true);
                } else {
                    beforeError = gameModel.getState();
                    if (!view.getSocketConnection())
                        view.printError("RESTRICTION ERROR");
                    gameModel.setState(ERROR);
                }
            }
            else
                checkErrorPutDice(view, view.getChoose2());
        }
        else
            checkErrorPutDice(view, view.getChoose1());

    }

    /**
     * asks the client if he wants to pick a dice or use a toolcard or end his turn
     * changing the state to SELECTDRAFT, SELECTCARD, or calculating next player
     * @param view the remoteview of the client
     * @throws RemoteException if the reference could not be accessed
     */
    private void selectMove2(RemoteView view) throws RemoteException{

        check = 2;

        if(view.getChoose1() == 1){
            if(gameModel.getRoundManager().getFirstMove() == 1)
                gameModel.setState(SELECTCARD);

            else if(gameModel.getRoundManager().getFirstMove() == 2)
                gameModel.setState(SELECTDRAFT);
        }
        else if(view.getChoose1() == 0)
            endTurn(true);
        else
            checkError(view, view.getChoose1());

    }

    /**
     * ask the client to select a toolcard
     * verifies if he can use that card in this specific status or not
     * if he can, change the state to USETOOLCARD for multiplayer, to SELECTDIE for singleplayer
     * @param view the remoteview of the client
     * @throws RemoteException if the reference could not be accessed
     */
    private void selectCard(RemoteView view) throws RemoteException{

        if (view.getChoose1() > 0 && view.getChoose1() <= gameModel.getField().getToolCards().size()){

            if(gameModel.getField().getToolCards().get(view.getChoose1()-1).select(gameModel)) {

                if (check == 2 && gameModel.getField().getToolCards().get(view.getChoose1() - 1).getForceTurn()) {
                    if (!view.getSocketConnection())
                        view.printError("You can't use this card now, you have already put a dice.");
                    gameModel.setState(SELECTCARD);
                } else {
                    if (multiPlayerStarted && gameModel.playerSelectToolCardMP(view.getChoose1() - 1))
                        gameModel.setState(USETOOLCARD);
                    else if (singlePlayerStarted && gameModel.playerSelectToolCardSP(view.getChoose1() - 1))
                        gameModel.setState(SELECTDIE);
                    else {
                        beforeError = gameModel.getState();
                        if (multiPlayerStarted) {
                            if (!view.getSocketConnection())
                                view.printError("You don't have enough tokens");
                        }
                        else {
                            if (!view.getSocketConnection())
                                view.printError("You already used this card or there are no dice in the draft \n" +
                                    "that matches the color of the ToolCard. Try again later");
                            gameModel.setState(ERROR);
                        }
                    }
                }
            }
            else{
                beforeError = gameModel.getState();
                if (!view.getSocketConnection())
                    view.printError("You can't use this card.");
                gameModel.setState(ERROR);
            }
        }
        else
            checkError(view, view.getChoose1());

    }

    /**
     * (only singleplayer)
     * asks the client to remove a die of the same color as the card
     * then change the state to USETOOLCARD
     * @param view the remoteview of the client
     * @throws RemoteException if the reference could not be accessed
     */
    private void selectDie(RemoteView view) throws RemoteException{

        if (view.getChoose1() > 0 && view.getChoose1() <= gameModel.getField().getDraft().getDraft().size()){
            if (gameModel.getField().getDraft().getDraft().get(view.getChoose1()-1).getColor().equals(gameModel.getActualPlayer().getToolCardSelected().getColor())) {
                gameModel.getField().getDraft().extract(view.getChoose1()-1);
                gameModel.setState(USETOOLCARD);
            }
            else {
                beforeError = gameModel.getState();
                gameModel.setState(ERROR);
            }
        }
        else
            checkError(view, view.getChoose1());
    }

    /**
     * asks the client some inputs to use the selected toolcard
     * verifies if it's necessary to asks other inputs and changes to USETOOLCARD2
     * otherwise changes the state to SELECTMOVE2 or calculates the next player
     * @param view the remoteview of the client
     * @throws RemoteException if the reference could not be accessed
     */
    private void useToolCard(RemoteView view) throws RemoteException{

        if(gameModel.playerUseToolCard(view.getChoices())) {
            if (singlePlayerStarted)
                gameModel.getActualPlayer().getToolCardSelected().setIsUsed(true);
            if (multiPlayerStarted)
                gameModel.decreaseToken();
            if(gameModel.getActualPlayer().getToolCardSelected().getCalls() == 1)
                setNextState();
            else
                gameModel.setState(USETOOLCARD2);
        }
        else
            checkError(view, view.getChoices().get(0));
    }

    /**
     * asks the client some inputs to use the selected toolcard
     * verifies if it's necessary to asks other inputs and changes to USETOOLCARD3
     * otherwise changes the state to SELECTMOVE2 or calculates the next player
     * @param view the remoteview of the client
     * @throws RemoteException if the reference could not be accessed
     */
    private void useToolCard2(RemoteView view) throws RemoteException{

        if(gameModel.playerUseToolCard(view.getChoices())) {
            if(gameModel.getActualPlayer().getToolCardSelected().getCalls() == 2)
                setNextState();
            else
                gameModel.setState(USETOOLCARD3);
        }
        else
            checkError(view, view.getChoices().get(0));

    }

    /**
     * asks the client some inputs to use the selected toolcard
     * changes the state to SELECTMOVE2 or calculates the next player
     * @param view the remoteview of the client
     * @throws RemoteException if the reference could not be accessed
     */
    private void useToolCard3(RemoteView view) throws RemoteException{

        if(gameModel.playerUseToolCard(view.getChoices()))
            setNextState();
        else
            checkError(view, view.getChoices().get(0));

    }

    /**
     * tries to ping each client connected to the server
     * if it does not go well, sets the player offline and removes the observer (setting him as null)
     */
    private void verifyObserver() {

        for(int i=0; i<gameModel.getObservers().size(); i++){
            try{
                if(gameModel.getObservers().get(i) != null)
                    gameModel.getObservers().get(i).getUser();
            } catch(RemoteException e){
                gameModel.getPlayers().get(i).setOnline(false);
                gameModel.removeObserver(gameModel.getObservers().get(i));
            }
            try{
                if(gameModel.getObserverSocket().get(i)!= null) {
                    gameModel.setUpdateSocket(false);
                    ObjectOutputStream ob = new ObjectOutputStream(gameModel.getObserverSocket().get(i).getOutputStream());
                    ob.writeObject(gameModel);
                }
            } catch (IOException e){
                gameModel.getPlayers().get(i).setOnline(false);
                gameModel.removeObserverSocket(gameModel.getObserverSocket().get(i));
            }
        }
        gameModel.setUpdateSocket(true);
    }

    /**
     * calculates the final score for each player
     * sums the private objective score with the public objectives scores
     */
    private void scoreCalculation(){

        int score;

        for(Player x : gameModel.getPlayers()){
            if (multiPlayerStarted)
                score = x.getPrivateObjectives().get(0).calculateScoreMP(x);
            else
                score = x.getPrivateObjectives().get(0).calculateScoreSP(x);
            for(PublicObjective po : gameModel.getField().getPublicObjectives())
                score += po.calculateScore(x.getWindow());
            x.setFinalScore(score);
        }
    }

    /**
     * calculates the next player based on the actual state, the turn and the round
     * @return true if the round is ended, false otherwise
     */
    private boolean nextPlayer(){
        do {
            actualPlayer = gameModel.nextPlayer(actualPlayer);
            gameModel.setActualPlayer(actualPlayer);
            if(gameModel.getRoundManager().getTurn()==1 && gameModel.getRoundManager().getCounter()==1)
                roundEnded = true;
        }while(!gameModel.getActualPlayer().getOnline() || gameModel.getActualPlayer().getSkipNextTurn());
        return roundEnded;
    }

    /**
     * checks if there are at least 2 player online: if not, changes the state to ENDMATCH
     * if the round is ended, sets the state ENDROUND,
     * otherwise calculates the next player change the state to SELECTMOVE1
     * @param stopTimer a boolean to know if it's needed to stop the timer or not
     */
    private void endTurn(boolean stopTimer) {
        if(stopTimer && multiPlayerStarted)
            t.cancel();
        if (multiPlayerStarted) {
            int onPlayers = 0;
            for (Player p : gameModel.getPlayers()) {
                if (p.getOnline())
                    onPlayers++;
                if (onPlayers > 1)
                    break;
            }
            if (onPlayers < 2) {
                scoreCalculation();
                gameModel.setState(ENDMATCH);
                return;
            }
        }

        if(nextPlayer()){
            roundEnded = false;
            gameModel.setState(ENDROUND);
        }
        else
            gameModel.setState(SELECTMOVE1);

    }

    /**
     * if the actual round is equal to 11, calculates the score of each player and change the state to ENDMATCH
     * otherwise change the state to SELECTMOVE1
     */
    private void endRound() {

        gameModel.endRound();

        if(gameModel.getField().getRoundTrack().getRound() == 11) {
            scoreCalculation();
            gameModel.setState(ENDMATCH);
        }
        else {
            gameModel.setDraft();
            gameModel.setState(SELECTMOVE1);
        }

    }

    /**
     * calculates and change the state based on the current one
     */
    private void setNextState() {

        if (gameModel.getRoundManager().getFirstMove() == 1 || gameModel.getActualPlayer().getToolCardSelected().getForceTurn())
            endTurn(true);
        else if (gameModel.getRoundManager().getFirstMove() == 2)
            gameModel.setState(SELECTMOVE2);

    }

    /**
     * calculates and change the next state based on the current one (beforeError)
     * @param view the remoteview of the client
     * @param i if equal to -1 means the client decided to stop using a toolcard
     * @throws RemoteException if the reference could not be accessed
     */
    private void checkError(RemoteView view, int i) throws RemoteException {

        beforeError = gameModel.getState();

        if(i!=-1){
            if (!view.getSocketConnection())
                view.printError("Input error!");
            gameModel.setState(ERROR);
        }else{
            if (check == 1) {
                if(beforeError.equals(USETOOLCARD2) || beforeError.equals(USETOOLCARD3))
                    gameModel.setState(SELECTMOVE2);
                else {
                    if (multiPlayerStarted)
                        t.cancel();
                    gameModel.setState(SELECTMOVE1);
                }
            } else if(check == 2) {
                if(beforeError.equals(USETOOLCARD2) || beforeError.equals(USETOOLCARD3))
                    endTurn(true);
                else
                    gameModel.setState(SELECTMOVE2);
            }
        }
    }

    /**
     * if an error occurred, adds the picked die of the player to the draft
     * @param view the remoteview of the client
     * @param i if equal to -1 means the client decided to stop
     * @throws RemoteException if the reference could not be accessed
     */
    private void checkErrorPutDice(RemoteView view, int i) throws RemoteException {
        if(i == -1)
            gameModel.putDiceInDraft();
        checkError(view, i);
    }

    /**
     * starts a timer when the state is LOBBY
     * when expired, set the new state to SELECTWINDOW
     */
    private void startTimerLobby(){
        t = new Timer();
            t.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            verifyObserver();
                            gameModel.setDraft();
                            gameModel.setSchemeCards();
                            gameModel.setState(SELECTWINDOW);
                        }
                    }, 20000
            );
    }
}
