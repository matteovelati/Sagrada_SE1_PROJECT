package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;


public class TCLensCutter extends Card implements ToolCard {

    private boolean isUsed;
    private Dice dicetmp;
    private int calls = 1;

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
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        //arraylist in 0 indice dado draft, in 1 indice dado roundtrack
        swapDice(gameModel.getField().getRoundTrack(), input.get(1), gameModel.getField().getDraft(), input.get(0));
        if(!getIsUsed())
            setIsUsed(true);
        return true;
    }


    private void swapDice(RoundTrack grid, int i, Draft draft, int j){  //i indice roundtrack, j indice draft
        dicetmp = grid.changeDice(i, draft.getDraft().get(j));
        draft.getDraft().set(j, dicetmp);
    }
}
