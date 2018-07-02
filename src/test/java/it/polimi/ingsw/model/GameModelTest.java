package it.polimi.ingsw.model;

import it.polimi.ingsw.model.toolcards.TCEglomiseBrush;
import it.polimi.ingsw.model.toolcards.TCGlazingHammer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameModelTest {

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
        gameModel = GameModel.getInstance(0);
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
        setUp();
        assertEquals(gameModel, GameModel.getInstance(0));
    }

    @Test
    public void setSchemeCards() {
        setUp();
        gameModel.setSchemeCards();
        assertEquals(4, gameModel.getSchemeCards().size());
    }

    @Test
    public void setDraft() {
        setUp();
        gameModel.setDraft();
        assertEquals(5, gameModel.getField().getDraft().getDraft().size());
    }

    @Test
    public void playerSetWindow() {
        setUp();
        gameModel.setSchemeCards();
        gameModel.playerSetWindow(2);
        assertNotNull(gameModel.getActualPlayer().getWindow());
    }

    @Test
    public void playerPickDice() {
        setUp();
        gameModel.setDraft();
        assertEquals(5, gameModel.getField().getDraft().getDraft().size());
        gameModel.playerPickDice(2);
        assertEquals(4, gameModel.getField().getDraft().getDraft().size());
    }

    @Test
    public void playerPutDice() {
        setUp();
        gameModel.setDraft();
        gameModel.setSchemeCards();
        gameModel.playerSetWindow(2);
        gameModel.playerPickDice(2);
        gameModel.playerPutDice(1,1);
        assertTrue(gameModel.getActualPlayer().getWindow().getIsEmpty());
    }

    @Test
    public void playerSelectToolCard() {
        setUp();
        gameModel.setSchemeCards();
        gameModel.playerSetWindow(1);
        assertTrue(gameModel.playerSelectToolCardMP(2));
    }

    @Test
    public void playerUseToolCard() {
        setUp();
        gameModel.setSchemeCards();
        gameModel.playerSetWindow(3);
        gameModel.getField().getToolCards().add(0, new TCEglomiseBrush());
        gameModel.playerSelectToolCardMP(0);
        ArrayList<Integer> input = new ArrayList<>(1);
        input.add(-1);
        assertFalse(gameModel.playerUseToolCard(input));
    }

    @Test
    public void nextPlayer() {
        setUp();
        assertEquals(1, gameModel.nextPlayer(0));
        assertEquals(1, gameModel.nextPlayer(1));
        assertEquals(0, gameModel.nextPlayer(1));
    }

    @Test
    public void endRound() {
        setUp();
        assertEquals(1, gameModel.getField().getRoundTrack().getRound());
        gameModel.setDraft();
        gameModel.endRound();
        assertEquals(2, gameModel.getField().getRoundTrack().getRound());
        assertEquals(5, gameModel.getField().getRoundTrack().getGrid().size());
    }

    @Test
    public void putDiceInDraft() {
        setUp();
        gameModel.setDraft();
        assertEquals(5, gameModel.getField().getDraft().getDraft().size());
        gameModel.playerPickDice(0);
        assertEquals(4, gameModel.getField().getDraft().getDraft().size());
        gameModel.putDiceInDraft();
        assertEquals(5, gameModel.getField().getDraft().getDraft().size());
    }

    @Test
    public void decreaseToken() {
        setUp();
        gameModel.setSchemeCards();
        gameModel.playerSetWindow(1);
        gameModel.playerSelectToolCardMP(2);
        int oldtokens = gameModel.getActualPlayer().getTokens();
        gameModel.decreaseToken();
        assertTrue(gameModel.getActualPlayer().getTokens() < oldtokens);
    }
}