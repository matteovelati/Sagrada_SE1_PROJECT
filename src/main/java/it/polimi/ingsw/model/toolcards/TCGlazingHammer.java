package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import it.polimi.ingsw.model.Draft;
import it.polimi.ingsw.model.ToolCard;

import java.util.Random;


public class TCGlazingHammer extends Card implements ToolCard{

    private boolean isUsed;
    private Dice dicetmp;

    public TCGlazingHammer(int idNumber){
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(7);
        super.setColor(Colors.B);
        super.setName("Glazing Hammer");
        super.setDescription("Re-roll all dice in the Draft Pool." +
        " This may only be used on your second turn before drafting.\n");
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


    private void reRoll(Draft draft){
        Random r = new Random();
        for (int i=0; i<draft.getDraft().size(); i++) {
            dicetmp = draft.getDraft().get(i);
            dicetmp.modifyValue(r.nextInt(6) + 1);
            draft.getDraft().set(i, dicetmp);
        }
    }
}
