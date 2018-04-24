package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FieldTest {

    private Field field;
    private ToolCard[] toolCards;
    private PublicObjective[] publicObjectives;

    @Before
    public void before(){
        toolCards = new ToolCard[3];
        toolCards[0] = new ToolCard(1);
        toolCards[1] = new ToolCard(2);
        toolCards[2] = new ToolCard(3);
        publicObjectives = new PublicObjective[3];
        publicObjectives[0] = new PublicObjective(1);
        publicObjectives[1] = new PublicObjective(2);
        publicObjectives[2] = new PublicObjective(3);
        field = Field.getInstance(toolCards, publicObjectives);
    }

    @Test
    public void getRoundTrackTest(){
        assertEquals(RoundTrack.getInstance(), field.getRoundTrack());
    }

    @Test
    public void getDraftTest(){
        assertEquals(Draft.getInstance(), field.getDraft());
    }

    @Test
    public void getToolCardTest(){
        assertEquals(toolCards, field.getToolCard());
    }

    @Test
    public void getPublicObjectiveTest(){
        assertEquals(publicObjectives, field.getPublicObjectives());
    }
}
