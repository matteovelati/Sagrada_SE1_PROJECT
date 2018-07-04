package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoundManagerTest {

    private RoundManager roundManager;
    private int actualPlayer0, actualPlayer1, actualPlayer2, actualPlayer3, nPlayers;
    private Draft draft;
    private Bag bag;
    private RoundTrack roundTrack;

    @Before
    public void setUp() {
        Bag.reset();
        RoundTrack.reset();
        Draft.reset();
        RoundManager.reset();
        roundManager = RoundManager.getInstance();
        actualPlayer0 = 0;
        actualPlayer1 = 1;
        actualPlayer2 = 2;
        actualPlayer3 = 3;
        nPlayers = 4;

        bag = Bag.getInstance();
        draft = Draft.getInstance();
        draft.setDraft(bag);
        roundTrack = RoundTrack.getInstance();
    }


    @Test
    public void changeActualPlayer() {
        setUp();
        assertEquals(1, roundManager.changeActualPlayer(actualPlayer0, nPlayers));
        assertEquals(1, roundManager.getTurn());

        assertEquals(2, roundManager.changeActualPlayer(actualPlayer1, nPlayers));
        assertEquals(1, roundManager.getTurn());

        assertEquals(3, roundManager.changeActualPlayer(actualPlayer2, nPlayers));
        assertEquals(1, roundManager.getTurn());

        assertEquals(3, roundManager.changeActualPlayer(actualPlayer3, nPlayers));
        assertEquals(2, roundManager.getTurn());

        assertEquals(2, roundManager.changeActualPlayer(actualPlayer3, nPlayers));
        assertEquals(2, roundManager.getTurn());

        assertEquals(1, roundManager.changeActualPlayer(actualPlayer2, nPlayers));
        assertEquals(2, roundManager.getTurn());

        assertEquals(0, roundManager.changeActualPlayer(actualPlayer1, nPlayers));
        assertEquals(2, roundManager.getTurn());

        assertEquals(1, roundManager.changeActualPlayer(actualPlayer0, nPlayers));
        assertEquals(1, roundManager.getTurn());
    }

    @Test
    public void endRound() {
        setUp();
        Dice dice = draft.getDraft().get(0);
        roundManager.endRound(draft, roundTrack);
        assertEquals(1, roundManager.getCounter());
        assertEquals(dice, roundTrack.getGrid().get(0));
        assertFalse(draft.getDraft().contains(dice));
    }
}