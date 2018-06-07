package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import it.polimi.ingsw.model.Draft;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.model.Window;

import java.util.ArrayList;


public class TCCorkbackedStraightedge extends ToolCard {

    /**
     * creates a toolcard setting idnumber, color, name, description, forceturn
     */
    public TCCorkbackedStraightedge(){
        super.setIdNumber(9);
        super.setColor(Colors.Y);
        super.setName("Cork-backed Straightedge");
        super.setDescription("After drafting, place the die in the spot that is not adjacent to another die.\nYou must obey all placement restrictions.\n");
        super.setForceTurn(true);
    }

    /**
     * places a die in a spot that is not adjacent to another die
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
        if (window.neighboursColorRestriction(dicetmp, x, y) && window.neighboursNumberRestriction(dicetmp, x, y) && window.spaceColorRestriction(dicetmp, x, y) && window.spaceNumberRestriction(dicetmp, x, y)){
            draft.extract(i);
            window.getWindow()[x][y].setDice(dicetmp);
            return true;
        }
        else
            return false;
    }
}
