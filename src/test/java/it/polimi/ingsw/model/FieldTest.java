package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class FieldTest {

    private Field field;

    @Before
    public void setUp() {
        field = Field.getInstance();
    }

    @Test
    public void getInstance() {
        assertEquals(field, Field.getInstance());
        assertEquals(field.getDraft(), Draft.getInstance());
        assertEquals(field.getRoundTrack(), RoundTrack.getInstance());
        assertEquals(3, field.getToolCards().size());
        assertEquals(3, field.getPublicObjectives().size());
    }

}