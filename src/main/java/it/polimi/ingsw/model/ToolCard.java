package it.polimi.ingsw.model;

import java.util.ArrayList;

public abstract class ToolCard extends Card {

    private boolean isUsed = false;
    private int calls = 1;
    private boolean forceTurn = false;

    public abstract boolean useToolCard(GameModel gameModel, ArrayList<Integer> input);
    public String getTitle(){
        return super.getName();
    }
    public String getDescr(){
        return super.getDescription();
    }
    public int getNumber(){
        return super.getIdNumber();
    }
    public boolean getIsUsed() {
        return isUsed;
    }
    public int getCalls(){
        return calls;
    }
    public boolean getForceTurn() {
        return forceTurn;
    }
    public void setIsUsed(boolean isUsed){
        this.isUsed = isUsed;
    }
    protected void setCalls(int calls){
        this.calls = calls;
    }
    protected void setForceTurn(boolean forceTurn){
        this.forceTurn = forceTurn;
    }
    protected boolean diePlacement(GameModel gameModel, ArrayList<Integer> input){
        if ((gameModel.getActualPlayer().getWindow().verifyAllRestrictions(gameModel.getField().getDraft().getDraft().get(input.get(0)), input.get(2), input.get(3))) ||
                (gameModel.getActualPlayer().getWindow().getIsEmpty() && gameModel.getActualPlayer().getWindow().verifyFirstDiceRestriction(gameModel.getField().getDraft().getDraft().get(input.get(0)), input.get(2), input.get(3)))){
            gameModel.getActualPlayer().pickDice(gameModel.getField().getDraft(), input.get(0));
            return (gameModel.getActualPlayer().putDice(input.get(2), input.get(3)));
        } else
            return false;
    }
    protected boolean whichRestriction(GameModel gameModel, ArrayList<Integer> input, int card){
        if (input.get(0) != -1)
            return (moveDice(gameModel.getActualPlayer().getWindow(), input.get(0), input.get(1), input.get(2), input.get(3), card));
        else
            return false; //con questo false NON deve richiamare il metodo
    }
    private boolean moveDice(Window window, int i, int j, int x, int y, int card) { //i,j dado da muovere - x,y nuova casella
        Dice dicetmp = window.getWindow()[i][j].getDice();
        window.getWindow()[i][j].setDice(null);
        if ((window.neighboursColorRestriction(dicetmp, x, y) && window.neighboursNumberRestriction(dicetmp, x, y) && window.neighboursPositionRestriction(x, y)) ||
                (window.getIsEmpty() && window.neighboursColorRestriction(dicetmp, x, y) && window.neighboursNumberRestriction(dicetmp, x, y))) {
            if ((card == 2 && window.spaceNumberRestriction(dicetmp, x, y) || (card == 3 && window.spaceColorRestriction(dicetmp, x, y)))) {
                window.getWindow()[x][y].setDice(dicetmp);
                return true;
            }
            else {
                window.getWindow()[i][j].setDice(dicetmp);
                return false;
            }
        } else {
            window.getWindow()[i][j].setDice(dicetmp);
            return false;
        }
    }
}
