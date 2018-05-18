package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;


public class TCGrozingPliers extends Card implements ToolCard {

    private boolean isUsed;
    private int calls = 1;

    public TCGrozingPliers(){
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
    public int getCalls(){
        return calls;
    }

    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        // arraylist: in 0 mettere '-1' per decreamentare; in 1 posizione dado draft
        boolean check;
        if (input.get(0) == -1)
            check = (decreaseValue(gameModel.getField().getDraft().getDraft().get( input.get(1) )));
        else
            check = (increaseValue(gameModel.getField().getDraft().getDraft().get( input.get(1) )));
        if (check){
            if(!getIsUsed())
                setIsUsed(true);
            return true;
        }
        else
            return false;
    }

    private boolean increaseValue(Dice dice){
        if (dice.getValue() != 6) {
            return(dice.modifyValue(dice.getValue() + 1));
        }
        else
            return false;
    }

    private boolean decreaseValue(Dice dice){
        if (dice.getValue() != 1) {
            return(dice.modifyValue(dice.getValue() - 1));
        }
        else
            return false;
    }

}
