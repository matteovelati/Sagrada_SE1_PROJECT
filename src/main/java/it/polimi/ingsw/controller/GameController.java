package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.RemoteView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
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

    public GameController(ServerSocket serverSocket) throws RemoteException{
        gameEnded = false;
        singlePlayerStarted = false;
        multiPlayerStarted = false;
        this.serverSocket = serverSocket;
    }

    public void addSocketConnection() throws IOException, ClassNotFoundException {
        while (true){
            Socket socket = serverSocket.accept();
            //ObjectInputStream obj2 = new ObjectInputStream(socket.getInputStream());
            //RemoteView view = (RemoteView) obj2.readObject();
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
            }
            else if(!getSinglePlayerStarted()){
                createGameModel( 0);
                gameModel.addObserverSocket(socket);
                ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
                ob.writeObject(this);
            }
            socketListener(socket);
            if(gameEnded)
                break;
        }
    }

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
                        else
                            update(view);
                    }
                } catch (SocketTimeoutException e) {
                    if (!gameModel.getState().equals(LOBBY)) {
                        int tmp = gameModel.getObserverSocket().indexOf(socket);
                        gameModel.getPlayers().get(tmp).setOnline(false);
                        gameModel.removeObserverSocket(socket);
                        try {
                            if (gameModel.getPlayers().get(tmp).getUsername().equals(gameModel.getActualPlayer().getUsername()))
                                endTurn(true);
                        } catch (RemoteException e1) {
                            //do nothing
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

    @Override
    public boolean getSinglePlayerStarted(){
        return singlePlayerStarted;
    }

    @Override
    public boolean getMultiPlayerStarted(){
        return multiPlayerStarted;
    }

    @Override
    public void setPlayerOnline(String user, boolean online){
        for(Player x : gameModel.getPlayers()){
            if(x.getUsername().equals(user)){
                x.setOnline(online);
            }
        }
    }

    @Override
    public void reAddObserver(RemoteView view) throws RemoteException{
        gameModel.reAddObserver(view);
    }

    @Override
    public void addObserver(RemoteView view) throws RemoteException{
        gameModel.addObserver(view);
    }

    @Override
    public GameModel getGameModel() throws RemoteException {
        return gameModel;
    }

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

    @Override
    public void startTimerSP(final RemoteView view){
        tSP = new Timer();
        tSP.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            verifyObserver();
                            if (!singlePlayerStarted)
                                tSP.cancel();
                            else
                                startTimerSP(view);
                        } catch (RemoteException e) {
                            //
                        }
                    }
                }, 3000
        );
    }

    @Override
    public void createGameModel(int level) throws RemoteException{
        gameModel = GameModel.getInstance(level);
        if (level == 0)
            multiPlayerStarted = true;
        else
            singlePlayerStarted = true;
    }

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

    private void selectDraft(RemoteView view) throws RemoteException{


        if(view.getChoose1() > 0 && view.getChoose1() <= gameModel.getField().getDraft().getDraft().size()) {
            gameModel.playerPickDice(view.getChoose1() - 1);
            gameModel.setState(PUTDICEINWINDOW);
        }
        else
            checkError(view, view.getChoose1());

    }

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

    private void useToolCard3(RemoteView view) throws RemoteException{

        if(gameModel.playerUseToolCard(view.getChoices()))
            setNextState();
        else
            checkError(view, view.getChoices().get(0));

    }

    private void verifyObserver() throws RemoteException {
        gameModel.setUpdateSocket(false);
        for(int i=0; i<gameModel.getObservers().size(); i++){
            try{
                if(gameModel.getObservers().get(i) != null)
                    gameModel.getObservers().get(i).getUser();
            } catch(RemoteException e){
                gameModel.getPlayers().get(i).setOnline(false);
                gameModel.removeObserver(gameModel.getObservers().get(i));
                if (singlePlayerStarted)
                    tSP.cancel();
            }
            try{
                if(gameModel.getObserverSocket().get(i)!= null) {
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

    private boolean nextPlayer(){
        do {
            actualPlayer = gameModel.nextPlayer(actualPlayer);
            gameModel.setActualPlayer(actualPlayer);
            if(gameModel.getRoundManager().getTurn()==1 && gameModel.getRoundManager().getCounter()==1)
                roundEnded = true;
        }while(!gameModel.getActualPlayer().getOnline() || gameModel.getActualPlayer().getSkipNextTurn());
        return roundEnded;
    }

    private void endTurn(boolean stopTimer) throws RemoteException {
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

    private void endRound() throws RemoteException {

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

    private void setNextState() throws RemoteException {

        if (gameModel.getRoundManager().getFirstMove() == 1 || gameModel.getActualPlayer().getToolCardSelected().getForceTurn())
            endTurn(true);
        else if (gameModel.getRoundManager().getFirstMove() == 2)
            gameModel.setState(SELECTMOVE2);

    }

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

    private void checkErrorPutDice(RemoteView view, int i) throws RemoteException {
        if(i == -1)
            gameModel.putDiceInDraft();
        checkError(view, i);
    }

    private void startTimerLobby(){
        t = new Timer();
            t.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                verifyObserver();
                                gameModel.setDraft();
                                gameModel.setSchemeCards();
                                gameModel.setState(SELECTWINDOW);
                            } catch (RemoteException e) {
                                //DO NOTHING
                            }
                        }
                    }, 20000
            );
    }
}
