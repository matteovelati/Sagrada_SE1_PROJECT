package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class GameController implements ControllerObserver {

    private GameModel gameModel;
    private int actualPlayer;

    public GameController(){

    }


    public void setPlayers(String username){//---------------------------------------------DA FARE
        ArrayList<Player> players;

        Player p = new Player(username);
        players.add(p);

        if(players.size()==4) startGame(players);
        else
            //aspetta giocatori
    }

    private void setWindow(int i){
        gameModel.playerSetWindow(i);
    }


    private void startGame(ArrayList<Player> players){
        gameModel = new GameModel(players);
        actualPlayer = 0;
        gameModel.setState(SHOWWINDOWS);
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

            case(SHOWWINDOWS):

                setWindow(viewCLI.window);
                changePlayer();
                gameModel.setActualPlayer(actualPlayer);

                if(gameModel.getActualPlayer().getWindow()) != null){
                    gameModel.setState(SETTOKENS);
                }else{
                    gameModel.setState(SHOWWINDOWS);
                }

    }

    @Override
    public void update(ViewGUI viewGUI) {

    }
}
