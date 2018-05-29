package it.polimi.ingsw.model;

import java.util.ArrayList;

public abstract class ToolCard extends Card {

    private boolean isUsed;
    private int calls;
    private boolean forceTurn;

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

}
