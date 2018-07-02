package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.util.ArrayList;


public class TCCopperFoilBurnisher extends ToolCard  {

    /**
     * creates a toolcard setting idnumber, color, name and description
     */
    public TCCopperFoilBurnisher(){
        super.setIdNumber(3);
        super.setColor(Colors.R);
        super.setName("Copper Foil Burnisher");
        super.setDescription("Move any ONE die in your window ignoring value restrictions.\n");
    }

    /**
     * moves a die in window's player ignoring value restrictions
     * @param gameModel the gamemodel of the match
     * @param input a list of integer that represents the client's inputs
     *              in [0],[1] the i,j of the die to be moved
     *              in [2],[3] the i,j of the new position of the die
     *              IN [0] '-1' TO UNDO
     * @return true if the toolcard has been used correctly, false otherwise
     */
    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        return (whichRestriction(gameModel, input, 3));
    }

    /**
     * check if the actualplayer's window is empty or not
     * @param gameModel the gamemodel of the match
     * @return true if the window isn't empty, false otherwise
     */
    @Override
    public boolean select(GameModel gameModel){
        return checkNotEmptyWindow(gameModel.getActualPlayer().getWindow());
    }
}