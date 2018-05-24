package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.RemoteView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static it.polimi.ingsw.model.States.*;

public class GameController extends UnicastRemoteObject implements ControllerObserver, RemoteGameController {

    private GameModel gameModel;
    private int actualPlayer, check;
    private States beforeError;


    public GameController() throws RemoteException{
        gameModel = GameModel.getInstance(LOBBY);
    }


    @Override
    public void addObserver(RemoteView view) throws RemoteException{
        gameModel.addObserver(view);
    }


    @Override
    public GameModel getGameModel() throws RemoteException {
        return gameModel;
    }


    private void scoreCalculation(){

        int score;

        for(Player players : gameModel.getPlayers()) {

            //calcolo punteggio degli obiettivi privati
            score = gameModel.getActualPlayer().getPrivateObjective().calculateScore(gameModel.getActualPlayer());

            //calcolo punteggio degli obietivi pubblici
            for (int j = 0; j < 2; j++) {
                score = score + gameModel.getField().getPublicObjectives().get(j).calculateScore(gameModel.getActualPlayer().getWindow());
            }

            gameModel.getActualPlayer().setFinalScore(score);

            actualPlayer = ChangePlayer.clockwise(actualPlayer, gameModel.getPlayers().size());//-----------CAMBIO IL PLAYER
            gameModel.setActualPlayer(actualPlayer);
        }
    }


    private void nextPlayer(){

        gameModel.getRoundManager().setFirstMove(0);
        actualPlayer = gameModel.getRoundManager().changeActualPlayer(actualPlayer, gameModel.getPlayers().size());
        gameModel.setActualPlayer(actualPlayer);

    }


    private boolean setNextState() throws RemoteException {
        // SE L'USO DELLA TOOLCARD è LA PRIMA MOSSA PASSA ALLA SCELTA DELLA SECONDA MOSSA, ALTRIMENTI PASSA IL TURNO(STATO SELECTMOVE1 DEL PROSSIMO PLAYER)
        if (gameModel.getRoundManager().getFirstMove() == 1 || gameModel.getActualPlayer().getToolCardSelected().getForceTurn()) {

            nextPlayer();

            //---------SE è FINITO IL ROUND METTE I DADI RIMASTI NELLA ROUNDTRACK
            if (gameModel.getRoundManager().getTurn() == 1 && gameModel.getRoundManager().getCounter() == 1) {
                gameModel.setState(ENDROUND);
                return false;
            }

            gameModel.setState(SELECTMOVE1);

        } else if (gameModel.getRoundManager().getFirstMove() == 2)
            gameModel.setState(SELECTMOVE2);

        return true;
    }


    private void checkError(int i) throws RemoteException {

        if(i!=-1){
            beforeError = gameModel.getState();
            System.out.println("ERRORE");
            gameModel.setState(ERROR);//ERRORE NELL'USO DELLA TOOLCARD
        }else{
            if (check == 1) {
                gameModel.setState(SELECTMOVE1);
            } else if(check == 2) {
                gameModel.setState(SELECTMOVE2);
            }
        }
    }


