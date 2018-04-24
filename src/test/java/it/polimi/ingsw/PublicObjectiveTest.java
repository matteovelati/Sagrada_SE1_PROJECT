package it.polimi.ingsw;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.PublicObjective;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PublicObjectiveTest {

    private PublicObjective card1;

    @Before
    public void Before(){
        card1 = new PublicObjective(1);
    }
    @Test
    public void getScoreTest(){
        assertEquals(2 , card1.getScore());
    }
    @Test
    public void setScoreTest(){
        card1.setScore(324);
        assertEquals(324 , card1.getScore());
    }
    @Test
    public void getNameTest(){
        assertEquals("Light Shades" , card1.getName());
    }
    @Test
    public void setNameTest(){
        card1.setName("ingswpolimi2018");
        assertEquals("ingswpolimi2018" , card1.getName());
    }
    @Test
    public void getDescriptionTest(){
        assertEquals("Sets of 1 & 2 anywhere" , card1.getDescription());
    }
    @Test
    public void setDescriptionTest(){
        card1.setDescription("ciaone");
        assertEquals("ciaone" , card1.getDescription());
    }
    @Test
    public void getIdNumberTest(){
        assertEquals(1 , card1.getIdNumber());
    }
    @Test
    public void setIdNumberTest(){
        card1.setIdNumber(56);
        assertEquals(56 , card1.getIdNumber());
    }
    @Test
    public void getColorTest(){
        assertNull(card1.getColor());
    }
    @Test
    public void setColorTest(){
        card1.setColor(Colors.Y);
        assertEquals(Colors.Y, card1.getColor());
    }


}
