package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class ChangePlayerTest {

    @Test
    public void clockwise() {
        assertEquals(0, ChangePlayer.clockwise(0,1));
        assertEquals(1, ChangePlayer.clockwise(0,2));
    }

    @Test
    public void antiClockwise() {
        assertEquals(3, ChangePlayer.antiClockwise(0,4));
        assertEquals(1, ChangePlayer.antiClockwise(2, 0));
    }
}