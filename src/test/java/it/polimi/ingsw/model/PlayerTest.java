package it.polimi.ingsw.model;

import it.polimi.ingsw.model.toolcards.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {

    private GameModel gameModel;
    private Player player1, player2;
    private SchemeCard sc1, sc2;
    private ArrayList<ToolCard> toolCards;
    private Draft draft;
    private Bag bag;
    private Dice diceR, diceB;
    private ArrayList<Integer> input;

    @Before
    public void setUp(){
        Bag.reset();
        RoundTrack.reset();
        Draft.reset();
        RoundManager.reset();
        Field.reset();
        GameModel.reset();
        gameModel = GameModel.getInstance(0);
        sc1 = new SchemeCard(1);
        sc2 = new SchemeCard(2);
        player1 = new Player("P1", Colors.R);
        player2 = new Player("P2", Colors.B);
        toolCards = new ArrayList<>(3);
        toolCards.add(new TCCopperFoilBurnisher());
        toolCards.add(new TCGlazingHammer());
        toolCards.add(new TCGrindingStone());
        draft = Draft.getInstance();
        bag = Bag.getInstance();
        diceR = new Dice(Colors.R);
        diceR.modifyValue(4);
        diceB = new Dice(Colors.B);
        diceB.modifyValue(3);
        draft.addDice(diceR);
        draft.addDice(diceB);
        draft.addDice(new Dice(Colors.Y));
        draft.addDice(new Dice(Colors.G));
        draft.addDice(new Dice(Colors.P));
        input = new ArrayList<>(1);
        assertTrue(player1.getOnline());
    }

    @Test
    public void setWindow() {
        setUp();
        assertEquals("P1", player1.getUsername());
        player1.setWindow(sc1, sc2, 1);
        assertEquals(player1.getWindow().getDifficulty(), player1.getTokens());
        player2.setWindow(sc1, sc2, 2);
        assertEquals(player2.getWindow().getDifficulty(), player2.getTokens());
    }

    @Test
    public void selectToolCardMP() {
        setUp();
        player1.setWindow(sc1, sc2, 1);
        player2.setWindow(sc1, sc2, 2);
        assertTrue(player1.selectToolCardMP(toolCards, 0));
        toolCards.get(1).setIsUsed(true);
        assertTrue(player2.selectToolCardMP(toolCards, 1));
    }

    @Test
    public void selectToolCardSP() {
        setUp();
        player1.setWindow(sc1, sc2, 1);
        assertTrue(player1.selectToolCardSP(toolCards, 0));
        assertEquals(toolCards.get(0), player1.getToolCardSelected());
        toolCards.get(0).setIsUsed(true);
        assertFalse(player1.selectToolCardSP(toolCards, 0));
        draft.getDraft().clear();
        assertFalse(player1.selectToolCardSP(toolCards, 1));
    }
    
    @Test
    public void pickDice() {
        setUp();
        assertEquals(5, draft.getDraft().size());
        player1.pickDice(draft, 1);
        assertEquals(4, draft.getDraft().size());
        assertEquals(diceB, player1.getDice());
        Dice diceX = new Dice(Colors.R);
        player1.setDice(diceX);
        assertEquals(diceX, player1.getDice());
    }

    @Test
    public void putDice() {
        setUp();
        player1.setWindow(sc1, sc2, 1);
        assertTrue(player1.getWindow().getIsEmpty());
        player1.pickDice(draft, 0);
        assertFalse(player1.putDice(0,0));
        assertTrue(player1.putDice(0,2));
        player1.pickDice(draft, 0);
        assertFalse(player1.putDice(3,1));
        assertTrue(player1.putDice(1,2));
    }

    @Test
    public void useToolCard() {
        setUp();
        input.add(-1);
        input.add(0);
        input.add(1);
        input.add(2);
        player1.setWindow(sc1, sc2, 1);
        player1.selectToolCardMP(toolCards, 0);
        try {
            gameModel.setPlayers(player1);
        }
        catch (Exception e){
            //do nothing
        }
        gameModel.setActualPlayer(0);
        assertFalse(player1.useToolCard(gameModel, input));
        assertFalse(player1.getSkipNextTurn());
    }

    @Test
    public void decreaseToken() {
        setUp();
        player1.setWindow(sc1, sc2, 1);
        player2.setWindow(sc1, sc2, 2);
        player1.selectToolCardMP(toolCards, 0);
        player2.selectToolCardMP(toolCards, 0);
        assertEquals(3, player1.getTokens());
        player1.decreaseToken();
        assertEquals(2, player1.getTokens());
        toolCards.get(0).setIsUsed(true);
        assertEquals(5, player2.getTokens());
        player2.decreaseToken();
        assertEquals(3, player2.getTokens());
    }


    @Test
    public void getFinalScore(){
        setUp();
        player1.setFinalScore(21);
        assertEquals(21, player1.getFinalScore());
    }
}