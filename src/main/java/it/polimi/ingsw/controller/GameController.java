package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.cli.RemoteView;
import it.polimi.ingsw.view.cli.SubjectView;
import it.polimi.ingsw.view.cli.ViewCLI;
import it.polimi.ingsw.view.cli.ViewObserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import static it.polimi.ingsw.model.States.*;

public class GameController extends UnicastRemoteObject implements ControllerObserver, RemoteGameController {

    private GameModel gameModel;
    private int actualPlayer;

    public GameController() throws RemoteException{
        gameModel = new GameModel(LOBBY);
    }

    @Override
    public void addObserver(SubjectView view){
        gameModel.addObserver((ViewObserver) view);
    }

    @Override
    public GameModel getGameModel() throws RemoteException {
        return gameModel;
    }

    @Override
    public void update(RemoteView view) throws RemoteException {

        switch(gameModel.getState()){

            case LOBBY:
                gameModel.setPlayers(new Player(view.getUser()));
                System.out.println("YOU HAVE BEEN ADDED TO THIS GAME!");
                for(Player x : gameModel.getPlayers()){
                    System.out.println(x.getUsername());
                    System.out.println(gameModel.getPlayers().size());
                }
                if(gameModel.getPlayers().size() == 2){
                    System.out.println("ARE YOU READY? THE GAME IS STARTING...");
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
                            gameModel.setState(SELECTWINDOW);
                        }
                    }else {
                        gameModel.setState(ERROR);//------setWindow NON VA A BUON FINE
                    }
                }else{
                    gameModel.setState(ERROR);//----------INPUT NON CORRETTO
                }

                break;



            case SELECTMOVE1:

                gameModel.getRoundManager().setFirstMove(view.getChoose1());

                if(gameModel.getRoundManager().getFirstMove() == 1){
                    gameModel.setState(SELECTDRAFT);//--------------------------------------PIù CHIARO CHIAMARLA SELECTDICE
                }

                else if(gameModel.getRoundManager().getFirstMove() == 2){
                    gameModel.setState(SELECTCARD);
                }

                else if(gameModel.getRoundManager().getFirstMove() == 3){//-----------------------------------------------------PASSA TURNO

                    gameModel.getRoundManager().setFirstMove(0);
                    actualPlayer = gameModel.getRoundManager().changeActualPlayer(actualPlayer, gameModel.getPlayers().size());
                    gameModel.setActualPlayer(actualPlayer);

                    if(gameModel.getRoundManager().getTurn()==1 && gameModel.getRoundManager().getCounter()==1)//---------SE è FINITO IL ROUND METTE I DADI RIMASTI NELLA ROUNDTRACK
                        gameModel.getRoundManager().endRound(gameModel.getField().getDraft(), gameModel.getField().getRoundTrack());

                    gameModel.setState(SELECTMOVE1);

                }

                else{
                    gameModel.setState(ERROR);
                }

                break;



            case SELECTDRAFT:

                gameModel.playerPickDice(view.getChoose1());
                gameModel.setState(PUTDICEINWINDOW);

                break;



            case PUTDICEINWINDOW:

                //CONTROLLO INPUT
                if(view.getChoose1() >= 0 && view.getChoose1() <= 3 && view.getChoose2() >= 0 && view.getChoose2() <= 4) {

                    if(gameModel.playerPutDice(view.getChoose1(), view.getChoose2())) {

                        //SE LA SELEZIONE DEL DADO è LA PRIMA MOSSA PASSA ALLA SCELTA DELLA SECONDA MOSSA, ALTRIMENTI PASSA IL TURNO(STATO SELECTMOVE1 DEL PROSSIMO PLAYER)
                        if (gameModel.getRoundManager().getFirstMove() == 1)
                            gameModel.setState(SELECTMOVE2);

                        else if (gameModel.getRoundManager().getFirstMove() == 2) {

                            gameModel.getRoundManager().setFirstMove(0);
                            actualPlayer = gameModel.getRoundManager().changeActualPlayer(actualPlayer, gameModel.getPlayers().size());
                            gameModel.setActualPlayer(actualPlayer);

                            //SE è FINITO IL ROUND METTE I DADI RIMASTI NELLA ROUNDTRACK
                            if (gameModel.getRoundManager().getTurn() == 1 && gameModel.getRoundManager().getCounter() == 1) {
                                gameModel.setState(ENDROUND);
                                break;
                            }

                            gameModel.setState(SELECTMOVE1);

                        } else
                            gameModel.setState(ERROR);//----------------VALORE FIRST MOVE NON RICONOSCIUTO
                    }
                    else{
                        gameModel.setState(ERROR);//---------------------RESTRIZIONE CASELLA PRESENTE
                    }
                }
                else{
                    gameModel.setState(ERROR);//---------------------------ERRORE INPUT
                }
                break;



