package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.util.ArrayList;


public class TCGrozingPliers extends ToolCard {

    private int flag = 1;

    public TCGrozingPliers(){
        super.setIdNumber(1);
        super.setColor(Colors.P);
        super.setName("Grozing Pliers");
        super.setDescription("After drafting, increase or decrease the value of the drafted die by ONE.\nONE may not change to SIX, or SIX to ONE.\n");
        super.setCalls(2);
        super.setForceTurn(true);
    }

    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        // arraylist: in 0 posizione dado draft; in 1 mettere '-2' per decreamentare; in 2,3 le i,j della new pos
        //IN 0 (-1) PER ANNULLARE
        boolean check;
        if (input.get(0) != -1) {
            if (flag == 1) {
                if (input.get(1) == -2)
                    check = (decreaseValue(gameModel.getField().getDraft().getDraft().get(input.get(0))));
                else
                    check = (increaseValue(gameModel.getField().getDraft().getDraft().get(input.get(0))));
                if (check) {
                    flag = 2;
                    return true;
                } else
                    return false;
            } else if (flag == 2) {
                flag = 1;
                return diePlacement(gameModel, input);
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
