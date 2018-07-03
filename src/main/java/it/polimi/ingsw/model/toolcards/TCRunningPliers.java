package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import it.polimi.ingsw.model.Draft;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.model.Window;

import java.util.ArrayList;

public class TCRunningPliers extends ToolCard {

    /**
     * creates a toolcard setting idnumber, color, name and description
     */
    public TCRunningPliers(){
        super.setIdNumber(8);
        super.setColor(Colors.R);
        super.setName("Running Pliers");
        super.setDescription("After your first turn, immediately draft a die." +
                " Skip your next turn this round.\n");
    }

    /**
     * allows the player to draft a second die in it's first turn, skipping the second one
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
            if (placeDice(gameModel.getActualPlayer().getWindow(), input.get(1), input.get(2), gameModel.getField().getDraft(), input.get(0))){
                gameModel.getActualPlayer().setSkipNextTurn(true);
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    /**
     * check if the actual player's turn is 1 and his first move was 1
     * @param gameModel the gamemodel of the match
     * @return true if the conditions are both true, false otherwise
     */
    @Override
    public boolean select(GameModel gameModel){
        return (gameModel.getRoundManager().getTurn()==1 && gameModel.getRoundManager().getFirstMove() == 1);
    }

    /**
     * verifies if the die of draft at index i can be placed in player's window in i,j positions
     * if true, places the die
     * @param window the player's window
     * @param x the row to put the die in
     * @param y the column to put the die in
     * @param draft the draft of the game
     * @param i the index of the die in the draft
     * @return true if the die has been placed, false otherwise
     */
    private boolean placeDice(Window window, int x, int y, Draft draft, int i){
        Dice dicetmp = draft.getDraft().get(i);
        if ((window.verifyAllRestrictions(dicetmp, x, y)) || (window.getIsEmpty() && window.verifyFirstDiceRestriction(dicetmp, x, y))){
            draft.extract(i);
            window.getWindow()[x][y].setDice(dicetmp);
            return true;
        }
        else
            return false;
    }

}
