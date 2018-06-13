package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BagTest {

    private Bag bag;

    @Before
    public void setUp(){
        Bag.reset();
        bag = Bag.getInstance();
    }

    @Test
    public void getInstance() {
        setUp();
        assertEquals(bag, Bag.getInstance());
    }

    @Test
    public void getBag() {
        setUp();
        assertEquals(90, bag.getBag().size());
    }

    @Test
    public void extract() {
        setUp();
        bag.extract(1);
        assertEquals(89, bag.getBag().size());
    }
}