package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TCLathekinTest {

    private ToolCard tc = new TCLathekin();
    private GameModel gameModel;
    private Dice dice1, dice2, dice3, dice4, dice5, dice6, dice7, dice8;
    private Player player1;
    private SchemeCard schemeCard1;
    private SchemeCard schemeCard2;

    @Before
    public void before() {

        Bag.reset();
        RoundTrack.reset();
        Draft.reset();
        RoundManager.reset();
        Field.reset();
        GameModel.reset();
        gameModel = GameModel.getInstance(0);
        player1 = new Player("matteo", Colors.G);
        try {
            gameModel.setPlayers(player1);
        }
        catch (Exception e){
            assert false;
        }

        dice1 = new Dice(Colors.R);    //00
        dice1.modifyValue(5);
        dice2 = new Dice(Colors.B);    //01
        dice2.modifyValue(6);
        dice3 = new Dice(Colors.R);    //02
        dice3.modifyValue(2);
        dice4 = new Dice(Colors.R);    //03
        dice4.modifyValue(3);
        dice5 = new Dice(Colors.Y);    //04
        dice5.modifyValue(1);
        dice6 = new Dice(Colors.B);    //20
        dice6.modifyValue(2);
        dice7 = new Dice(Colors.R);    //21
        dice7.modifyValue(5);
        dice8 = new Dice(Colors.R);    //22
        dice8.modifyValue(5);

        setDraft();

        schemeCard1 = new SchemeCard(3);
        schemeCard2 = new SchemeCard(1);
        gameModel.getActualPlayer().setWindow(schemeCard1, schemeCard2, 1);
        gameModel.getActualPlayer().getWindow().setWindow(dice1, 0, 0);
        gameModel.getActualPlayer().getWindow().setWindow(dice2, 0, 1);
        gameModel.getActualPlayer().getWindow().setWindow(dice3, 0, 2);
        //gameModel.getActualPlayer().getWindow().setWindow(dice4, 0, 3); EMPTY
        gameModel.getActualPlayer().getWindow().setWindow(dice5, 0, 4);
        gameModel.getActualPlayer().getWindow().setWindow(dice6, 2, 0);
        gameModel.getActualPlayer().getWindow().setWindow(dice7, 2, 1);
        gameModel.getActualPlayer().getWindow().setWindow(dice8, 2, 2);

    }

    public void setDraft(){
        gameModel.getField().getDraft().getDraft().clear();
        gameModel.getField().getDraft().addDice(dice1);
        gameModel.getField().getDraft().addDice(dice2);
        gameModel.getField().getDraft().addDice(dice3);
        gameModel.getField().getDraft().addDice(dice4);
        gameModel.getField().getDraft().addDice(dice5);
    }


    @Test
    public void useToolCard1() {

        before();
        ArrayList<Integer> input = new ArrayList<>();

        input.add(0);
        input.add(1);
        input.add(1);
        input.add(4);
        input.add(0);
        input.add(0);
        input.add(2);
        input.add(4);

        assertTrue(tc.useToolCard(gameModel, input));
        assertTrue(tc.useToolCard(gameModel, input));
        assertEquals(gameModel.getActualPlayer().getWindow().getWindow()[input.get(2)][input.get(3)].getDice(), dice2);
        assertEquals(gameModel.getActualPlayer().getWindow().getWindow()[input.get(6)][input.get(7)].getDice(), dice1);
        assertTrue(gameModel.getActualPlayer().getWindow().getWindow()[input.get(0)][input.get(1)].getIsEmpty());
        assertTrue(gameModel.getActualPlayer().getWindow().getWindow()[input.get(4)][input.get(5)].getIsEmpty());
    }

    @Test
    public void useToolCard2() {

        before();
        ArrayList<Integer> input = new ArrayList<>();

        input.add(0);
        input.add(0);
        input.add(1);
        input.add(0);
        input.add(0);
        input.add(1);
        input.add(3);
        input.add(4);

        assertTrue(tc.useToolCard(gameModel, input));
        assertFalse(tc.useToolCard(gameModel, input));
        assertTrue(gameModel.getActualPlayer().getWindow().getWindow()[input.get(0)][input.get(1)].getIsEmpty());
    }

    @Test
    public void useToolCard3() {

        before();
        ArrayList<Integer> input = new ArrayList<>();

        input.add(0);
        input.add(0);
        input.add(2);
        input.add(0);
        input.add(0);
        input.add(2);
        input.add(1);
        input.add(1);

        assertFalse(tc.useToolCard(gameModel, input));
        assertFalse(tc.useToolCard(gameModel, input));
        assertFalse(gameModel.getActualPlayer().getWindow().getWindow()[input.get(0)][input.get(1)].getIsEmpty());
        assertFalse(gameModel.getActualPlayer().getWindow().getWindow()[input.get(4)][input.get(5)].getIsEmpty());
    }

    @Test
    public void useToolCard4() {

        before();
        ArrayList<Integer> input = new ArrayList<>();

        input.add(-1);

        assertTrue(tc.select(gameModel));
        assertFalse(tc.useToolCard(gameModel, input));
        assertFalse(tc.useToolCard(gameModel, input));
    }
}