            case SELECTMOVE2://--------------------------------------------------LA VIEW MOSTRERà UNA SOLA MOSSA POSSIBILE(1) E IL PASSATURNO(2)

                if(view.getChoose1() == 1){

                    //SE LA PRIMA MOSSA EFFETTUATA è SELEZIONE DADO LA SECONDA SARà SELEZIONA CARTA E VICEVERSA
                    if(gameModel.getRoundManager().getFirstMove() == 1)
                        gameModel.setState(SELECTCARD);
                    else if(gameModel.getRoundManager().getFirstMove() == 2)
                        gameModel.setState(SELECTDRAFT);
                    else
                        gameModel.setState(ERROR);

                }
                else if(view.getChoose1() == 2){

                    gameModel.getRoundManager().setFirstMove(0);
                    actualPlayer = gameModel.getRoundManager().changeActualPlayer(actualPlayer, gameModel.getPlayers().size());
                    gameModel.setActualPlayer(actualPlayer);

                    if(gameModel.getRoundManager().getTurn()==1 && gameModel.getRoundManager().getCounter()==1) {//---------SE è FINITO IL ROUND METTE I DADI RIMASTI NELLA ROUNDTRACK
                        gameModel.setState(ENDROUND);
                        break;
                    }

                    gameModel.setState(SELECTMOVE1);

                }
                else{
                    gameModel.setState(ERROR);
                }

                break;



            case SELECTCARD:

                if (view.getChoose1() > 0 && view.getChoose1() < 4){//----------VERIFICA INPUT

                    if(gameModel.playerSelectToolCard(view.getChoose1()-1)){
                        gameModel.setState(USETOOLCARD);
                    }else{
                        gameModel.setState(ERROR);//----------------------------------SEGNALINI FAVORE NON SUFFICIENTI
                    }
                }else{
                    gameModel.setState(ERROR);//--------------------------------------INPUT NON CORRETTO
                }

                break;



            case USETOOLCARD:

                //--------------------------------------------------------CHIAMA METODO PER L'USO DELLA TOOLCARD

                // SE L'USO DELLA TOOLCARD è LA PRIMA MOSSA PASSA ALLA SCELTA DELLA SECONDA MOSSA, ALTRIMENTI PASSA IL TURNO(STATO SELECTMOVE1 DEL PROSSIMO PLAYER)
                if(gameModel.getRoundManager().getFirstMove() == 1) {

                    gameModel.getRoundManager().setFirstMove(0);
                    actualPlayer = gameModel.getRoundManager().changeActualPlayer(actualPlayer, gameModel.getPlayers().size());
                    gameModel.setActualPlayer(actualPlayer);

                    if(gameModel.getRoundManager().getTurn()==1 && gameModel.getRoundManager().getCounter()==1) {//---------SE è FINITO IL ROUND METTE I DADI RIMASTI NELLA ROUNDTRACK
                        gameModel.setState(ENDROUND);
                        break;
                    }

                    gameModel.setState(SELECTMOVE1);

                }
                else if(gameModel.getRoundManager().getFirstMove() == 2)
                    gameModel.setState(SELECTMOVE2);
                else
                    gameModel.setState(ERROR);

                break;



            case ENDROUND:

                gameModel.getRoundManager().endRound(gameModel.getField().getDraft(), gameModel.getField().getRoundTrack());

                if(gameModel.getField().getRoundTrack().getRound() == 10)
                    gameModel.setState(SCORECALCULATION);
                else
                    gameModel.setState(SELECTMOVE1);

                break;



            case SCORECALCULATION:
                break;

            case ERROR:
                break;
            default:
                break;


        }
    }
}
