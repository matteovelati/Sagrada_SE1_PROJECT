package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.util.ArrayList;


public class TCGrindingStone extends ToolCard {

    private int flag = 1;

    /**
     * creates a toolcard setting idnumber, color, name, description, number of calls and force turn
     */
    public TCGrindingStone() {
        super.setIdNumber(10);
        super.setColor(Colors.G);
        super.setName("Grinding Stone");
        super.setDescription("After drafting, flip the die to its opposite side.\n");
        super.setCalls(2);
        super.setForceTurn(true);
    }

    /**
     * flips a die of the draft to his opposite side, changing the value from x to (7-x), and puts the die in player's window
     * @param gameModel the gamemodel of the match
     * @param input a list of integer that represents the client's inputs
     * @return true if the toolcard has been used correctly, false otherwise
     */
    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        //arraylist in 0 la posizione del dado nella draft; in 1 niente; in 2,3 le i,j della new pos
        //IN 0 (-1) PER ANNULLARE
        if (input.get(0) != -1) {
            if (flag == 1) {
                flag = 2;
                flipDice(gameModel.getField().getDraft().getDraft().get(input.get(0)));
                return true;
            } else if (flag == 2) {
                if (diePlacement(gameModel, input)){
                    flag = 1;
                    return true;
                }
                else
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
     * flips the die to his opposite side, changing the value from x to (7-x)
     * @param dice the die to be flipped
     */
    private void flipDice(Dice dice){
        dice.modifyValue( 7-dice.getValue() );
    }

}