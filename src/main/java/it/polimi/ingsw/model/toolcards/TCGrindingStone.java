package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import it.polimi.ingsw.model.ToolCard;


public class TCGrindingStone extends Card implements ToolCard {

    private boolean isUsed;

    public TCGrindingStone(int idNumber) {
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(10);
        super.setColor(Colors.G);
        super.setName("Grinding Stone");
        super.setDescription("After drafting, flip the die to its opposite side.\n");
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


    private void flipDice(Dice dice){
        switch(dice.getValue()){
            case 1:
                dice.modifyValue(6);
                break;
            case 2:
                dice.modifyValue(5);
                break;
            case 3:
                dice.modifyValue(4);
                break;
            case 4:
                dice.modifyValue(3);
                break;
            case 5:
                dice.modifyValue(2);
                break;
            case 6:
                dice.modifyValue(1);
                break;
            default: //eccezione numero
                break;

        }

    }

}