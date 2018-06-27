package it.polimi.ingsw.model;

import java.util.ArrayList;

public abstract class ToolCard extends Card {

    private boolean isUsed = false;
    private int calls = 1;
    private boolean forceTurn = false;

    /**
     * allows to use a toolcard effect
     * @param gameModel the gamemodel of the match
     * @param input a list of integer that represents the client's inputs
     * @return true if the toolcard has been used correctly, false otherwise
     */
    public abstract boolean useToolCard(GameModel gameModel, ArrayList<Integer> input);

    /**
     * gets the title of the toolcard
     * @return the title of the toolcard
     */
    public String getTitle(){
        return super.getName();
    }

    /**
     * gets the description of the toolcard
     * @return the description of the toolcard
     */
    public String getDescr(){
        return super.getDescription();
    }

    /**
     * gets the idnumber of the toolcard
     * @return the idnumber of the toolcard
     */
    public int getNumber(){
        return super.getIdNumber();
    }

    /**
     * gets if the toolcard is used or not
     * @return true if the toolcard is used, false otherwise
     */
    public boolean getIsUsed() {
        return isUsed;
    }

    /**
     * gets the number of calls of the toolcard
     * @return the number of calls of the toolcard
     */
    public int getCalls(){
        return calls;
    }

    /**
     * gets if the toolcard force the turn or not
     * @return true if the toolcard force the turn, false otherwise
     */
    public boolean getForceTurn() {
        return forceTurn;
    }

    /**
     * sets the toolcard as used or not
     * @param isUsed the boolean to be set
     */
    public void setIsUsed(boolean isUsed){
        this.isUsed = isUsed;
    }

    /**
     * verifies if the objects used in the toolcards are empty or not
     * @param gameModel the gamemodel of the match
     * @return true if the toolcard can be used, false otherwise
     */
    public boolean select(GameModel gameModel){
        return true;
    }

    /**
     * sets the number of calls to the toolcard
     * @param calls the number of calls to be set
     */
    protected void setCalls(int calls){
        this.calls = calls;
    }

    /**
     * sets if the toolcard forces the turn or not
     * @param forceTurn the boolean to be set
     */
    protected void setForceTurn(boolean forceTurn){
        this.forceTurn = forceTurn;
    }

    /**
     * verifies if the draft's selected die from the player can be placed at the i,j position in the player's window
     * if true, places the die
     * @param gameModel the gamemodel of the match
     * @param input a list of integer that represents the client's inputs
     * @return true if the die has been placed, false otherwise
     */
    protected boolean diePlacement(GameModel gameModel, ArrayList<Integer> input){
        if ((gameModel.getActualPlayer().getWindow().verifyAllRestrictions(gameModel.getField().getDraft().getDraft().get(input.get(0)), input.get(2), input.get(3))) ||
                (gameModel.getActualPlayer().getWindow().getIsEmpty() && gameModel.getActualPlayer().getWindow().verifyFirstDiceRestriction(gameModel.getField().getDraft().getDraft().get(input.get(0)), input.get(2), input.get(3)))){
            gameModel.getActualPlayer().pickDice(gameModel.getField().getDraft(), input.get(0));
            return (gameModel.getActualPlayer().putDice(input.get(2), input.get(3)));
        } else
            return false;
    }

    /**
     * verifies if the first input is [-1] and tries to move a die
     * @param gameModel the gamemodel of the match
     * @param input a list of integer that represents the client's inputs
     * @param card an identifier of some toolcards
     * @return true if as been moved
     */
    protected boolean whichRestriction(GameModel gameModel, ArrayList<Integer> input, int card){
        if (input.get(0) != -1)
            return (moveDice(gameModel.getActualPlayer().getWindow(), input.get(0), input.get(1), input.get(2), input.get(3), card));
        else
            return false; //con questo false NON deve richiamare il metodo
    }

    /**
     * verifies if the die in window's i,j positions can be moved to x,y positions
     * if true, moves the die
     * @param window the player's window
     * @param i the actual row of the die
     * @param j the actual column of the die
     * @param x the next row
     * @param y the next column
     * @param card an identifier of some toolcards
     * @return true if the die has been moved, false otherwise
     */
    private boolean moveDice(Window window, int i, int j, int x, int y, int card) { //i,j dado da muovere - x,y nuova casella
        Dice dicetmp = window.getWindow()[i][j].getDice();
        if (dicetmp == null)
            return false;
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

    /**
     * verifies if the window is empty or not
     * @param window the window to be analyzed
     * @return true if isn't empty, false otherwise
     */
    protected boolean checkNotEmptyWindow(Window window){
        return !window.getIsEmpty();
    }

    /**
     * verifies if the roundtrack is empty or not
     * @param roundTrack the roundtrack to be analyzed
     * @return true if isn't empty, false otherwise
     */
    protected boolean checkNotEmptyRoundTrack(RoundTrack roundTrack){
        return !roundTrack.getGrid().isEmpty();
    }
}