package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;


public class TCLensCutter extends Card implements ToolCard {

    private boolean isUsed;
    private Dice dicetmp;
    private int calls = 2;
    private int flag = 1;
    private boolean forceTurn = true;

    public TCLensCutter(){
        this.isUsed = false;
        super.setIdNumber(5);
        super.setColor(Colors.G);
        super.setName("Lens Cutter");
        super.setDescription("After drafting, swap the drafted die with a die from the Round Track.\n");
    }

    @Override
    public String getTitle(){
        return super.getName();
    }

    @Override
    public String getDescr(){
        return super.getDescription();
    }

    @Override
    public int getNumber(){
        return super.getIdNumber();
    }

    @Override
    public boolean getIsUsed() {
        return isUsed;
    }

    @Override
    public void setIsUsed(boolean isUsed){
        this.isUsed = isUsed;
    }

    @Override
    public int getCalls(){
        return calls;
    }

    @Override
    public boolean getForceTurn() {
        return forceTurn;
    }

    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        //arraylist in 0 indice dado draft, in 1 indice dado roundtrack, in 2,3 le i,j della new pos
        //IN 0 (-1) PER ANNULLARE
        if (input.get(0) != -1) {
            if (flag == 1) {
                flag = 2;
                swapDice(gameModel.getField().getRoundTrack(), input.get(1), gameModel.getField().getDraft(), input.get(0));
                if (!getIsUsed())
                    setIsUsed(true);
                return true;
            } if (flag == 2) {
                flag = 1;
                if ((gameModel.getActualPlayer().getWindow().verifyAllRestrictions(gameModel.getField().getDraft().getDraft().get(input.get(0)), input.get(2), input.get(3))) ||
                        (gameModel.getActualPlayer().getWindow().getIsEmpty() && gameModel.getActualPlayer().getWindow().verifyFirstDiceRestriction(gameModel.getField().getDraft().getDraft().get(input.get(0)), input.get(2), input.get(3)))){
                    gameModel.getActualPlayer().pickDice(gameModel.getField().getDraft(), input.get(0));
                    return (gameModel.getActualPlayer().putDice(input.get(2), input.get(3)));
                } else
                    return false;
            } else
                return false;
        }
        else {
            flag = 1;
            return false; //questo false NON deve richiamare il metodo
        }
    }


    private void swapDice(RoundTrack grid, int i, Draft draft, int j){  //i indice roundtrack, j indice draft
        dicetmp = grid.changeDice(i, draft.getDraft().get(j));
        draft.getDraft().set(j, dicetmp);
    }
}
