package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.Random;

public class TCFluxRemover extends Card implements ToolCard   {

    private boolean isUsed;
    private Dice dicetmp;
    private int calls = 2;
    private int flag = 1;


    public TCFluxRemover(int idNumber){
        super(idNumber);
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
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        //arraylist in 0 indice i del dado nella draft, in 1 il nuovo valore, in 2,3 le i,j della new pos
        boolean check;
        if (flag == 1) {
            flag = 2;
            secondChance(gameModel.getBag(), gameModel.getField().getDraft(), input.get(0), input.get(1));
            return true;
        }
        else if (flag == 2){
            flag = 1;
            gameModel.getActualPlayer().pickDice(gameModel.getField().getDraft(), input.get(0));
            check = gameModel.getActualPlayer().putDice(input.get(2), input.get(3));
            if (check) {
                if(!getIsUsed())
                    setIsUsed(true);
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }


    private void secondChance(Bag bag, Draft draft, int i, int value){   //i posizione del dado nella draft

        Random r = new Random();
        dicetmp = draft.getDraft().get(i);
        bag.getBag().add(dicetmp);
        dicetmp = bag.extract((r.nextInt(bag.getBag().size()) + 1));
        dicetmp.modifyValue(value);
        draft.getDraft().set(i, dicetmp);

        //implemento qua o nella classe chiamante l'azione di inserire il dado?
    }
}
