package it.polimi.ingsw;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import it.polimi.ingsw.model.RoundTrack;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoundTrackTest {

    private RoundTrack roundTrack;
    private Dice dice;

    @Before
    public void before(){
        roundTrack = RoundTrack.getInstance();
        dice = new Dice(Colors.G);
    }

    @Test
    public void setGridTest(){
        roundTrack.setGrid(dice);
        assertEquals(dice, roundTrack.getGrid(0));
    }

    @Test
    public void getGridTest(){
        roundTrack.setGrid(dice);
        assertEquals(dice, roundTrack.getGrid(0));
    }

    @Test
    public void getRoundTest(){
        roundTrack.setGrid(dice);
        assertEquals(2, roundTrack.getRound());
    }
}
