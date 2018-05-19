package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    private String username;
    private int tokens;
    private Window window;
    private PrivateObjective privateObjective;
    private Dice dice;
    private ToolCard toolCardSelected;
    private int finalScore;

    public Player(String username){
        this.username = username;
        setPrivateObjective();
    }

    public Dice getDice() {
        return dice;
    }

    public String getUsername() {
        return username;
    }

    public int getTokens() {
        return tokens;
    }

    public Window getWindow() {
        return window;
    }

    public PrivateObjective getPrivateObjective() {
        return privateObjective;
    }

    public ToolCard getToolCardSelected() {
        return toolCardSelected;
    }

    public int getFinalScore(){
        return finalScore;
    }



    public void setDice(Dice dice){
        this.dice = dice;
    }

    /*vengono passate le carte schema mostrate e già il numero di quella selezionata*/
    public boolean setWindow(SchemeCard card1, SchemeCard card2, int i){
        switch(i){
            case 1:
                this.window = card1.getFront();
                tokens = window.getDifficulty();
                return true;
            case 2:
                this.window = card1.getBack();
                tokens = window.getDifficulty();
                return true;
            case 3:
                this.window = card2.getFront();
                tokens = window.getDifficulty();
                return true;
            case 4:
                this.window = card2.getBack();
                tokens = window.getDifficulty();
                return true;
            default :
                return false;
        }
    }

    public void setPrivateObjective() {
        this.privateObjective = new PrivateObjective();
    }

    public void setFinalScore(int finalScore){
        this.finalScore = finalScore;
    }



    public boolean selectToolCard(ArrayList<ToolCard> toolCards, int i){

        toolCardSelected = toolCards.get(i);
        if(toolCardSelected.getIsUsed()){
            if(this.tokens >= 2) {
                this.tokens = this.tokens - 2;
                return true;
            }
            else
                return false;
        }
        else{
            if(this.tokens >= 1){
                this.tokens --;
                toolCards.get(i).setIsUsed(true);
                return true;
            }
            else
                return false;
        }
    }

    public void pickDice(Draft draft, int i){
        this.setDice(draft.extract(i));
    }

    public boolean putDice(int i, int j){

        if (this.window.verifyAllRestrictions(dice, i, j)) {
            this.window.setWindow(dice, i, j);
            return true;
        }
        else
            return false;
    }

    //CHIAMA IL METODO DELLA USETOOLCARD SELEZIONATA
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input){
        return toolCardSelected.useToolCard(gameModel, input);
    }
}

