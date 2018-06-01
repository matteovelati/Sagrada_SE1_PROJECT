package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;


public class TCLensCutter extends ToolCard {

    private int flag = 1;

    public TCLensCutter(){
        super.setIdNumber(5);
        super.setColor(Colors.G);
        super.setName("Lens Cutter");
        super.setDescription("After drafting, swap the drafted die with a die from the Round Track.\n");
        super.setCalls(2);
        super.setForceTurn(true);
    }

    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        //arraylist in 0 indice dado draft, in 1 indice dado roundtrack, in 2,3 le i,j della new pos
        //IN 0 (-1) PER ANNULLARE
        if (input.get(0) != -1) {
            if (flag == 1) {
                flag = 2;
                swapDice(gameModel.getField().getRoundTrack(), input.get(1), gameModel.getField().getDraft(), input.get(0));
                return true;
            } if (flag == 2) {
                flag = 1;
                return diePlacement(gameModel, input);
            } else
                return false;
        }
        else {
            flag = 1;
            return false; //questo false NON deve richiamare il metodo
        }
    }


    private void swapDice(RoundTrack grid, int i, Draft draft, int j){  //i indice roundtrack, j indice draft
        Dice dicetmp = grid.changeDice(i, draft.getDraft().get(j));
        draft.getDraft().set(j, dicetmp);
    }
}
