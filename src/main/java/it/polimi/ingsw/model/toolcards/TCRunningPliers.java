package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.model.Card;


public class TCRunningPliers extends Card implements ToolCard   {
    private boolean isUsed;

    public TCRunningPliers(int idNumber){
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(8);
        super.setColor(Colors.R);
        super.setName("Running Pliers");
        super.setDescription("After your first turn, immediately draft a die." +
            " Skip your next turn this round.\n");
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
