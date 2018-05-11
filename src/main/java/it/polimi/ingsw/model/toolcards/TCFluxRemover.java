package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import it.polimi.ingsw.model.Draft;
import it.polimi.ingsw.model.ToolCard;

import java.util.Random;

public class TCFluxRemover extends Card implements ToolCard   {

    private boolean isUsed;
    private Dice dicetmp;

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
    public boolean useToolCard() {
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