    @Override
    public void update(RemoteView view) throws RemoteException {

        switch(gameModel.getState()){

            case LOBBY:
                gameModel.setPlayers(new Player(view.getUser(), gameModel.getAllColors().remove(0)));
                if(gameModel.getPlayers().size() == 2){
                    gameModel.setDraft();
                    gameModel.setSchemeCards();
                    gameModel.setState(SELECTWINDOW);
                }
                else{
                    gameModel.setState(LOBBY);
                }
                break;



            case SELECTWINDOW:

                if(view.getChoose1() > 0 && view.getChoose1() < 5) {//---------------------VERIFICA SULL'INPUT

                    if(gameModel.playerSetWindow(view.getChoose1())) {//----------------------SETTA LA WINDOW SELEZIONATA

                        actualPlayer = ChangePlayer.clockwise(actualPlayer, gameModel.getPlayers().size());//-----------CAMBIO IL PLAYER
                        gameModel.setActualPlayer(actualPlayer);

                        //SE IL PLAYER HA GIà SELEZIONATO LA WINDOW PASSO ALLA SELEZIONE DELLA MOSSA ALTRIMENTI GLI FACCIO SELEZIONARE LA WINDOW
                        if (gameModel.getActualPlayer().getWindow() != null) {
                            gameModel.setState(SELECTMOVE1);
                        } else {
                            gameModel.getSchemeCards().remove(0);
                            gameModel.getSchemeCards().remove(0);
                            gameModel.setState(SELECTWINDOW);
                        }
                    }else {
                        beforeError = gameModel.getState();
                        System.out.println("SELECTWINDOW set window non va a buon fine/n");
                        gameModel.setState(ERROR);//------setWindow NON VA A BUON FINE
                    }
                }else{
                    beforeError = gameModel.getState();
                    System.out.println("SELECTWINDOW input non corretto");
                    gameModel.setState(ERROR);//----------INPUT NON CORRETTO
                }

                break;



            case SELECTMOVE1:

                gameModel.getRoundManager().setFirstMove(view.getChoose1());

                if(gameModel.getRoundManager().getFirstMove() == 1){
                    gameModel.setState(SELECTDRAFT);//--------------------------------------PIù CHIARO CHIAMARLA SELECTDICE
                }

                else if(gameModel.getRoundManager().getFirstMove() == 2){
                    check = 1;
                    gameModel.setState(SELECTCARD);
                }

                else if(gameModel.getRoundManager().getFirstMove() == 0){//-----------------------------------------------------PASSA TURNO

                    nextPlayer();

                    if(gameModel.getRoundManager().getTurn()==1 && gameModel.getRoundManager().getCounter()==1) {//---------SE è FINITO IL ROUND METTE I DADI RIMASTI NELLA ROUNDTRACK
                        gameModel.setState(ENDROUND);
                        break;
                    }

                    gameModel.setState(SELECTMOVE1);

                }

                else{
                    beforeError = gameModel.getState();
                    System.out.println("SELECTMOVE1 errore di input sulla mossa");
                    gameModel.setState(ERROR);
                }

                break;



            case SELECTDRAFT:

                gameModel.playerPickDice(view.getChoose1()-1);
                gameModel.setState(PUTDICEINWINDOW);

                break;



            case PUTDICEINWINDOW:

                //CONTROLLO INPUT
                if(view.getChoose1() > 0 && view.getChoose1() < 5 && view.getChoose2() > 0 && view.getChoose2() < 6) {

                    if(gameModel.playerPutDice(view.getChoose1()-1, view.getChoose2()-1)) {

                        //SE LA SELEZIONE DEL DADO è LA PRIMA MOSSA PASSA ALLA SCELTA DELLA SECONDA MOSSA, ALTRIMENTI PASSA IL TURNO(STATO SELECTMOVE1 DEL PROSSIMO PLAYER)
                        if (gameModel.getRoundManager().getFirstMove() == 1)
                            gameModel.setState(SELECTMOVE2);

                        else if (gameModel.getRoundManager().getFirstMove() == 2) {

                            nextPlayer();

                            //SE è FINITO IL ROUND METTE I DADI RIMASTI NELLA ROUNDTRACK
                            if (gameModel.getRoundManager().getTurn() == 1 && gameModel.getRoundManager().getCounter() == 1) {
                                gameModel.setState(ENDROUND);
                                break;
                            }

                            gameModel.setState(SELECTMOVE1);

                        } else {
                            beforeError = gameModel.getState();
                            System.out.println("PUTDICEINWINDOW valore first move non riconosciuto");
                            gameModel.setState(ERROR);//----------------VALORE FIRST MOVE NON RICONOSCIUTO
                        }
                    }
                    else{
                        beforeError = gameModel.getState();
                        System.out.println("PUTDICEINWINDOW restrizione casella presente");
                        gameModel.setState(ERROR);//---------------------RESTRIZIONE CASELLA PRESENTE
                    }
                }
                else{
                    beforeError = gameModel.getState();
                    System.out.println("PUTDICEINWINDOW errore input");
                    gameModel.setState(ERROR);//---------------------------ERRORE INPUT
                }
                break;



            case SELECTMOVE2://--------------------------------------------------LA VIEW MOSTRERà UNA SOLA MOSSA POSSIBILE(1) E IL PASSATURNO(2)

                if(view.getChoose1() == 1){

                    //SE LA PRIMA MOSSA EFFETTUATA è SELEZIONE DADO LA SECONDA SARà SELEZIONA CARTA E VICEVERSA
                    if(gameModel.getRoundManager().getFirstMove() == 1) {
                        check = 2;
                        gameModel.setState(SELECTCARD);
                    }
                    else if(gameModel.getRoundManager().getFirstMove() == 2)
                        gameModel.setState(SELECTDRAFT);
                    else {
                        beforeError = gameModel.getState();
                        System.out.println("SELECTMOVE2 valore first move non riconosciuto");
                        gameModel.setState(ERROR);
                    }

                }
                else if(view.getChoose1() == 0){

                    nextPlayer();

                    //---------SE è FINITO IL ROUND METTE I DADI RIMASTI NELLA ROUNDTRACK
                    if(gameModel.getRoundManager().getTurn()==1 && gameModel.getRoundManager().getCounter()==1) {
                        gameModel.setState(ENDROUND);
                        break;
                    }

                    gameModel.setState(SELECTMOVE1);

                }
                else{
                    beforeError = gameModel.getState();
                    System.out.println("SELECTMOVE2 errore di input");
                    gameModel.setState(ERROR);
                }

                break;



            case SELECTCARD:

                if (view.getChoose1() > 0 && view.getChoose1() < 4){//----------VERIFICA INPUT

                    if(check==2 && gameModel.getField().getToolCards().getForceTurn()){
                        gameModel.setState(SELECTCARD);
                        break;
                    }
                    else{
                        if(gameModel.playerSelectToolCard(view.getChoose1()-1)){
                            gameModel.setState(USETOOLCARD);
                        }else{
                            beforeError = gameModel.getState();
                            System.out.println("SELECTCARD segnalini favore non sufficienti/n");
                            gameModel.setState(ERROR);//----------------------------------SEGNALINI FAVORE NON SUFFICIENTI
                        }
                    }
                }
                else{
                    checkError(view.getChoose1());
                }

                break;



            case USETOOLCARD:

                if(gameModel.playerUseToolCard(view.getChoices())) {

                    //SE LA TOOLCARD CONSISTE IN UNA SOLA FASE PASSA ALLA FASE DI GIOCO SUCCESSIVA, ALTRIMENTI PASSA ALLA SECONDA FASE DELLA TOOLCARD
                    if(gameModel.getActualPlayer().getToolCardSelected().getCalls() == 1) {
                        if(!setNextState())
                            break;
                    } else{
                        gameModel.setState(USETOOLCARD2);
                    }
                }
                else {
                    checkError(view.getChoices().get(0));
                }

                break;



            case USETOOLCARD2:

                if(gameModel.playerUseToolCard(view.getChoices())) {

                    //SE LA TOOLCARD CONSISTE IN DUE FASI PASSA ALLA FASE DI GIOCO SUCCESSIVA, ALTRIMENTI PASSA ALLA TERZA FASE DELLA TOOLCARD
                    if(gameModel.getActualPlayer().getToolCardSelected().getCalls() == 2) {
                            if (!setNextState())
                                break;
                    }else{
                        gameModel.setState(USETOOLCARD3);
                    }
                }
                else {
                    beforeError = gameModel.getState();
                    System.out.println("USETOOLCARD2 errore nell'uso della toolcard");
                    gameModel.setState(ERROR);//ERRORE NELL'USO DELLA TOOLCARD
                }

                break;



            case USETOOLCARD3:

                if(gameModel.playerUseToolCard(view.getChoices())) {

                    //SE LA TOOLCARD CONSISTE IN 3 FASI PASSA ALLA FASE DI GIOCO SUCCESSIVA
                    if(gameModel.getActualPlayer().getToolCardSelected().getCalls() == 3) {
                        if(!setNextState())
                            break;
                    }
                }
                else {
                    beforeError = gameModel.getState();
                    System.out.println("USETOOLCARD 3 errore nell'uso della toolcard");
                    gameModel.setState(ERROR);//ERRORE NELL'USO DELLA TOOLCARD
                }

                break;



            case ENDROUND:

                gameModel.getRoundManager().endRound(gameModel.getField().getDraft(), gameModel.getField().getRoundTrack());

                if(gameModel.getField().getRoundTrack().getRound() == 11) {
                    scoreCalculation();
                    gameModel.setState(ENDMATCH);
                }
                else {

                    gameModel.setDraft();
                    gameModel.setState(SELECTMOVE1);
                    
                }

                break;



            case ENDMATCH:

                //display risultati
                break;



            case ERROR:

                gameModel.setState(beforeError);

                break;

            default:
                break;


        }
    }
}
