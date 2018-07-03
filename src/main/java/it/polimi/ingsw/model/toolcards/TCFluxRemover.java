package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.Random;

public class TCFluxRemover extends ToolCard {

    private int flag = 1;

    /**
     * creates a toolcard setting idnumber, color, name, effect, number of calls and force turn
     */
    public TCFluxRemover(){
        super.setIdNumber(11);
        super.setColor(Colors.P);
        super.setName("Flux Remover");
        super.setDescription("After-drafting, return the die to the Dice Bag and pull ONE die from the bag.\nChoose a value and place the new dice, obeying all placement restrictions, or return it to the Draft Pool.\n");
        super.setCalls(3);
        super.setForceTurn(true);
    }

    /**
     * extracts a new die from the bag choosing the value and puts the die in the window
     * @param gameModel the gamemodel of the match
     * @param input a list of integer that represents the client's inputs
     *              in [0] the index of the die in the draft
     *              in [1] the new value to be set
     *              in [2],[3] the i,j of the new position of the die
     *              IN [0] '-1' TO UNDO
     * @return true if the toolcard has been used correctly, false otherwise
     */
    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        if (input.get(0) != -1) {
            if (flag == 1) {
                flag = 2;
                mixDie(gameModel.getBag(), gameModel.getField().getDraft(), input.get(0));
                return true;
            } else if (flag == 2) {
                flag = 3;
                setDicetmp(gameModel.getField().getDraft(), input.get(0), input.get(1));
                return true;
            } else if (flag == 3) {
                if ((gameModel.getActualPlayer().getWindow().verifyAllRestrictions(gameModel.getField().getDraft().getDraft().get(input.get(0)), input.get(2), input.get(3))) ||
                        gameModel.getActualPlayer().getWindow().getIsEmpty() && gameModel.getActualPlayer().getWindow().verifyFirstDiceRestriction(gameModel.getField().getDraft().getDraft().get(input.get(0)), input.get(2), input.get(3))) {
                    flag = 1;
                    gameModel.getActualPlayer().pickDice(gameModel.getField().getDraft(), input.get(0));
                    return (gameModel.getActualPlayer().putDice(input.get(2), input.get(3)));
                }
                return false;
            } else
                return false;
        }
        else {
            flag = 1;
            return false;
        }
    }

    /**
     * adds a die extracted from the draft at index i to the bag
     * extracts a new die from the bag and put it in the draft
     * @param bag the bag od the match
     * @param draft the draft of the match
     * @param i the index of the die to be extracted from the draft
     */
    private void mixDie(Bag bag, Draft draft, int i){
        Random r = new Random();
        Dice dicetmp = draft.getDraft().remove(i);
        bag.getBag().add(dicetmp);
        dicetmp = bag.extract(r.nextInt(bag.getBag().size()));
        dicetmp.setValue();
        draft.getDraft().add(i, dicetmp);
    }

    /**
     * modifies the value of a die in the draft
     * @param draft the draft of the match
     * @param i the index of the die in the draft
     * @param value the new value to be set to the die
     */
    private void setDicetmp(Draft draft, int i, int value){
        Dice dicetmp = draft.getDraft().get(i);
        dicetmp.modifyValue(value);
    }
}
