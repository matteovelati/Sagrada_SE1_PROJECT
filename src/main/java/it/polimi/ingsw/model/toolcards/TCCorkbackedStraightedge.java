package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import it.polimi.ingsw.model.Draft;
import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.model.Window;


public class TCCorkbackedStraightedge extends Card implements ToolCard   {

    private boolean isUsed;
    private Dice dicetmp;

    public TCCorkbackedStraightedge(int idNumber){
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(9);
        super.setColor(Colors.Y);
        super.setName("Cork-backed Straightedge");
        super.setDescription("After drafting, place the die in the spot that is not adjacent to another die.\n");
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
    public boolean useToolCard() {
        return false;
        }


    private void placeDice(Window window, int x, int y, Draft draft, int i){ //estrae il dado direttamente in questo metodo
        dicetmp = draft.extract(i);
        if (window.colorRestriction(dicetmp, x, y) && window.numberRestriction(dicetmp, x, y)){
            window.getWindow()[x][y].setDice(dicetmp);
        }
    }

}
