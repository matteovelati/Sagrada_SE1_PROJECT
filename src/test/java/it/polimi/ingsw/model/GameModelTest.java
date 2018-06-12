package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameModelTest {

    /*
     *
     *
     *
     *
     * VA COMMENTATA LA LINEA 61 DEL GAMEMODEL METODO SETPLAYERS AFFINCHE' FUNZIONI:
     * list.get(list.size()-1).print("YOU HAVE BEEN ADDED TO THIS GAME!");
     *
     *
     *
     *
     */

    private GameModel gameModel;
    private Player player1, player2;

    @Before
    public void setUp() {
        gameModel = GameModel.getInstance(States.LOBBY, 0);
        player1 = new Player("sara", Colors.R);
        player2 = new Player("matteo", Colors.G);
        try {
            gameModel.setPlayers(player1);
            gameModel.setPlayers(player2);
        }
        catch (Exception e){
            assert false;
        }
    }

    @Test
    public void getInstance() {
        assertEquals(gameModel, GameModel.getInstance(States.LOBBY, 0));
    }

    @Test
    public void setSchemeCards() {
        gameModel.setSchemeCards();
        assertEquals(4, gameModel.getSchemeCards().size());
        setUp();
    }

    @Test
    public void setDraft() {
        gameModel.setDraft();
        assertEquals(5, gameModel.getField().getDraft().getDraft().size());
        setUp();
    }

    @Test
    public void playerSetWindow() {
        gameModel.setSchemeCards();
        gameModel.playerSetWindow(2);
        assertNotNull(gameModel.getActualPlayer().getWindow());
        setUp();
    }

    @Test
    public void playerPickDice() {
        gameModel.setDraft();
        gameModel.playerPickDice(2);
        assertEquals(4, gameModel.getField().getDraft().getDraft().size());
        setUp();
    }

    @Test
    public void playerPutDice() {
        gameModel.setDraft();
        gameModel.setSchemeCards();
        gameModel.playerSetWindow(2);
        gameModel.playerPickDice(2);
        gameModel.playerPutDice(1,1);
        assertTrue(gameModel.getActualPlayer().getWindow().getIsEmpty());
        setUp();
    }

    @Test
    public void playerSelectToolCard() {
        gameModel.setSchemeCards();
        gameModel.playerSetWindow(1);
        assertTrue(gameModel.playerSelectToolCardMP(2));
        setUp();
    }

    @Test
    public void playerUseToolCard() {
        gameModel.setSchemeCards();
        gameModel.playerSetWindow(3);
        gameModel.playerSelectToolCardMP(0);
        ArrayList<Integer> input = new ArrayList<>(1);
        input.add(-1);
        assertFalse(gameModel.playerUseToolCard(input));
        setUp();
    }

    @Test
    public void nextPlayer() {
        assertEquals(1, gameModel.nextPlayer(0));
        assertEquals(1, gameModel.nextPlayer(1));
        assertEquals(0, gameModel.nextPlayer(1));
        setUp();
    }

    @Test
    public void endRound() {
        assertEquals(1, gameModel.getField().getRoundTrack().getRound());
        gameModel.setDraft();
        gameModel.endRound();
        assertEquals(2, gameModel.getField().getRoundTrack().getRound());
        assertEquals(5, gameModel.getField().getRoundTrack().getGrid().size());
        setUp();
    }

    @Test
    public void putDiceInDraft() {
        gameModel.setDraft();
        assertEquals(5, gameModel.getField().getDraft().getDraft().size());
        gameModel.playerPickDice(0);
        assertEquals(4, gameModel.getField().getDraft().getDraft().size());
        gameModel.putDiceInDraft();
        assertEquals(5, gameModel.getField().getDraft().getDraft().size());
        setUp();
    }

    @Test
    public void decreaseToken() {
        gameModel.setSchemeCards();
        gameModel.playerSetWindow(1);
        gameModel.playerSelectToolCardMP(2);
        int oldtokens = gameModel.getActualPlayer().getTokens();
        gameModel.decreaseToken();
        assertTrue(gameModel.getActualPlayer().getTokens() < oldtokens);
        setUp();
    }
}