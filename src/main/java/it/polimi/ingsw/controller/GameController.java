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

    private void setWindow(int i){
        gameModel.playerSetWindow(i);
    }


    private void startGame(ArrayList<Player> players){
        gameModel = new GameModel(players, SELECTWINDOW);
        actualPlayer = 0;
    }

    private void changePlayer(){
        actualPlayer ++;
        if(actualPlayer == gameModel.getPlayers().size()){
            actualPlayer = 0;
        }
    }

    @Override
    public void update(ViewCLI viewCLI) {

        switch(gameModel.getState()){

            case SELECTWINDOW:

                //setWindow(viewCLI.getChoose1);
                changePlayer();
                gameModel.setActualPlayer(actualPlayer);

                if(gameModel.getActualPlayer().getWindow() != null){
                    gameModel.setState(SELECTCARD);
                }else{
                    gameModel.setState(SELECTWINDOW);
                }

            case SELECTDRAFT:
                break;
            case SELECTCARD:
                break;
            case ERROR:
                break;
            default:
                break;


        }
    }
}
