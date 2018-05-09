package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;
import java.util.Random;


public class  TCFluxBrush extends Card implements ToolCard   {

    private boolean isUsed;
    private Dice dicetmp;

    public TCFluxBrush(int idNumber){
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(6);
        super.setColor(Colors.P);
        super.setName("Flux Brush");
        super.setDescription("After drafting, re-roll the drafted die.\nIf it cannot be placed, return it to the Draft Pool.\n");
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


    private void reRoll(Draft draft, int i){
        Random r = new Random();
        dicetmp = draft.getDraft().get(i);
        dicetmp.modifyValue(r.nextInt(6) + 1);
        draft.getDraft().set(i, dicetmp);
    }

}
