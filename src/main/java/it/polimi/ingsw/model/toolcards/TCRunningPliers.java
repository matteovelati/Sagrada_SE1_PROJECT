package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Draft;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.ToolCard;

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

    @Override
    public String getTitle(){
        return super.getName();
    }

    @Override
    public String getDescr(){
        return super.getDescription();
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


    private void draftDie(Player player, Draft draft, int i){
        player.pickDice(draft, i);
    }

}
