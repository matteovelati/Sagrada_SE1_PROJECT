package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;


public class TCCorkbackedStraightedge extends Card implements ToolCard   {

    private boolean isUsed;
    private Dice dicetmp;
    private int calls = 1;

    public TCCorkbackedStraightedge(){
        this.isUsed = false;
        super.setIdNumber(9);
        super.setColor(Colors.Y);
        super.setName("Cork-backed Straightedge");
        super.setDescription("After drafting, place the die in the spot that is not adjacent to another die.\n");
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
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        //arraylisy: in 0 indice dado draft; in 1,2 le i,j della nuova posizione
        return placeDice(gameModel.getActualPlayer().getWindow(), input.get(1), input.get(2), gameModel.getField().getDraft(), input.get(0));
    }

    private boolean placeDice(Window window, int x, int y, Draft draft, int i){ //x,y indice del piazzamento, i indice draft
        dicetmp = draft.extract(i);
        if (window.neighboursColorRestriction(dicetmp, x, y) && window.neighboursNumberRestriction(dicetmp, x, y) && window.spaceColorRestriction(dicetmp, x, y) && window.spaceNumberRestriction(dicetmp, x, y)){
            window.getWindow()[x][y].setDice(dicetmp);
            if(!getIsUsed())
                setIsUsed(true);
            return true;
        }
        else
            return false;
    }
}
