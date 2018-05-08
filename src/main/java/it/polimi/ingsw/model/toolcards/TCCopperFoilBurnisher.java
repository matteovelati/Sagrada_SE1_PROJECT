package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.model.Card;


public class TCCopperFoilBurnisher extends Card implements ToolCard   {

    private boolean isUsed;

    public TCCopperFoilBurnisher(int idNumber){
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(3);
        super.setColor(Colors.R);
        super.setName("Copper Foil Burnisher");
        super.setDescription("Move any ONE die in your window ignoring value restrictions.\n");
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
