package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import it.polimi.ingsw.model.Draft;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.util.ArrayList;
import java.util.Random;


public class  TCFluxBrush extends ToolCard {

    private int flag = 1;

    public TCFluxBrush(){
        super.setIdNumber(6);
        super.setColor(Colors.P);
        super.setName("Flux Brush");
        super.setDescription("After drafting, re-roll the drafted die.\nIf it cannot be placed, return it to the Draft Pool.\n");
        super.setCalls(2);
        super.setForceTurn(true);
    }

    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        //arraylist in 0 indice dado draft; 1,2 le i,j della new pos
        //IN 0 (-1) PER ANNULLARE
        if (input.get(0) != -1) {
            if (flag == 1) {
                flag = 2;
                reRoll(gameModel.getField().getDraft(), input.get(0));
                return true;
            } else if (flag == 2) {
                flag = 1;
                if ((gameModel.getActualPlayer().getWindow().verifyAllRestrictions(gameModel.getField().getDraft().getDraft().get(input.get(0)), input.get(1), input.get(2))) ||
                        (gameModel.getActualPlayer().getWindow().getIsEmpty() && gameModel.getActualPlayer().getWindow().verifyFirstDiceRestriction(gameModel.getField().getDraft().getDraft().get(input.get(0)), input.get(1), input.get(2)))) {
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
        Dice dicetmp = draft.getDraft().get(i);
        dicetmp.modifyValue(r.nextInt(6) + 1);
        draft.getDraft().set(i, dicetmp);
    }

}
