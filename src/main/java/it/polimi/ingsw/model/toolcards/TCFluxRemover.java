package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.Random;

public class TCFluxRemover extends Card implements ToolCard {

    private boolean isUsed;
    private Dice dicetmp;
    private int calls = 3;
    private int flag = 1;
    private boolean forceTurn = true;


    public TCFluxRemover(){
        this.isUsed = false;
        super.setIdNumber(11);
        super.setColor(Colors.P);
        super.setName("Flux Remover");
        super.setDescription("After-drafting, return the die to the Dice Bag and pull ONE die from the bag.\nChoose a value and place the new dice, obeying all placement restrictions, or return it to the Draft Pool.\n");
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
        //arraylist in 0 indice i del dado nella draft, in 1 il nuovo valore, in 2,3 le i,j della new pos
        // IN 0 (-1) PER ANNULLARE
        if (input.get(0) != -1) {
            if (flag == 1) {
                flag = 2;
                mixDie(gameModel.getBag(), gameModel.getField().getDraft(), input.get(0));
                return true;
            } else if (flag == 2) {
                flag = 3;
                setDicetmp(gameModel.getField().getDraft(), input.get(0), input.get(1));
                if (!getIsUsed())
                    setIsUsed(true);
                return true;
            } else if (flag == 3) {
                flag = 1;
                if ((gameModel.getActualPlayer().getWindow().verifyAllRestrictions(gameModel.getField().getDraft().getDraft().get(input.get(0)), input.get(2), input.get(3))) ||
                        gameModel.getActualPlayer().getWindow().getIsEmpty() && gameModel.getActualPlayer().getWindow().verifyFirstDiceRestriction(gameModel.getField().getDraft().getDraft().get(input.get(0)), input.get(2), input.get(3))) {
                    gameModel.getActualPlayer().pickDice(gameModel.getField().getDraft(), input.get(0));
                    return (gameModel.getActualPlayer().putDice(input.get(2), input.get(3)));
                } else
                    return false;
            } else
                return false;
        }
        else {
            flag = 1;
            return false; //con questo false NON deve richiamare il metodo
        }
    }


    private void mixDie(Bag bag, Draft draft, int i){   //i posizione del dado nella draft
        Random r = new Random();
        dicetmp = draft.getDraft().remove(i);
        bag.getBag().add(dicetmp);
        dicetmp = bag.extract(r.nextInt(bag.getBag().size()));
        dicetmp.setValue();
        draft.getDraft().add(i, dicetmp);
    }

    private void setDicetmp(Draft draft, int i, int value){
        dicetmp = draft.getDraft().get(i);
        dicetmp.modifyValue(value);
    }
}
