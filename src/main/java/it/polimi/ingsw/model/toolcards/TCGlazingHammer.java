package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Draft;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.util.ArrayList;
import java.util.Random;


public class TCGlazingHammer extends ToolCard {

    public TCGlazingHammer() {
        super.setIdNumber(7);
        super.setColor(Colors.B);
        super.setName("Glazing Hammer");
        super.setDescription("Re-roll all dice in the Draft Pool." +
                " This may only be used on your second turn before drafting.\n");
        super.setIsUsed(false);
        super.setCalls(1);
        super.setForceTurn(false);
    }

    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        if (gameModel.getRoundManager().getTurn() == 2 && gameModel.getRoundManager().getFirstMove() == 2 /*ha selezionato una toolcard*/){
            reRoll(gameModel.getField().getDraft());
            return true;
        }
        else
            return false;
    }


    private void reRoll(Draft draft){
        Random r = new Random();
        for (int i=0; i<draft.getDraft().size(); i++) {
            draft.getDraft().get(i).modifyValue(r.nextInt(6) + 1);
        }
    }
}
