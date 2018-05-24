package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;

public class TCRunningPliers extends Card implements ToolCard {

    private boolean isUsed;
    private int calls = 2;
    private int flag = 1;
    private boolean forceTurn = false;

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
    public boolean getForceTurn() {
        return forceTurn;
    }

    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        //arraylist in 0 indice dado draft; in 1,2 le i,j della new pos
        //IN 0 (-1) PER ANNULLARE
        if (input.get(0) != -1) {
            if (flag == 1) {
                if (gameModel.getRoundManager().getTurn() == 1 && gameModel.getRoundManager().getFirstMove() == 1 /*ha selezionato un dado*/) {
                    draftDie(gameModel.getActualPlayer(), gameModel.getField().getDraft(), input.get(0));
                    if (!getIsUsed())
                        setIsUsed(true);
                    flag = 2;
                    return true;
                } else
                    return false;
            }
            if (flag == 2){
                flag = 1;
                if (gameModel.getActualPlayer().getWindow().verifyAllRestrictions(gameModel.getField().getDraft().getDraft().get(input.get(0)), input.get(1), input.get(2))) {
                    gameModel.getActualPlayer().pickDice(gameModel.getField().getDraft(), input.get(0));
                    return (gameModel.getActualPlayer().putDice(input.get(2), input.get(3)));
                } else
                    return false;
            }
            else
                return false;
        }
        else {
            flag = 1;
            return false;   //con questo false NON deve richiamare il metodo
        }
    }


    private void draftDie(Player player, Draft draft, int i){
        player.pickDice(draft, i);
    }

}
