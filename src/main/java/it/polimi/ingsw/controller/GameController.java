package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.cli.ViewCLI;

import java.util.ArrayList;

import static it.polimi.ingsw.model.States.*;

public class GameController implements ControllerObserver {

    private GameModel gameModel;
    private int actualPlayer;

    public GameController(){

    }


    public void setPlayers(String username){//---------------------------------------------DA FARE
        ArrayList<Player> players = new ArrayList<Player>();

        Player p = new Player(username);
        players.add(p);

        if(players.size()==4) startGame(players);
            //aspetta giocatori
    }

    private void startGame(ArrayList<Player> players){
        gameModel = new GameModel(players, SELECTWINDOW);
    }



    @Override
    public void update(ViewCLI viewCLI) {

        switch(gameModel.getState()){


            case SELECTWINDOW:

                if(viewCLI.getChoose1() > 0 && viewCLI.getChoose1() < 5) {//---------------------VERIFICA SULL'INPUT

                    if(gameModel.playerSetWindow(viewCLI.getChoose1())) {//----------------------SETTA LA WINDOW SELEZIONATA

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

                gameModel.getRoundManager().setFirstMove(viewCLI.getChoose1());

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
                        EndRound.refreshDraft(gameModel.getField().getDraft(), gameModel.getField().getRoundTrack());

                    gameModel.setState(SELECTMOVE1);

                }

                else{
                    gameModel.setState(ERROR);
                }

                break;



            case SELECTDRAFT:

                gameModel.playerPickDice(viewCLI.getChoose1());
                gameModel.setState(PUTDICEINWINDOW);

                break;



            case PUTDICEINWINDOW:

                //CONTROLLO INPUT
                if(viewCLI.getChoose1() >= 0 && viewCLI.getChoose1() <= 3 && viewCLI.getChoose2() >= 0 && viewCLI.getChoose2() <= 4) {

                    if(gameModel.playerPutDice(viewCLI.getChoose1(), viewCLI.getChoose2())) {

                        //SE LA SELEZIONE DEL DADO è LA PRIMA MOSSA PASSA ALLA SCELTA DELLA SECONDA MOSSA, ALTRIMENTI PASSA IL TURNO(STATO SELECTMOVE1 DEL PROSSIMO PLAYER)
                        if (gameModel.getRoundManager().getFirstMove() == 1)
                            gameModel.setState(SELECTMOVE2);

                        else if (gameModel.getRoundManager().getFirstMove() == 2) {

                            gameModel.getRoundManager().setFirstMove(0);
                            actualPlayer = gameModel.getRoundManager().changeActualPlayer(actualPlayer, gameModel.getPlayers().size());
                            gameModel.setActualPlayer(actualPlayer);

                            //SE è FINITO IL ROUND METTE I DADI RIMASTI NELLA ROUNDTRACK
                            if (gameModel.getRoundManager().getTurn() == 1 && gameModel.getRoundManager().getCounter() == 1)
                                EndRound.refreshDraft(gameModel.getField().getDraft(), gameModel.getField().getRoundTrack());

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

                if(viewCLI.getChoose1() == 1){

                    //SE LA PRIMA MOSSA EFFETTUATA è SELEZIONE DADO LA SECONDA SARà SELEZIONA CARTA E VICEVERSA
                    if(gameModel.getRoundManager().getFirstMove() == 1)
                        gameModel.setState(SELECTCARD);
                    else if(gameModel.getRoundManager().getFirstMove() == 2)
                        gameModel.setState(SELECTDRAFT);
                    else
                        gameModel.setState(ERROR);

                }
                else if(viewCLI.getChoose1() == 2){

                    gameModel.getRoundManager().setFirstMove(0);
                    actualPlayer = gameModel.getRoundManager().changeActualPlayer(actualPlayer, gameModel.getPlayers().size());
                    gameModel.setActualPlayer(actualPlayer);

                    if(gameModel.getRoundManager().getTurn()==1 && gameModel.getRoundManager().getCounter()==1)//---------SE è FINITO IL ROUND METTE I DADI RIMASTI NELLA ROUNDTRACK
                        EndRound.refreshDraft(gameModel.getField().getDraft(), gameModel.getField().getRoundTrack());

                    gameModel.setState(SELECTMOVE1);

                }
                else{
                    gameModel.setState(ERROR);
                }

                break;



            case SELECTCARD:

                if (viewCLI.getChoose1() > 0 && viewCLI.getChoose1() < 4){//----------VERIFICA INPUT

                    if(gameModel.playerSelectToolCard(viewCLI.getChoose1()-1)){
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

                    if(gameModel.getRoundManager().getTurn()==1 && gameModel.getRoundManager().getCounter()==1)//---------SE è FINITO IL ROUND METTE I DADI RIMASTI NELLA ROUNDTRACK
                        EndRound.refreshDraft(gameModel.getField().getDraft(), gameModel.getField().getRoundTrack());

                    gameModel.setState(SELECTMOVE1);

                }
                else if(gameModel.getRoundManager().getFirstMove() == 2)
                    gameModel.setState(SELECTMOVE2);
                else
                    gameModel.setState(ERROR);

                break;



            case ERROR:
                break;
            default:
                break;


        }
    }
}
