package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.model.Card;


public class  TCTapWheel extends Card implements ToolCard   {

    private boolean isUsed;

    public TCTapWheel(int idNumber){
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(12);
        super.setColor(Colors.B);
        super.setName("Tap Wheel");
        super.setDescription("Move up to TWO dice of the same color that match the color of a die on the Round Track.\nYou must obey all placement restrictions.\n");
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
