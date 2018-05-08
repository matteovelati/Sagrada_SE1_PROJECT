package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.model.Card;


public class TCCorkbackedStraightedge extends Card implements ToolCard   {

    private boolean isUsed;

    public TCCorkbackedStraightedge(int idNumber){
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(9);
        super.setColor(Colors.Y);
        super.setName("Cork-backed Straightedge");
        super.setDescription("After drafting, place the die in the spot that is not adjacent to another die.\n");
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
