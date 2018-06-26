package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoundTrackTest {

    private RoundTrack roundTrack;

    @Before
    public void setUp(){
        RoundTrack.reset();
        roundTrack = RoundTrack.getInstance();
    }

    @Test
    public void getInstance() {
        setUp();
        assertEquals(roundTrack, RoundTrack.getInstance());
    }


    @Test
    public void incrementRound() {
        setUp();
        assertEquals(1, roundTrack.getRound());
        roundTrack.incrementRound();
        assertEquals(2, roundTrack.getRound());
    }

    @Test
    public void changeDice() {
        setUp();
        Dice dice1 = new Dice(Colors.R);
        dice1.modifyValue(5);
        Dice dice2 = new Dice(Colors.G);
        dice2.modifyValue(4);
        Dice dice3 = new Dice(Colors.Y);
        dice3.modifyValue(2);
        Dice dicetmp;
        assertTrue(roundTrack.getGrid().isEmpty());
        roundTrack.setGrid(dice1);
        assertEquals(5, roundTrack.calculateRoundTrack());
        assertFalse(roundTrack.getGrid().isEmpty());
        roundTrack.setGrid(dice2);
        assertEquals(9, roundTrack.calculateRoundTrack());
        dicetmp = roundTrack.changeDice(0, dice3);
        assertEquals(6, roundTrack.calculateRoundTrack());
        assertEquals(dice1, dicetmp);
        assertEquals(2, roundTrack.getGrid().size());
        assertEquals(1, roundTrack.getRound());
    }
}