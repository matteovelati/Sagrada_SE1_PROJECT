package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.model.Card;


public class TCEglomiseBrush extends Card implements ToolCard   {

    private boolean isUsed;

    public TCEglomiseBrush(int idNumber){
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(2);
        super.setColor(Colors.B);
        super.setName("Eglomise Brush");
        super.setDescription("Move any ONE die in your window ignoring color restrictions.\n");
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
