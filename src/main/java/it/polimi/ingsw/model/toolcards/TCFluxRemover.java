package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.model.Card;


public class TCFluxRemover extends Card implements ToolCard   {

    private boolean isUsed;

    public TCFluxRemover(int idNumber){
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(11);
        super.setColor(Colors.P);
        super.setName("Flux Remover");
        super.setDescription("After-drafting, return the die to the Dice Bag and pull ONE die from the bag.\nChoose a value and place the new dice, obeying all placement restrictions, or return it to the Draft Pool.\n");
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

    @Override
    public int getNumber(){
        return super.getIdNumber();
    }

}
