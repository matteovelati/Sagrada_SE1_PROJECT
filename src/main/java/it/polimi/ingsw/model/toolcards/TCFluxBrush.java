package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class  TCFluxBrush extends Card implements ToolCard {

    private boolean isUsed;
    private Dice dicetmp;
    private int calls = 2;
    private int flag = 1;
    private boolean forceTurn = true;

    public TCFluxBrush(){
        this.isUsed = false;
        super.setIdNumber(6);
        super.setColor(Colors.P);
        super.setName("Flux Brush");
        super.setDescription("After drafting, re-roll the drafted die.\nIf it cannot be placed, return it to the Draft Pool.\n");
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
        //arraylist in 0 indice dado draft; 1,2 le i,j della new pos
        //IN 0 (-1) PER ANNULLARE
        if (input.get(0) != -1) {
            if (flag == 1) {
                flag = 2;
                reRoll(gameModel.getField().getDraft(), input.get(0));
                if (!getIsUsed())
                    setIsUsed(true);
                return true;
            } else if (flag == 2) {
                flag = 1;
                if (gameModel.getActualPlayer().getWindow().verifyAllRestrictions(gameModel.getField().getDraft().getDraft().get(input.get(0)), input.get(1), input.get(2))) {
                    gameModel.getActualPlayer().pickDice(gameModel.getField().getDraft(), input.get(0));
                    return (gameModel.getActualPlayer().putDice(input.get(1), input.get(2)));
                }
                return false;
            }
            else
                return false;
        }
        else {
            flag = 1;
            return false; //questo false NON deve richiamare il metodo
        }
    }


    private void reRoll(Draft draft, int i){
        Random r = new Random();
        dicetmp = draft.getDraft().get(i);
        dicetmp.modifyValue(r.nextInt(6) + 1);
        draft.getDraft().set(i, dicetmp);
    }

}
