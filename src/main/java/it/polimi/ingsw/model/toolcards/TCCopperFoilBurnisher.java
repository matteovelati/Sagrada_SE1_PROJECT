package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.model.Window;


public class TCCopperFoilBurnisher extends Card implements ToolCard   {

    private boolean isUsed;
    private Dice dicetmp;

    public TCCopperFoilBurnisher(int idNumber){
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(3);
        super.setColor(Colors.R);
        super.setName("Copper Foil Burnisher");
        super.setDescription("Move any ONE die in your window ignoring value restrictions.\n");
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


    private void moveDice(Window window, int i, int j, int x, int y){ //i,j dado da muovere - x,y nuova casella
        dicetmp = window.getWindow()[i][j].getDice();
        if (window.positionRestriction(dicetmp, x, y) && window.colorRestriction(dicetmp, x, y)){
            window.getWindow()[x][y].setDice(dicetmp);
            window.getWindow()[i][j].setDice(null);
        }
        //else richiama la scelta
    }

}
