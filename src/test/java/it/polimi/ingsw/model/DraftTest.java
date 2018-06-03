package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DraftTest {

    private Draft draft;
    private Bag bag;

    @Before
    public void setUp() throws Exception{
        draft = Draft.getInstance();
        bag = Bag.getInstance();
    }

    @Test
    public void getInstance() {
        assertEquals(draft, Draft.getInstance());
    }

    @Test
    public void setDraft() {
        assertTrue(draft.getDraft().isEmpty());
        draft.setDraft(bag);
        assertFalse(draft.getDraft().isEmpty());
    }
}