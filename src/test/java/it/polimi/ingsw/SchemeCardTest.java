package it.polimi.ingsw;

import it.polimi.ingsw.model.SchemeCard;
import it.polimi.ingsw.model.Window;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SchemeCardTest {

    private SchemeCard card1;
    private SchemeCard card2;
    private Window front1;
    private Window back1;
    private Window front2;
    private Window back2;

    @Before
    public void before(){
        front1 = new Window("Bellesguard");
        back1 = new Window("Battlo");
        front2 = new Window("Chromatic Splendor");
        back2 = new Window("Comitas");
        card1 = new SchemeCard(front1, back1);
        card2 = new SchemeCard(front2, back2);
    }

    @Test
    public void testGetFront(){
        assertEquals(front1, card1.getFront());
        assertEquals(front2, card2.getFront());
    }

    @Test
    public void testGetBack(){
        assertEquals(back1, card1.getBack());
        assertEquals(back2, card2.getBack());
    }
}
