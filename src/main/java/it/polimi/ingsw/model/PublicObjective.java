package it.polimi.ingsw.model;

import java.util.ArrayList;

public abstract class PublicObjective extends Card{

    private int score;

    /**
     * calculates the score for each player at the end of the game based on public objectives cards
     * @param window is the player's window
     * @return the player's final score
     */
    public abstract int calculateScore(Window window);
    /**
     * gets the title of the publicobjective card
     * @return the title of the publicobjective card
     */
    public String getTitle(){
        return super.getName();
    }
    /**
     * gets the description of the publicobjective card
     * @return the description of the publicobjective card
     */
    public String getDescr(){
        return super.getDescription();
    }
    /**
     * gets the score of the publicobjective card
     * @return the score of the publicobjective card
     */
    public int getScore() {
        return score;
    }

    /**
     * sets the score to the publicobjective card
     * @param score is the score to be set
     */
    protected void setScore(int score) {
        this.score = score;
    }

    /**
     * scrolls the player's window saving every sets of same die value in a list
     * calculates, based on the card, the personal score for each player
     * @param window the player's window
     * @param card an identifier of public objective card
     * @return the score of the analyzed card
     */
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
