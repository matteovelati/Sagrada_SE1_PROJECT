package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DraftTest {

    private Draft draft;
    private Bag bag;

    @Before
    public void setUp(){
        Bag.reset();
        Draft.reset();
        draft = Draft.getInstance();
        bag = Bag.getInstance();
    }

    @Test
    public void getInstance() {
        setUp();
        assertEquals(draft, Draft.getInstance());
    }

    @Test
    public void setDraft() {
        setUp();
        assertTrue(draft.getDraft().isEmpty());
        draft.setDraft(bag);
        assertFalse(draft.getDraft().isEmpty());
        draft.extract(0);
        assertTrue(draft.getDraft().isEmpty());
    }
}