package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TCFluxRemoverTest {

    private ToolCard tc = new TCFluxRemover();
    private GameModel gameModel;
    private Dice dice1, dice2, dice3, dice4, dice5, dice6, dice7, dice8;
    private Player player1;
    private ArrayList<Player> players;
    private States state;
    private Window window1;
    private SchemeCard schemeCard1;
    private SchemeCard schemeCard2;

    @Before
    public void before() {

        players = new ArrayList<>(1);
        player1 = new Player("matteo");
        players.add(player1);
        state = States.SELECTMOVE1;
        gameModel = new GameModel(players, state);

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

        gameModel.getField().getDraft().addDice(dice1);
        gameModel.getField().getDraft().addDice(dice2);
        gameModel.getField().getDraft().addDice(dice3);
        gameModel.getField().getDraft().addDice(dice4);
        gameModel.getField().getDraft().addDice(dice5);

        window1 = new Window(5);
        schemeCard1 = new SchemeCard(window1, null);
        schemeCard2 = new SchemeCard(null, null);
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


    @Test
    public void useToolCard1() {

        ArrayList<Integer> input = new ArrayList<>();
        input.add(0);
        input.add(2);
        input.add(1);
        input.add(3);

        assertTrue(tc.useToolCard(gameModel, input));
        assertTrue(tc.useToolCard(gameModel, input));
        assertTrue(tc.useToolCard(gameModel, input));
        assertTrue(gameModel.getBag().getBag().contains(dice1));
    }

    @Test
    public void useToolCard2() {

        ArrayList<Integer> input = new ArrayList<>();
        input.add(2);
        input.add(1);
        input.add(3);
        input.add(4);

        assertTrue(tc.useToolCard(gameModel, input));
        assertTrue(tc.useToolCard(gameModel, input));
        assertFalse(tc.useToolCard(gameModel, input));
        assertTrue(gameModel.getField().getDraft().getDraft().contains(dice3));
    }
}