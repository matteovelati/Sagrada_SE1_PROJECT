package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;


public class TCEglomiseBrush extends Card implements ToolCard {

    private boolean isUsed;
    private Dice dicetmp;
    private int calls = 1;

    public TCEglomiseBrush(){
        this.isUsed = false;
        super.setIdNumber(2);
        super.setColor(Colors.B);
        super.setName("Eglomise Brush");
        super.setDescription("Move any ONE die in your window ignoring color restrictions.\n");
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
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input){
        //arraylist: in 0,1 le i,j del dado da muovere; in 2,3 le i,j della nuova posizione
        //IN 0 (-1) PER ANNULLARE
        if (input.get(0) != -1)
            return (moveDice(gameModel.getActualPlayer().getWindow(), input.get(0), input.get(1), input.get(2), input.get(3)));
        else
            return false; //con questo false NON deve richiamare il metodo
    }

    private boolean moveDice(Window window, int i, int j, int x, int y){ //i,j dado da muovere - x,y nuova casella
        dicetmp = window.getWindow()[i][j].getDice();
        window.getWindow()[i][j].setDice(null);
        if (window.neighboursColorRestriction(dicetmp, x, y) && window.neighboursNumberRestriction(dicetmp, x, y) && window.neighboursPositionRestriction(x, y) && window.spaceNumberRestriction(dicetmp, x, y)){
            window.getWindow()[x][y].setDice(dicetmp);
            if(!getIsUsed())
                setIsUsed(true);
            return true;
        }
        else {
            window.getWindow()[i][j].setDice(dicetmp);
            return false;
        }
    }

}
