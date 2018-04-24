package it.polimi.ingsw;

import it.polimi.ingsw.model.ToolCard;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ToolCardTest {

    private ToolCard card;

    @Before
    public void before() {
        card = new ToolCard(3);
    }

    @Test
    public void getIsUsedTest(){
        assertFalse(card.getIsUsed());
    }

    @Test
    public void setIsUsedTest(){
        card.setIsUsed(true);
        assertTrue(card.getIsUsed());
    }

}
