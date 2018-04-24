package it.polimi.ingsw;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class DiceTest {

    private Dice dice1;
    private Dice dice2;
    private Dice dice3;
    private Dice dice4;
    private Dice dice5;

    @Before
    public void before(){
        dice1 = new Dice(Colors.Y);
        dice2 = new Dice(Colors.G);
        dice3 = new Dice(Colors.R);
        dice4 = new Dice(Colors.P);
        dice5 = new Dice(Colors.B);
        dice1.setValue();
        dice2.setValue();
        dice3.setValue();
        dice4.setValue();
        dice5.setValue();
    }

    @Test
    public void testGetColor(){
        assertEquals(Colors.Y, dice1.getColor());
        assertEquals(Colors.G, dice2.getColor());
        assertEquals(Colors.R, dice3.getColor());
        assertEquals(Colors.P, dice4.getColor());
        assertEquals(Colors.B, dice5.getColor());
    }

    @Test
    public void testGetValue(){
        assertTrue(dice1.getValue()>0 && dice1.getValue()<7);
        assertTrue(dice2.getValue()>0 && dice1.getValue()<7);
        assertTrue(dice3.getValue()>0 && dice1.getValue()<7);
        assertTrue(dice4.getValue()>0 && dice1.getValue()<7);
        assertTrue(dice5.getValue()>0 && dice1.getValue()<7);
    }


}
