package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;


public class TCLathekin extends Card implements ToolCard {

    private boolean isUsed;
    private Dice dicetmp;
    private int calls = 2;
    private int flag = 1;


    public TCLathekin(){
        this.isUsed = false;
        super.setIdNumber(4);
        super.setColor(Colors.Y);
        super.setName("Lathekin");
        super.setDescription("Move exactly TWO dice, obeying all placement restrictions.\n");
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
        //arraylist: in 0,1 le i,j del dado1; in 2,3 le i,j della new pos dado1; in 4,5 le i,j del dado2; in 6,7 le i,j della new pos dado2
        boolean check;
        if(flag == 1) {
            flag = 2;
            return (moveDice(gameModel.getActualPlayer().getWindow(), input.get(0), input.get(1), input.get(2), input.get(3)));
        }
        if (flag == 2) {
            flag = 1;
            check = (moveDice(gameModel.getActualPlayer().getWindow(), input.get(4), input.get(5), input.get(6), input.get(7)));
            if (check) {
                if (!getIsUsed())
                    setIsUsed(true);
                return true;
            }
            else {
                moveDice(gameModel.getActualPlayer().getWindow(), input.get(2), input.get(3),input.get(0), input.get(1));
                return false;
            }
        }
        else
            return false;
    }

    private boolean moveDice(Window window, int i, int j, int x, int y){ //i,j dado da muovere - x,y nuova casella
        dicetmp = window.getWindow()[i][j].getDice();
        if (window.verifyAllRestrictions(dicetmp, x, y)){
            window.getWindow()[x][y].setDice(dicetmp);
            window.getWindow()[i][j].setDice(null);
            return true;
        }
        else
            return false;
    }

}
