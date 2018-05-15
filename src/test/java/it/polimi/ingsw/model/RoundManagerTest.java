package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoundManagerTest {

    RoundManager roundManager;
    int actualPlayer0;
    int actualPlayer1;
    int actualPlayer2;
    int actualPlayer3;
    int nPlayers;

    Draft draft;
    Bag bag;
    RoundTrack roundTrack;

    @Before
    public void before() {
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
        Dice dice = draft.getDraft().get(0);

        roundManager.endRound(draft, roundTrack);

        assertEquals(dice, roundTrack.getGrid().get(0));
        assertNotEquals(dice, draft.getDraft().get(0));
    }
}