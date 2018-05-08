package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.model.Card;


public class TCGlazingHammer extends Card implements ToolCard{

    private boolean isUsed;

    public TCGlazingHammer(int idNumber){
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(7);
        super.setColor(Colors.B);
        super.setName("Glazing Hammer");
        super.setDescription("Re-roll all dice in the Draft Pool." +
        " This may only be used on your second turn before drafting.\n");
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
