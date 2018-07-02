package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    private String username;
    private int tokens;
    private Window window;
    private ArrayList<PrivateObjective> privateObjectives;
    private Dice dice;
    private ToolCard toolCardSelected;
    private int finalScore;
    private boolean skipNextTurn;
    private boolean online;

    public Player(String username, Colors color){
        this.username = username;
        this.setSkipNextTurn(false);
        setOnline(true);
        privateObjectives = new ArrayList<>(1);
        setPrivateObjectives(color);
    }

    public synchronized boolean getOnline() {
        return online;
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

    public ArrayList<PrivateObjective> getPrivateObjectives() {
        return privateObjectives;
    }

    public ToolCard getToolCardSelected() {
        return toolCardSelected;
    }

    public int getFinalScore(){
        return finalScore;
    }

    public synchronized void setOnline(boolean online) {
        this.online = online;
        if(online)
            System.out.println(username+" IS ONLINE");
        else{
            System.out.println(username+" IS OFFLINE");
        }
    }

    public void setDice(Dice dice){
        this.dice = dice;
    }

    public void setWindow(SchemeCard card1, SchemeCard card2, int i){
        switch(i){
            case 1:
                this.window = card1.getFront();
                tokens = window.getDifficulty();
                break;
            case 2:
                this.window = card1.getBack();
                tokens = window.getDifficulty();
                break;
            case 3:
                this.window = card2.getFront();
                tokens = window.getDifficulty();
                break;
            case 4:
                this.window = card2.getBack();
                tokens = window.getDifficulty();
                break;
            default:
                assert false: "wrong input: " + i;
        }
    }

    public void setPrivateObjectives(Colors color) {
        this.privateObjectives.add(new PrivateObjective(color));
    }

    public void setFinalScore(int finalScore){
        this.finalScore = finalScore;
    }

    public boolean selectToolCardMP(ArrayList<ToolCard> toolCards, int i){
        toolCardSelected = toolCards.get(i);
        if(toolCardSelected.getIsUsed())
            return (this.tokens >= 2);
        else
            return (this.tokens >= 1);
    }

    public boolean selectToolCardSP(ArrayList<ToolCard> toolCards, int i){
        toolCardSelected = toolCards.get(i);
        if(toolCardSelected.getIsUsed())
            return false;
        else {
            for (Dice d: Draft.getInstance().getDraft()){
                if (toolCardSelected.getColor().equals(d.getColor()))
                    return true;
            }
            return false;
        }
    }

    public void pickDice(Draft draft, int i){
        dice = draft.extract(i);
    }

    public boolean putDice(int i, int j){

        if(this.window.getIsEmpty()){
            if (this.window.verifyFirstDiceRestriction(dice, i, j)) {
                this.window.setWindow(dice, i, j);
                return true;
            }
            else
                return false;
        }

        else if (this.window.verifyAllRestrictions(dice, i, j)) {
            this.window.setWindow(dice, i, j);
            return true;
        }
        else
            return false;
    }

    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input){
        return toolCardSelected.useToolCard(gameModel, input);
    }

    public void decreaseToken() {
        if (toolCardSelected.getIsUsed())
            this.tokens = this.tokens - 2;
        else {
            this.tokens--;
            toolCardSelected.setIsUsed(true);
        }
    }

    public boolean getSkipNextTurn() {
        if (skipNextTurn){
            setSkipNextTurn(false);
            return true;
        }
        else
            return false;
    }

    public void setSkipNextTurn(boolean skipNextTurn) {
        this.skipNextTurn = skipNextTurn;
    }
}

