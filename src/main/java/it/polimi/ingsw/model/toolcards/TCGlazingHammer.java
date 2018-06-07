package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Draft;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.util.ArrayList;
import java.util.Random;


public class TCGlazingHammer extends ToolCard {

    /**
     * creates a toolcard setting idnumber, color, name and description
     */
    public TCGlazingHammer() {
        super.setIdNumber(7);
        super.setColor(Colors.B);
        super.setName("Glazing Hammer");
        super.setDescription("Re-roll all dice in the Draft Pool." +
                " This may only be used on your second turn before drafting.\n");
    }

    /**
     * rolls again all the dice in the draft setting a new random value for each die
     * @param gameModel the gamemodel of the match
     * @param input a list of integer that represents the client's inputs
     * @return true if the toolcard has been used correctly, false otherwise
     */
    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        reRoll(gameModel.getField().getDraft());
        return true;
    }

    /**
     * check if the actualplayer's turn is 2 and his first move was 2
     * @param gameModel the gamemodel of the match
     * @return true if both conditions are true, false otherwise
     */
    @Override
    public boolean select(GameModel gameModel){
        return (gameModel.getRoundManager().getTurn()==2 && gameModel.getRoundManager().getFirstMove() == 2);
    }

    /**
     * rolls again all the dice in the draft, modifying the value for each die
     * @param draft the draft containing the dice
     */
    private void reRoll(Draft draft){
        Random r = new Random();
        for (int i=0; i<draft.getDraft().size(); i++) {
            draft.getDraft().get(i).modifyValue(r.nextInt(6) + 1);
        }
    }
}

