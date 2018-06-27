package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.util.ArrayList;


public class TCGrozingPliers extends ToolCard {

    private int flag = 1;

    /**
     * create a toolcard setting idnumber, color, name, description, calls and force turn
     */
    public TCGrozingPliers(){
        super.setIdNumber(1);
        super.setColor(Colors.P);
        super.setName("Grozing Pliers");
        super.setDescription("After drafting, increase or decrease the value of the drafted die by ONE.\nONE may not change to SIX, or SIX to ONE.\n");
        super.setCalls(2);
        super.setForceTurn(true);
    }

    /**
     * increases or decreases a die of the draft by one and puts the die in player's window
     * @param gameModel the gamemodel of the match
     * @param input a list of integer that represents the client's inputs
     * @return true if the toolcard has been used correctly, false otherwise
     */
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
                if (diePlacement(gameModel, input)){
                    flag = 1;
                    return true;
                }
                return false;
            } else
                return false;
        }
        else {
            flag = 1;
            return false; //questo false NON deve richiamare il metodo
        }
    }

    /**
     * increases the value of a die if it's not equals to 6
     * @param dice the die to be increased
     * @return true if the die's value has been changed, false otherwise
     */
    private boolean increaseValue(Dice dice){
        if (dice.getValue() != 6) {
            dice.modifyValue(dice.getValue() + 1);
            return true;
        }
        else
            return false;
    }

    /**
     * decreases the value of a die if it's not equals to 1
     * @param dice the die to be decreased
     * @return true if the die's has been changed, false otherwise
     */
    private boolean decreaseValue(Dice dice){
        if (dice.getValue() != 1) {
            dice.modifyValue(dice.getValue() - 1);
            return true;
        }
        else
            return false;
    }

}
