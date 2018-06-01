package it.polimi.ingsw.model;

import java.util.ArrayList;

public abstract class PublicObjective extends Card{

    private int score;

    public abstract int calculateScore(Window window);
    public String getTitle(){
        return super.getName();
    }
    public String getDescr(){
        return super.getDescription();
    }
    public int getScore() {
        return score;
    }
    protected void setScore(int score) {
        this.score = score;
    }
    protected int matrixAnalyzer(Window window, int card){
        ArrayList<Integer> dicelist  = new ArrayList<>(6);
        dicelist.add(0);
        dicelist.add(0);
        dicelist.add(0);
        dicelist.add(0);
        dicelist.add(0);
        dicelist.add(0);
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                if (!window.getWindow()[i][j].getIsEmpty())
                    dicelist.set(window.getWindow()[i][j].getDice().getValue()-1, dicelist.get( window.getWindow()[i][j].getDice().getValue()-1 ) + 1);
            }
        }
        if (card == 4) {
            int minsets = dicelist.get(0);
            for(int i : dicelist) {
                if(i < minsets) minsets = i;
            }
            return getScore()*minsets;
        }
        if (dicelist.get((2*card)-2) < dicelist.get((2*card-1))) {
            return getScore() * dicelist.get((2*card)-2);
        }
        else
            return getScore() * dicelist.get((2*card)-1);
    }
}
