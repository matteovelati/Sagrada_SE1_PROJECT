package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BagTest {

    private Bag bag;

    @Before
    public void setUp(){
        bag = Bag.getInstance();
    }



    @Test
    public void getInstance() {
        assertEquals(bag, Bag.getInstance());
    }

    @Test
    public void getBag() {
        assertEquals(90, bag.getBag().size());
    }

    @Test
    public void extract() {
        Dice dicetmp = bag.extract(1);
        assertEquals(89, bag.getBag().size());
        bag.getBag().add(dicetmp);
    }
}