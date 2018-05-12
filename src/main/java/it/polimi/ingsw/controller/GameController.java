package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.cli.ViewCLI;

import java.util.ArrayList;

import static it.polimi.ingsw.model.States.*;

public class GameController implements ControllerObserver {

    private GameModel gameModel;
    private int actualPlayer;
    private int turn;
    private int counter;
    private int firstMove;//----------------------------MEMORIZZA LA PRIMA MOSSA FATTA DA UN GIOCATORE(1:SELEZIONA DADO 2:SCEGLI TOOLCARD)

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
        turn = 1;
        counter = 1;
        actualPlayer = 0;
    }

    private void changeActualPlayer(){

        switch(turn) {

            case 1:

                if (counter < gameModel.getPlayers().size()) {

                    actualPlayer = ChangePlayer.clockwise(actualPlayer, gameModel.getPlayers().size());
                    counter++;

                } else if (counter == gameModel.getPlayers().size()) {
                    counter --;
                    turn = 2;
                }

                break;

            case 2:

                if(counter > 0){

                    counter--;
                    actualPlayer = ChangePlayer.antiClockwise(actualPlayer, gameModel.getPlayers().size());

                }else if(counter == 0){

                    counter = 1;
                    turn = 1;
                    actualPlayer = ChangePlayer.clockwise(actualPlayer, gameModel.getPlayers().size());
                    EndRound.refreshDraft(gameModel.getField().getDraft(), gameModel.getField().getRoundTrack());

                }
                break;

            default:
                gameModel.setState(ERROR);//---------------------------------------------------------------------ERRORE GESTIONE TURNO
        }

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

                firstMove = viewCLI.getChoose1();

                if(firstMove == 1){
                    gameModel.setState(SELECTDRAFT);//--------------------------------------PIù CHIARO CHIAMARLA SELECTDICE
                }
                else if(firstMove == 2){
                    gameModel.setState(SELECTCARD);
                }
                else if(firstMove == 3){//-----------------------------------------------------PASSA TURNO
                    firstMove = 0;
                    changeActualPlayer();
                    gameModel.setActualPlayer(actualPlayer);
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

                gameModel.playerPutDice(viewCLI.getChoose1(), viewCLI.getChoose2());

                //SE LA SELEZIONE DEL DADO è LA PRIMA MOSSA PASSA ALLA SCELTA DELLA SECONDA MOSSA, ALTRIMENTI PASSA IL TURNO(STATO SELECTMOVE1 DEL PROSSIMO PLAYER)
                if(firstMove == 1)
                    gameModel.setState(SELECTMOVE2);
                else if(firstMove == 2) {
                    changeActualPlayer();
                    gameModel.setActualPlayer(actualPlayer);
                    gameModel.setState(SELECTMOVE1);
                }
                else
                    gameModel.setState(ERROR);

                break;

            case SELECTMOVE2://--------------------------------------------------LA VIEW MOSTRERà UNA SOLA MOSSA POSSIBILE(1) E IL PASSATURNO(2)

                if(viewCLI.getChoose1() == 1){

                    //SE LA PRIMA MOSSA EFFETTUATA è SELEZIONE DADO LA SECONDA SARà SELEZIONA CARTA E VICEVERSA
                    if(firstMove == 1)
                        gameModel.setState(SELECTCARD);
                    else if(firstMove == 2)
                        gameModel.setState(SELECTDRAFT);
                    else
                        gameModel.setState(ERROR);

                }
                else if(viewCLI.getChoose1() == 2){
                    changeActualPlayer();
                    gameModel.setActualPlayer(actualPlayer);
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
                if(firstMove == 1) {
                    changeActualPlayer();
                    gameModel.setActualPlayer(actualPlayer);
                    gameModel.setState(SELECTMOVE1);
                }
                else if(firstMove == 2)
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
