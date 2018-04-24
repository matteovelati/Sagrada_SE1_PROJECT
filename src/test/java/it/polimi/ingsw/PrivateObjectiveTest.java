package it.polimi.ingsw;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.PrivateObjective;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrivateObjectiveTest {

    private PrivateObjective obj1;
    private PrivateObjective obj2;
    private PrivateObjective obj3;
    private PrivateObjective obj4;
    private PrivateObjective obj5;

    @Before
    public void before(){
        obj1 = new PrivateObjective(Colors.Y);
        obj2 = new PrivateObjective(Colors.R);
        obj3 = new PrivateObjective(Colors.B);
        obj4 = new PrivateObjective(Colors.G);
        obj5 = new PrivateObjective(Colors.P);
    }

    @Test
    public void getColorTest(){
        assertEquals(Colors.Y, obj1.getColor());
        assertEquals(Colors.R, obj2.getColor());
        assertEquals(Colors.B, obj3.getColor());
        assertEquals(Colors.G, obj4.getColor());
        assertEquals(Colors.P, obj5.getColor());
    }

    @Test
    public void getNameTest(){
        assertEquals("Shades of Yellow", obj1.getName());
        assertEquals("Shades of Red", obj2.getName());
        assertEquals("Shades of Blue", obj3.getName());
        assertEquals("Shades of Green", obj4.getName());
        assertEquals("Shades of Purple", obj5.getName());
    }

    @Test
    public void getDescriptionTest(){
        assertEquals("Private sum of values on yellow dice\n", obj1.getDescription());
        assertEquals("Private sum of values on red dice\n", obj2.getDescription());
        assertEquals("Private sum of values on blue dice", obj3.getDescription());
        assertEquals("Private sum of values on green dice\n", obj4.getDescription());
        assertEquals("Private sum of values on purple dice\n", obj5.getDescription());
    }
}
