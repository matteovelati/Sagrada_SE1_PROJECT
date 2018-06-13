package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class FieldTest {

    private Field field;

    @Before
    public void setUp() {
        Bag.reset();
        RoundTrack.reset();
        Draft.reset();
        RoundManager.reset();
        Field.reset();
        field = Field.getInstance(0);
    }

    @Test
    public void getInstance() {
        setUp();
        assertEquals(field, Field.getInstance(0));
        assertEquals(field.getDraft(), Draft.getInstance());
        assertEquals(field.getRoundTrack(), RoundTrack.getInstance());
        assertEquals(3, field.getToolCards().size());
        assertEquals(3, field.getPublicObjectives().size());
    }

}