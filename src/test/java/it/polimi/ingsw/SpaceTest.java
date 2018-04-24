package it.polimi.ingsw;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import it.polimi.ingsw.model.Space;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpaceTest {


    private Dice dice;
    private Space space;

    @Before
    public void before(){
        space = new Space(Colors.R, 0);
        dice = new Dice(Colors.R);
    }

    @Test
    public void getIsEmptyTest(){
        assertTrue(space.getIsEmpty());
    }
    @Test
    public void setIsEmptyTest(){
        space.setIsEmpty(false);
        assertFalse(space.getIsEmpty());
    }
    @Test
    public void getColorTest(){
        assertEquals(Colors.R, space.getColor());
    }
    @Test
    public void setColorTest(){
        space.setColor(Colors.W);
        assertEquals(Colors.W, space.getColor());
    }
    @Test
    public void getValueTest(){
        assertEquals(0, space.getValue());
    }
    @Test
    public void setValueTest(){
        space.setValue(5);
        assertEquals(5, space.getValue());
    }
    @Test
    public void getDiceTest(){
        assertNull(space.getDice());
    }
    @Test
    public void setDiceTest(){
        space.setDice(dice);
        assertEquals(dice, space.getDice());
    }

}
