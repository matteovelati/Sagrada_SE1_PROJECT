package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WindowTest {

    private Window window;
    private Dice dice1, dice2, dice3, dicenull;

    @Before
    public void setUp(){
        dice1 = new Dice(Colors.R);
        dice2 = new Dice(Colors.R);
        dice3 = new Dice(Colors.B);
        dice1.modifyValue(1);
        dice2.modifyValue(6);
        dice3.modifyValue(6);
        dicenull = null;
        window = new Window(0);
        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                assertTrue(window.getWindow()[i][j].getIsEmpty());
                assertEquals(Colors.W, window.getWindow()[i][j].getColor());
                assertEquals(0, window.getWindow()[i][j].getValue());
                assertNull(window.getWindow()[i][j].getDice());
            }
        }
    }

    @Test
    public void windowSwitch(){
        setUp();
        for (int x = 1; x < 25; x ++){
            window = new Window(x);
            assertNotNull(window.getName());
            assertTrue(window.getDifficulty() > 2);
            assertEquals(x, window.getIdNumber());
        }
    }

    @Test
    public void setWindow() {
        setUp();
        assertTrue(window.getIsEmpty());
        window.setWindow(dice1, 0, 0);
        assertFalse(window.getIsEmpty());
        assertEquals(dice1, window.getWindow()[0][0].getDice());
        window.setWindow(dicenull, 0, 0);
        assertTrue(window.getIsEmpty());
        assertNull(window.getWindow()[0][0].getDice());
    }

    @Test
    public void neighboursColorRestriction() {
        setUp();
        window.setWindow(dice1,1,1);
        assertFalse(window.neighboursColorRestriction(dice2, 0, 1));
        assertFalse(window.neighboursColorRestriction(dice2, 1, 0));
        assertFalse(window.neighboursColorRestriction(dice2, 2, 1));
        assertFalse(window.neighboursColorRestriction(dice2, 1, 2));
        assertTrue(window.neighboursColorRestriction(dice2, 2, 2));
        assertTrue(window.neighboursColorRestriction(dice2, 2, 0));
        assertTrue(window.neighboursColorRestriction(dice2, 0, 2));
        assertTrue(window.neighboursColorRestriction(dice2, 0, 0));
    }

    @Test
    public void neighboursNumberRestriction() {
        setUp();
        window.setWindow(dice2,1,1);
        assertFalse(window.neighboursNumberRestriction(dice3, 0, 1));
        assertFalse(window.neighboursNumberRestriction(dice3, 1, 0));
        assertFalse(window.neighboursNumberRestriction(dice3, 2, 1));
        assertFalse(window.neighboursNumberRestriction(dice3, 1, 2));
        assertTrue(window.neighboursNumberRestriction(dice3, 2, 2));
        assertTrue(window.neighboursNumberRestriction(dice3, 2, 0));
        assertTrue(window.neighboursNumberRestriction(dice3, 0, 2));
        assertTrue(window.neighboursNumberRestriction(dice3, 0, 0));
    }

    @Test
    public void neighboursPositionRestriction() {
        setUp();
        window.setWindow(dice1,1,1);
        assertTrue(window.neighboursPositionRestriction(0, 1));
        assertTrue(window.neighboursPositionRestriction(1, 0));
        assertTrue(window.neighboursPositionRestriction(2, 1));
        assertTrue(window.neighboursPositionRestriction(1, 2));
        assertTrue(window.neighboursPositionRestriction(2, 2));
        assertTrue(window.neighboursPositionRestriction(2, 0));
        assertTrue(window.neighboursPositionRestriction(0, 2));
        assertTrue(window.neighboursPositionRestriction(0, 0));
        assertFalse(window.neighboursPositionRestriction(3, 4));
        assertFalse(window.neighboursPositionRestriction(2, 3));
        assertFalse(window.neighboursPositionRestriction(0, 4));
    }

    @Test
    public void spaceColorRestriction() {
        setUp();
        window = new Window(1);
        assertFalse(window.spaceColorRestriction(dice2, 0, 0));
        assertTrue(window.spaceColorRestriction(dice3, 0, 0));
        window.setWindow(dice1, 0, 0);
        assertFalse(window.spaceColorRestriction(dice3, 0, 0));

    }

    @Test
    public void spaceNumberRestriction() {
        setUp();
        window = new Window(2);
        assertTrue(window.spaceNumberRestriction(dice1, 3, 0));
        assertFalse(window.spaceNumberRestriction(dice3, 3, 0));
        window.setWindow(dice3, 3, 0);
        assertFalse(window.spaceNumberRestriction(dice1, 3, 0));
    }

    @Test
    public void verifyFirstDiceRestriction() {
        setUp();
        window = new Window(3);
        assertFalse(window.verifyFirstDiceRestriction(dice1, 1, 1));
        assertFalse(window.verifyFirstDiceRestriction(dice3, 1, 2));
        assertFalse(window.verifyFirstDiceRestriction(dice2, 1, 3));
        assertFalse(window.verifyFirstDiceRestriction(dice2, 2, 1));
        assertFalse(window.verifyFirstDiceRestriction(dice1, 2, 2));
        assertFalse(window.verifyFirstDiceRestriction(dice3, 2, 3));
        assertTrue(window.verifyFirstDiceRestriction(dice3, 0, 0));
        assertTrue(window.verifyFirstDiceRestriction(dice1, 0, 1));
        assertTrue(window.verifyFirstDiceRestriction(dice1, 3, 0));
        assertTrue(window.verifyFirstDiceRestriction(dice2, 3, 3));
    }

    @Test
    public void verifyAllRestrictions() {
        setUp();
        window = new Window(4);
        assertFalse(window.verifyAllRestrictions(dice1, 1, 1));
        assertFalse(window.verifyAllRestrictions(dice2, 0, 0));
        assertFalse(window.verifyAllRestrictions(dice3, 3, 4));
        window.setWindow(dice1, 1,1);
        assertFalse(window.verifyAllRestrictions(dice1, 0, 0));
        assertFalse(window.verifyAllRestrictions(dice2, 0, 0));
        assertFalse(window.verifyAllRestrictions(dice3, 0, 0));
        Dice diceY = new Dice(Colors.Y);
        assertTrue(window.verifyAllRestrictions(diceY, 0, 0));
        assertTrue(window.verifyAllRestrictions(dice2, 2, 0));
        assertTrue(window.verifyAllRestrictions(dice3, 1, 2));
    }
}