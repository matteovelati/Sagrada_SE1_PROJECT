package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class TCGlazingHammer extends Card implements ToolCard {

    private boolean isUsed;
    private Dice dicetmp;
    private int calls = 1;

    public TCGlazingHammer(){
        this.isUsed = false;
        super.setIdNumber(7);
        super.setColor(Colors.B);
        super.setName("Glazing Hammer");
        super.setDescription("Re-roll all dice in the Draft Pool." +
        " This may only be used on your second turn before drafting.\n");
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
    public int getCalls(){
        return calls;
    }

    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        if (gameModel.getRoundManager().getTurn() == 2 && gameModel.getRoundManager().getFirstMove() == 2 /*ha selezionato una toolcard*/){
            reRoll(gameModel.getField().getDraft());
            if(!getIsUsed())
                setIsUsed(true);
            return true;
        }
        else
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
