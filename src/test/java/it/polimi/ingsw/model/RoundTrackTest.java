package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoundTrackTest {

    private RoundTrack roundTrack;

    @Before
    public void setUp(){
        roundTrack = RoundTrack.getInstance();
    }

    @Test
    public void getInstance() {
        assertEquals(roundTrack, RoundTrack.getInstance());
    }


    @Test
    public void incrementRound() {
        assertEquals(1, roundTrack.getRound());
        roundTrack.incrementRound();
        assertEquals(2, roundTrack.getRound());
    }

    @Test
    public void changeDice() {
        Dice dice1 = new Dice(Colors.R);
        Dice dice2 = new Dice(Colors.G);
        Dice dice3 = new Dice(Colors.Y);
        Dice dicetmp;
        assertTrue(roundTrack.getGrid().isEmpty());
        roundTrack.setGrid(dice1);
        assertFalse(roundTrack.getGrid().isEmpty());
        roundTrack.setGrid(dice2);
        dicetmp = roundTrack.changeDice(0, dice3);
        assertEquals(dice1, dicetmp);
        assertEquals(2, roundTrack.getGrid().size());
        assertEquals(1, roundTrack.getRound());
    }
}