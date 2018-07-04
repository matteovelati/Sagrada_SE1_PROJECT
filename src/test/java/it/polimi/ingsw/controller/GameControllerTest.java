package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameControllerTest {

    private GameController gameController = null;
    private GameModel gameModel;
    private Player player1, player2;

    @Before
    public void setUp() {
        Bag.reset();
        RoundTrack.reset();
        Draft.reset();
        RoundManager.reset();
        Field.reset();
        GameModel.reset();
        if (gameController == null) {
            try {
                gameController = new GameController(null, "90000");
            } catch (RemoteException e) {
                //
            }
        }
    }

    @Test
    public void getSinglePlayerStarted(){
        setUp();
        assertFalse(gameController.getSinglePlayerStarted());
        gameController.createGameModel(1);
        assertTrue(gameController.getSinglePlayerStarted());
    }

    @Test
    public void getMultiPlayerStarted(){
        setUp();
        assertFalse(gameController.getMultiPlayerStarted());
        gameController.createGameModel(0);
        assertTrue(gameController.getMultiPlayerStarted());
    }

    @Test
    public void setPlayerOnline(){
        setUp();
        gameController.createGameModel(0);
        gameModel = gameController.getGameModel();
        player1 = new Player("sara", Colors.R);
        player2 = new Player("matteo", Colors.G);
        try {
            gameModel.setPlayers(player1);
            gameModel.setPlayers(player2);
        }
        catch (Exception e){
            assert false;
        }
        assertTrue(player1.getOnline());
        assertTrue(player2.getOnline());
        gameController.setPlayerOnline("sara", false);
        assertFalse(player1.getOnline());
        assertTrue(player2.getOnline());
    }

}