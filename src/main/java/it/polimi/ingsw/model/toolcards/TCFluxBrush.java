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

    /**
     * creates a toolcard setting idnumber, color, name, description
     */
    public TCFluxBrush(){
        super.setIdNumber(6);
        super.setColor(Colors.P);
        super.setName("Flux Brush");
        super.setDescription("After drafting, re-roll the drafted die.\nIf it cannot be placed, return it to the Draft Pool.\n");
        super.setCalls(2);
        super.setForceTurn(true);
    }

    /**
     * roll again a die of the draft, setting a new random value, and puts the die in the window
     * @param gameModel the gamemodel of the match
     * @param input a list of integer that represents the client's inputs
     *              in [0] the index of the die in the draft
     *              in [1],[2] the i,j of the new position of the die
     *              IN [0] '-1' TO UNDO
     * @return true if the toolcard has been used correctly, false otherwise
     */
    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        if (input.get(0) != -1) {
            if (flag == 1) {
                flag = 2;
                reRoll(gameModel.getField().getDraft(), input.get(0));
                return true;
            } else if (flag == 2) {
                if ((gameModel.getActualPlayer().getWindow().verifyAllRestrictions(gameModel.getField().getDraft().getDraft().get(input.get(0)), input.get(1), input.get(2))) ||
                        (gameModel.getActualPlayer().getWindow().getIsEmpty() && gameModel.getActualPlayer().getWindow().verifyFirstDiceRestriction(gameModel.getField().getDraft().getDraft().get(input.get(0)), input.get(1), input.get(2)))) {
                    flag = 1;
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
            return false; 
        }
    }

    /**
     * randomly set a new value to a die in the draft
     * @param draft the draft of the game
     * @param i the index of the die in the draft
     */
    private void reRoll(Draft draft, int i){
        Random r = new Random();
        Dice dicetmp = draft.getDraft().get(i);
        dicetmp.modifyValue(r.nextInt(6) + 1);
        draft.getDraft().set(i, dicetmp);
    }

}
