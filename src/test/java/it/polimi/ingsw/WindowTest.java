package it.polimi.ingsw;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import it.polimi.ingsw.model.Space;
import it.polimi.ingsw.model.Window;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WindowTest {

    private Window window;
    private Space[][] pattern;
    private Dice dice;

    @Before
    public void Before(){
        window = new Window(1);
        dice = new Dice(Colors.B);
    }

    @Test
    public void getDifficultyTest(){
        assertEquals(3, window.getDifficulty());
    }
    @Test
    public void getNameTest(){
        assertEquals("Bellesguard", window.getName());
    }
    @Test
    public void getPatternTest(){
        for (int i=0; i<4; i++){
            for (int j=0; j<5; j++){
                assertTrue(!window.getPattern()[i][j].equals(null));

            }
        }
    }
    @Test
    public void setPatternTest(){
        window.setPattern(dice, 0 ,0);
        assertEquals(window.getPattern()[0][0].getColor(), dice.getColor());
    }


}
