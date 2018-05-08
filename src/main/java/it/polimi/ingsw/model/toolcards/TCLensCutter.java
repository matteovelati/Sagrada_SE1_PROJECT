package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.model.Card;


public class TCLensCutter extends Card implements ToolCard   {

    private boolean isUsed;

    public TCLensCutter(int idNumber){
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(5);
        super.setColor(Colors.G);
        super.setName("Lens Cutter");
        super.setDescription("After drafting, swap the drafted die with a die from the Round Track.\n");
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

}
