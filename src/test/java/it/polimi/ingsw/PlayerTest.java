package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PlayerTest {

    private Player player;
    private Draft draft;
    private Dice dice;
    private PrivateObjective privateObjective;

    @Before
    public void before(){
        player = new Player("zanga");
        dice = new Dice(Colors.G);
        draft = Draft.getInstance();
        draft.setDraft(dice);
        privateObjective = new PrivateObjective(Colors.G);

    }

    @Test
    public void setPrivateObjectiveTest(){
        player.setPrivateObjective(privateObjective);
        assertEquals(privateObjective, player.getPrivateObjective());
    }

    @Test
    public void setDiceTest(){
        player.setDice(dice);
        assertEquals(dice, player.getDice());
    }

    @Test
    public void usernameTest(){
        assertEquals("zanga", player.getUsername());
    }

    @Test
    public void pickDiceTest(){
        player.setDice(draft.extract(0));
        assertEquals(dice, player.getDice());
    }
}
