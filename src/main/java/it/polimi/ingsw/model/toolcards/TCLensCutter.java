package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import it.polimi.ingsw.model.Draft;
import it.polimi.ingsw.model.RoundTrack;
import it.polimi.ingsw.model.ToolCard;


public class TCLensCutter extends Card implements ToolCard   {

    private boolean isUsed;
    private Dice dicetmp;

    public TCLensCutter(int idNumber){
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(5);
        super.setColor(Colors.G);
        super.setName("Lens Cutter");
        super.setDescription("After drafting, swap the drafted die with a die from the Round Track.\n");
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
    public boolean useToolCard() {
        return false;
    }


    private void swapDice(RoundTrack grid, int i, Draft draft, int j){  //i indice roundtrack, j indice draft
        dicetmp = grid.changeDice(i, draft.getDraft().get(j));
        draft.getDraft().set(j, dicetmp);
    }
}
