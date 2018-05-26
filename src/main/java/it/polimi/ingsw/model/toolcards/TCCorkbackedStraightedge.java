package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;


public class TCCorkbackedStraightedge extends Card implements ToolCard {

    private boolean isUsed;
    private Dice dicetmp;
    private int calls = 1;
    private boolean forceTurn = true;

    public TCCorkbackedStraightedge(){
        this.isUsed = false;
        super.setIdNumber(9);
        super.setColor(Colors.Y);
        super.setName("Cork-backed Straightedge");
        super.setDescription("After drafting, place the die in the spot that is not adjacent to another die.\nYou must obey all placement restrictions.\n");
    }

    @Override
    public String getTitle(){
        return super.getName();
    }

    @Override
    public String getDescr(){
        return super.getDescription();
    }

    @Override
    public int getNumber(){
        return super.getIdNumber();
    }

    @Override
    public boolean getIsUsed() {
        return isUsed;
    }

    @Override
    public void setIsUsed(boolean isUsed){
        this.isUsed = isUsed;
    }

    @Override
    public int getCalls(){
        return calls;
    }

    @Override
    public boolean getForceTurn() {
        return forceTurn;
    }

    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        //arraylisy: in 0 indice dado draft; in 1,2 le i,j della nuova posizione
        //IN 0 (-1) PER ANNULLARE
        if (input.get(0) != -1)
            return placeDice(gameModel.getActualPlayer().getWindow(), input.get(1), input.get(2), gameModel.getField().getDraft(), input.get(0));
        else
            return false; //con questo false NON deve richiamare il metodo
    }

    private boolean placeDice(Window window, int x, int y, Draft draft, int i){ //x,y indice del piazzamento, i indice draft
        dicetmp = draft.getDraft().get(i);
        if (window.neighboursColorRestriction(dicetmp, x, y) && window.neighboursNumberRestriction(dicetmp, x, y) && window.spaceColorRestriction(dicetmp, x, y) && window.spaceNumberRestriction(dicetmp, x, y)){
            draft.extract(i);
            window.getWindow()[x][y].setDice(dicetmp);
            return true;
        }
        else
            return false;
    }
}
