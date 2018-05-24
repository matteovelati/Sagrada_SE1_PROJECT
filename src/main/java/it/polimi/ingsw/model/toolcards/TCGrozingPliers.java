package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;


public class TCGrozingPliers extends Card implements ToolCard {

    private boolean isUsed;
    private int calls = 2;
    private int flag = 1;
    private boolean forceTurn = true;

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
    public boolean getForceTurn() {
        return forceTurn;
    }

    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        // arraylist: in 0 posizione dado draft; in 1 mettere '-1' per decreamentare; in 2,3 le i,j della new pos
        //IN 0 (-1) PER ANNULLARE
        boolean check;
        if (input.get(0) != -1) {
            if (flag == 1) {
                if (input.get(0) == -1)
                    check = (decreaseValue(gameModel.getField().getDraft().getDraft().get(input.get(1))));
                else
                    check = (increaseValue(gameModel.getField().getDraft().getDraft().get(input.get(1))));
                if (check) {
                    if (!getIsUsed())
                        setIsUsed(true);
                    flag = 2;
                    return true;
                } else
                    return false;
            } else if (flag == 2) {
                flag = 1;
                if (gameModel.getActualPlayer().getWindow().verifyAllRestrictions(gameModel.getField().getDraft().getDraft().get(input.get(0)), input.get(2), input.get(3))) {
                    gameModel.getActualPlayer().pickDice(gameModel.getField().getDraft(), input.get(0));
                    return (gameModel.getActualPlayer().putDice(input.get(2), input.get(3)));
                } else
                    return false;
            } else
                return false;
        }
        else {
            flag = 1;
            return false; //questo false NON deve richiamare il metodo
        }
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
