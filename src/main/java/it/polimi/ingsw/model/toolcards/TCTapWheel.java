package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;


public class TCTapWheel extends Card implements ToolCard   {

    private boolean isUsed;
    private Dice dicetmp;
    private int calls = 2;
    private int flag = 1;

    public TCTapWheel(){
        this.isUsed = false;
        super.setIdNumber(12);
        super.setColor(Colors.B);
        super.setName("Tap Wheel");
        super.setDescription("Move up to TWO dice of the same color that match the color of a die on the Round Track.\nYou must obey all placement restrictions.\n");
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
        //arraylist: in 0 indice dado roundtrack; in 1 la volontà della seconda mossa ('1' = YES); in 2,3 le i,j del dado1 da muovere; in 4,5 le i,j della new pos dado1; in 6,7 le i,j del dado1 da muovere; in 8,9 le i,j della new pos dado1
        boolean check;
        if (flag == 1) {
            flag = 2;
            return (moveDice(gameModel.getActualPlayer().getWindow(), input.get(2), input.get(3), input.get(4), input.get(5), gameModel.getField().getRoundTrack(), input.get(0)));
        }
        if (flag == 2) {
            if (input.get(1) == 1) {
                check = (moveDice(gameModel.getActualPlayer().getWindow(), input.get(6), input.get(7), input.get(8), input.get(9), gameModel.getField().getRoundTrack(), input.get(0)));
                if (check) {
                    if (!getIsUsed())
                        setIsUsed(true);
                    return true;
                }
                else {
                    moveDice(gameModel.getActualPlayer().getWindow(), input.get(4), input.get(5), input.get(2), input.get(3), gameModel.getField().getRoundTrack(), input.get(0));
                    return false;
                }
            }
            else {
                if (!getIsUsed())
                    setIsUsed(true);
                return true;
            }
        }
        else
            return false;

    }

    private boolean moveDice(Window window, int i, int j, int x, int y, RoundTrack grid, int k){ //i,j dado da muovere - x,y nuova casella - k dado roundtrack
        dicetmp = window.getWindow()[i][j].getDice();
        if (dicetmp.getColor().equals(grid.getGrid().get(k).getColor()) && window.verifyAllRestrictions(dicetmp, x, y)){
            window.getWindow()[x][y].setDice(dicetmp);
            window.getWindow()[i][j].setDice(null);
            return true;
        }
        else
            return false;
    }

}
