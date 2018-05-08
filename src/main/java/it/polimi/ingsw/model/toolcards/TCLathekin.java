package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.model.Card;


public class TCLathekin extends Card implements ToolCard   {

    private boolean isUsed;

    public TCLathekin(int idNumber){
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(4);
        super.setColor(Colors.Y);
        super.setName("Lathekin");
        super.setDescription("Move exactly TWO dice, obeying all placement restrictions.\n");
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
