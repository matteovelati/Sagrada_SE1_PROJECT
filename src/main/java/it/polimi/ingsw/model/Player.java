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

    /**
     * creates a Player object setting the username, a private objective and some booleans to default value
     * @param username the nickname of the client
     * @param color the color of the private objective
     */
    public Player(String username, Colors color){
        this.username = username;
        this.setSkipNextTurn(false);
        setOnline(true);
        privateObjectives = new ArrayList<>(1);
        setPrivateObjectives(color);
    }

    /**
     * gets if the player is online or not
     * @return true if the player is online, false otherwise
     */
    public synchronized boolean getOnline() {
        return online;
    }

    /**
     * gets the die selected
     * @return the selected die
     */
    public Dice getDice() {
        return dice;
    }

    /**
     * gets the player's username
     * @return the player's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * gets the remaining favor tokens of the player
     * @return the number of favor tokens of the player
     */
    public int getTokens() {
        return tokens;
    }

    /**
     * gets the window of the player
     * @return the player's window
     */
    public Window getWindow() {
        return window;
    }

    /**
     * gets the list of private objectives of the player
     * containing 1 card if multiplayer, 2 if singleplayer
     * @return the list of private objectives
     */
    public ArrayList<PrivateObjective> getPrivateObjectives() {
        return privateObjectives;
    }

    /**
     * gets the selected toolcard by the player
     * @return the selected toolcard
     */
    public ToolCard getToolCardSelected() {
        return toolCardSelected;
    }

    /**
     * gets the final score of the player
     * @return the final score
     */
    public int getFinalScore(){
        return finalScore;
    }

    /**
     * change the status of the player
     * @param online if true means the player is online, false means offline
     */
    public synchronized void setOnline(boolean online) {
        this.online = online;
        if(online)
            System.out.println(username+" IS ONLINE");
        else{
            System.out.println(username+" IS OFFLINE");
        }
    }

    /**
     * temporary saves a die in the player class
     * @param dice the die to be set
     */
    public void setDice(Dice dice){
        this.dice = dice;
    }

    /**
     * set the window pattern that the player has choosen
     * @param card1 the first SchemeCard available for selection
     * @param card2 the second SchemeCard available for selection
     * @param i the index of the window
     */
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

    /**
     * sets a new private objective to the player
     * @param color the color of the private objective
     */
    public void setPrivateObjectives(Colors color) {
        this.privateObjectives.add(new PrivateObjective(color));
    }

    /**
     * sets the final score to the player
     * @param finalScore the score to be set
     */
    public void setFinalScore(int finalScore){
        this.finalScore = finalScore;
    }

    /**
     * (multiplayer)
     * checks if the player can select this toolcard or not, based on the number of tokens remaining
     * @param toolCards the arraylist of toolcard of the game
     * @param i the index of the toolcard selected
     * @return true if can be selected, false otherwise
     */
    public boolean selectToolCardMP(ArrayList<ToolCard> toolCards, int i){
        toolCardSelected = toolCards.get(i);
        if(toolCardSelected.getIsUsed())
            return (this.tokens >= 2);
        else
            return (this.tokens >= 1);
    }

    /**
     * (singleplayer)
     * checks if the player can select this toolcard or not, based on if the card is already used
     * or if there is at least a die in the draft that matches the color of the toolcard
     * @param toolCards the arraylist of toolcard of the game
     * @param i the index of the toolcard selected
     * @return true if can be selected, false otherwise
     */
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

    /**
     * extracts and saves a the die at index i from the draft
     * @param draft the draft of the game
     * @param i the index of the die to be extracted
     */
    public void pickDice(Draft draft, int i){
        dice = draft.extract(i);
    }

    /**
     * checks if are verified all the restriction and then puts the die in the window at position [i,j]
     * @param i the row
     * @param j the column
     * @return true if the die has been added, false otherwise
     */
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

    /**
     * uses the effect of the selected toolcard toolcard
     * @param gameModel the gamemodel of the match
     * @param input the arraylist of client's inputs
     * @return true if the toolcard has been used, false otherwise
     */
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input){
        return toolCardSelected.useToolCard(gameModel, input);
    }

    /**
     * (multiplayer)
     * decreases the tokens of the player when he uses a toolcard
     * decreses by 2 if the toolcard has been already used, otherwise by 1
     */
    public void decreaseToken() {
        if (toolCardSelected.getIsUsed())
            this.tokens = this.tokens - 2;
        else {
            this.tokens--;
            toolCardSelected.setIsUsed(true);
        }
    }


    /**
     * gets if the player has to skip his next turn in the round
     * if true, sets the boolean to false
     * @return true if the player has to skip his next turn, false otherwise
     */
    public boolean getSkipNextTurn() {
        if (skipNextTurn){
            setSkipNextTurn(false);
            return true;
        }
        else
            return false;
    }

    /**
     * sets if the player has to skip his next turn in the round
     * @param skipNextTurn the boolean to be set
     */
    public void setSkipNextTurn(boolean skipNextTurn) {
        this.skipNextTurn = skipNextTurn;
    }
}

