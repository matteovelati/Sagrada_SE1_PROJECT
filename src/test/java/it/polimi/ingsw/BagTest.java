package it.polimi.ingsw;

import it.polimi.ingsw.model.Bag;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BagTest {

    private Bag bag1;
    private Bag bag2;

    @Before
    public void before(){
        bag1 = Bag.getInstance();
    }

    @Test
    public void testGetInstance(){
        bag2 = Bag.getInstance();
        assertEquals(bag1, bag2);
    }
}
