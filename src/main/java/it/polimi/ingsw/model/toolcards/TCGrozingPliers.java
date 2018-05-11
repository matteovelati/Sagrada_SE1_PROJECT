package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import it.polimi.ingsw.model.ToolCard;


public class TCGrozingPliers extends Card implements ToolCard   {

    private boolean isUsed;

    public TCGrozingPliers(int idNumber){
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(1);
        super.setColor(Colors.P);
        super.setName("Grozing Pliers");
        super.setDescription("After drafting, increase or decrease the value of the drafted die by ONE.\nONE may not change to SIX, or SIX to ONE.\n");
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

    private boolean increaseValue(Dice dice){
        if (dice.getValue() != 6) {
            dice.modifyValue(dice.getValue() + 1);
            return true;
        }
        else
            return false;
    }
    private boolean decreaseValue(Dice dice){
        if (dice.getValue() != 1) {
            dice.modifyValue(dice.getValue() - 1);
            return true;
        }
        else
            return false;
    }

}
