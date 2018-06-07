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
     * createsa toolcard setting idnumber, color, name and description
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
     * @return true if the toolcard has been used correctly, false otherwise
     */
    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        //arraylisy: in 0 indice dado draft; in 1,2 le i,j della nuova posizione
        //IN 0 (-1) PER ANNULLARE
        if (input.get(0) != -1)
            return placeDice(gameModel.getActualPlayer().getWindow(), input.get(1), input.get(2), gameModel.getField().getDraft(), input.get(0));
        else
            return false; //con questo false NON deve richiamare il metodo
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
    private boolean placeDice(Window window, int x, int y, Draft draft, int i){ //x,y indice del piazzamento, i indice draft
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
