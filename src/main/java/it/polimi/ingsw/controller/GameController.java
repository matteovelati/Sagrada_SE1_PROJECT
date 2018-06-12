package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.RemoteView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

import static it.polimi.ingsw.model.States.*;

public class GameController extends UnicastRemoteObject implements ControllerObserver, RemoteGameController {

    private GameModel gameModel;
    private int actualPlayer, check;
    private boolean roundEnded;
    private States beforeError;
    private transient Timer t;

    public GameController() throws RemoteException{
        gameModel = GameModel.getInstance(LOBBY, 0);
        t = new Timer();
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
    public void startTimer(final RemoteView view){
        t = new Timer();
        t.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            view.setOnline(false);
                            setPlayerOnline(view.getUser(), false);
                            endTurn();
                        } catch (RemoteException e) {
                            try {
                                verifyObserver();
                                endTurn();
                            } catch (RemoteException e1) {
                                //
                            }
                        }
                    }
                }, 30000
        );
    }

    private void verifyObserver() throws RemoteException {
        for(int i=0; i<gameModel.getObservers().size(); i++){
            try{
                if(gameModel.getObservers().get(i) != null)
                    gameModel.getObservers().get(i).getUser();
            } catch(RemoteException e){
                gameModel.getPlayers().get(i).setOnline(false);
                gameModel.removeObserver(gameModel.getObservers().get(i));
            }
        }
    }

    @Override
    public void update(RemoteView view) throws RemoteException {

        verifyObserver();

        switch(gameModel.getState()){

            case LOBBY:
                lobby(view);
                break;

            case SELECTWINDOW:
                selectWindow(view);
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
                break;

            case ERROR:
                gameModel.setState(beforeError);
                break;

            default:
                break;
        }
    }


    private void lobby(RemoteView view) throws RemoteException {

        gameModel.setPlayers(new Player(view.getUser(), gameModel.getAllColors().remove(0)));
        if (gameModel.getPlayers().size() == 2) {
            startTimerLobby(t);
        }
        if(gameModel.getPlayers().size() == 4){
            t.cancel();
            gameModel.setDraft();
            gameModel.setSchemeCards();
            gameModel.setState(SELECTWINDOW);
        }
        else {
            gameModel.setState(LOBBY);
        }
    }

    private void selectWindow(RemoteView view) throws RemoteException {

        //t.cancel();
        if(view.getChoose1() > 0 && view.getChoose1() < 5) {

            gameModel.playerSetWindow(view.getChoose1());

            actualPlayer = ChangePlayer.clockwise(actualPlayer, gameModel.getPlayers().size());
            gameModel.setActualPlayer(actualPlayer);

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
            view.printError("Input error!");
            gameModel.setState(ERROR);
        }
    }

    private void selectMove1(RemoteView view) throws RemoteException{

        t.cancel();
        gameModel.getRoundManager().setFirstMove(view.getChoose1());
        check = 1;

        if(gameModel.getRoundManager().getFirstMove() == 1)
            gameModel.setState(SELECTDRAFT);

        else if(gameModel.getRoundManager().getFirstMove() == 2)
            gameModel.setState(SELECTCARD);

        else if(gameModel.getRoundManager().getFirstMove() == 0)
            endTurn();

        else
            checkError(view, view.getChoose1());

    }

    private void selectDraft(RemoteView view) throws RemoteException{

        t.cancel();
        if(view.getChoose1() > 0 && view.getChoose1() <= gameModel.getField().getDraft().getDraft().size()) {
            gameModel.playerPickDice(view.getChoose1() - 1);
            gameModel.setState(PUTDICEINWINDOW);
        }
        else
            checkError(view, view.getChoose1());

    }

    private void putDiceInWindow(RemoteView view) throws RemoteException{

        t.cancel();
        if(view.getChoose1() > 0 && view.getChoose1() < 5) {

            if(view.getChoose2() > 0 && view.getChoose2() < 6) {

                if (gameModel.playerPutDice(view.getChoose1() - 1, view.getChoose2() - 1)) {

                   if (gameModel.getRoundManager().getFirstMove() == 1)
                        gameModel.setState(SELECTMOVE2);

                    else if (gameModel.getRoundManager().getFirstMove() == 2)
                        endTurn();

                } else {
                    beforeError = gameModel.getState();
                    view.printError("Restriction error");
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

        t.cancel();
        check = 2;

        if(view.getChoose1() == 1){

            if(gameModel.getRoundManager().getFirstMove() == 1)
                gameModel.setState(SELECTCARD);

            else if(gameModel.getRoundManager().getFirstMove() == 2)
                gameModel.setState(SELECTDRAFT);

        }
        else if(view.getChoose1() == 0)
            endTurn();

        else
            checkError(view, view.getChoose1());

    }

    private void selectCard(RemoteView view) throws RemoteException{

        t.cancel();

        if (view.getChoose1() > 0 && view.getChoose1() < 4){

            if(gameModel.getField().getToolCards().get(view.getChoose1()-1).select(gameModel)) {

                if (check == 2 && gameModel.getField().getToolCards().get(view.getChoose1() - 1).getForceTurn()) {
                    view.printError("You can't use this card now, you have already put a dice.");
                    gameModel.setState(SELECTCARD);
                } else {
                    if (gameModel.playerSelectToolCardMP(view.getChoose1() - 1)) {
                        gameModel.setState(USETOOLCARD);
                    } else {
                        beforeError = gameModel.getState();
                        view.printError("You don't have enough tokens");
                        gameModel.setState(ERROR);
                    }
                }
            }
            else{
                beforeError = gameModel.getState();
                view.printError("You can't use this card.");
                gameModel.setState(ERROR);
            }
        }
        else
            checkError(view, view.getChoose1());

    }

    private void useToolCard(RemoteView view) throws RemoteException{

        t.cancel();

        if(gameModel.playerUseToolCard(view.getChoices())) {

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


    private void scoreCalculation(){

        int score;

        for(Player x : gameModel.getPlayers()){
            score = x.getPrivateObjectives().get(0).calculateScoreMP(x);
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
        }while(!gameModel.getActualPlayer().getOnline());
        return roundEnded;
    }

    private void endTurn() throws RemoteException {
        int onPlayers = 0;
        for(Player p : gameModel.getPlayers()){
            if(p.getOnline())
                onPlayers ++;
            if(onPlayers > 1)
                break;
        }
        if(onPlayers < 2){
            scoreCalculation();
            gameModel.setState(ENDMATCH);
            return;
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
            endTurn();
        else if (gameModel.getRoundManager().getFirstMove() == 2)
            gameModel.setState(SELECTMOVE2);

    }

    private void checkError(RemoteView view, int i) throws RemoteException {

        beforeError = gameModel.getState();

        if(i!=-1){
            view.printError("Input error!");
            gameModel.setState(ERROR);
        }else{
            if (check == 1) {
                if(beforeError.equals(USETOOLCARD2) || beforeError.equals(USETOOLCARD3))
                    gameModel.setState(SELECTMOVE2);
                else
                    gameModel.setState(SELECTMOVE1);
            } else if(check == 2) {
                if(beforeError.equals(USETOOLCARD2) || beforeError.equals(USETOOLCARD3))
                    endTurn();
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

    private void startTimerLobby(Timer t){
        t.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            gameModel.setDraft();
                            gameModel.setSchemeCards();
                            gameModel.setState(SELECTWINDOW);
                        } catch (RemoteException e) {
                            //DO NOTHING
                        }
                    }
                }, 30000
        );
    }


}
