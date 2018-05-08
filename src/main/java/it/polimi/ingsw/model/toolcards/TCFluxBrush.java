package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.model.Card;


public class  TCFluxBrush extends Card implements ToolCard   {

    private boolean isUsed;

    public TCFluxBrush(int idNumber){
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(6);
        super.setColor(Colors.P);
        super.setName("Flux Brush");
        super.setDescription("After drafting, re-roll the drafted die.\nIf it cannot be placed, return it to the Draft Pool.\n");
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed){
        this.isUsed = isUsed;
    }

    @Override
    public boolean useToolCard() {
        return false;
    }

}
