package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;


public class TCGrindingStone extends Card implements ToolCard {

    private boolean isUsed;
    private int calls = 2;
    private int flag = 1;
    private boolean forceTurn = true;

    public TCGrindingStone() {
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
    public int getCalls(){
        return calls;
    }

    @Override
    public boolean getForceTurn() {
        return forceTurn;
    }

    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        //arraylist in 0 la posizione del dado nella draft; in 1,2 le i,j della new pos
        //IN 0 (-1) PER ANNULLARE
        if (input.get(0) != -1) {
            if (flag == 1) {
                flag = 2;
                flipDice(gameModel.getField().getDraft().getDraft().get(input.get(0)));
                return true;
            } else if (flag == 2) {
                flag = 1;
                if ((gameModel.getActualPlayer().getWindow().verifyAllRestrictions(gameModel.getField().getDraft().getDraft().get(input.get(0)), input.get(1), input.get(2))) ||
                        (gameModel.getActualPlayer().getWindow().getIsEmpty() && gameModel.getActualPlayer().getWindow().verifyFirstDiceRestriction(gameModel.getField().getDraft().getDraft().get(input.get(0)), input.get(1), input.get(2)))) {
                    gameModel.getActualPlayer().pickDice(gameModel.getField().getDraft(), input.get(0));
                    return (gameModel.getActualPlayer().putDice(input.get(1), input.get(2)));
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