package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class TCRunningPliers extends Card implements ToolCard   {

    private boolean isUsed;
    private int calls = 1;

    public TCRunningPliers(){
        this.isUsed = false;
        super.setIdNumber(8);
        super.setColor(Colors.R);
        super.setName("Running Pliers");
        super.setDescription("After your first turn, immediately draft a die." +
            " Skip your next turn this round.\n");
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
        //arraylist in 0 indice dado draft
        if (gameModel.getRoundManager().getTurn() == 1 && gameModel.getRoundManager().getFirstMove() == 1 /*ha selezionato un dado*/){
            draftDie(gameModel.getActualPlayer(), gameModel.getField().getDraft(), input.get(0));
            if(!getIsUsed())
                setIsUsed(true);
            return true;
        }
        else
            return false;
    }


    private void draftDie(Player player, Draft draft, int i){
        player.pickDice(draft, i);
    }

}
