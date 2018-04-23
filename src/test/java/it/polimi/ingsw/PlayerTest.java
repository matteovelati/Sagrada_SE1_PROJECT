package it.polimi.ingsw;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import it.polimi.ingsw.model.Draft;
import it.polimi.ingsw.model.Player;
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

    @Before
    public void before(){
        player = new Player("zanga");
        dice = new Dice(Colors.G);
        draft = new Draft();
        draft.setDraft(dice);

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
