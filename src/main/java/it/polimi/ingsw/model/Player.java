package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Player {

    private String username;
    private int tokens;
    private Window window;
    private PrivateObjective privateObjective;
    private Dice dice;
    private ToolCard toolCardSelected;

    public Player(String username){
        this.username = username;
    }

    public void setPrivateObjective(PrivateObjective privateObjective) {
        this.privateObjective = privateObjective;
    }

    public void setDice(Dice dice){
        this.dice = dice;
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

    public void pickDice(Draft draft, int i){
        this.setDice(draft.extract(i));
    }

    public boolean selectToolCard(ArrayList<ToolCard> toolCards, int i){

        toolCardSelected = toolCards.get(i);

        if(toolCardSelected.getIsUsed()){
            if(this.tokens >= 2) {
                this.tokens = this.tokens - 2;
                return true;
            }
            else{
                /*System.out.println("Segnalini favore non sufficienti");
                this.selectToolCard(toolCards, i);*/
                return false;
            }
        }
        else{
            if(this.tokens >= 1){
                this.tokens --;
                toolCards.get(i).setIsUsed(true);
                return true;
            }
            else{
                /*System.out.println("Segnalini favore non sufficienti");
                toolCardSelected = null;*/
                return false;
            }
        }
    }

    public boolean putDice(int i, int j){

        if (this.window.verifyAllRestrictions(dice, i, j)) {
            this.window.setWindow(dice, i, j);
            return true;
        } else {
            /*Restrizione presente*/
            return false;
        }
    }
}

