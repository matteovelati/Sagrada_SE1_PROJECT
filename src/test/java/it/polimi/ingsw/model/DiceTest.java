package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiceTest {

    private Dice dice;

    @Before
    public void setUp(){
        dice = new Dice(Colors.R);
    }

    @Test
    public void setValue() {
        setUp();
        dice.setValue();
        assertTrue(dice.getValue() <= 6);
        assertTrue(dice.getValue() >= 1);
    }

    @Test
    public void modifyValue() {
        setUp();
        dice.modifyValue(5);
        assertEquals(5, dice.getValue());
    }
}