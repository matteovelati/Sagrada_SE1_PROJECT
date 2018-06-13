package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpaceTest {

    private Space space;
    private Dice dice, dicenull;

    @Before
    public void setUp(){
        space = new Space(Colors.W, 0);
        dice = new Dice(Colors.R);
        dicenull = null;
    }

    @Test
    public void setDice() {
        setUp();
        space.setValue(3);
        space.setColor(Colors.Y);
        assertEquals(Colors.Y, space.getColor());
        assertEquals(3, space.getValue());
        assertTrue(space.getIsEmpty());
        assertNull(space.getDice());
        space.setDice(dice);
        assertEquals(dice, space.getDice());
        assertFalse(space.getIsEmpty());
        space.setDice(dicenull);
        assertNull(space.getDice());
        assertTrue(space.getIsEmpty());
    }
}