package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BagTest {

    @Test
    public void getInstance() {
        Bag bag = Bag.getInstance();
        assertEquals(bag, Bag.getInstance());
    }

    @Test
    public void getBag() {
        Bag bag = Bag.getInstance();
        assertEquals(90, bag.getBag().size());
    }

    @Test
    public void extract() {
        Bag bag = Bag.getInstance();
        bag.extract(1);
        assertEquals(89, bag.getBag().size());
    }
}