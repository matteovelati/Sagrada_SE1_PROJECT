package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.util.ArrayList;


public class TCCopperFoilBurnisher extends ToolCard  {

    public TCCopperFoilBurnisher(){
        super.setIdNumber(3);
        super.setColor(Colors.R);
        super.setName("Copper Foil Burnisher");
        super.setDescription("Move any ONE die in your window ignoring value restrictions.\n");
    }

    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        //arraylist: in 0,1 le i,j del dado da muovere; in 2,3 le i,j della nuova posizione
        //IN 0 (-1) PER ANNULLARE
        return (whichRestriction(gameModel, input, 3));
    }

    @Override
    public boolean select(GameModel gameModel){
        return checkNotEmptyWindow(gameModel.getActualPlayer().getWindow());
    }
}