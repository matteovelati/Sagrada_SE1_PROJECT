package it.polimi.ingsw.model;

import java.io.Serializable;

public class Space implements Serializable {

    private Colors color;
    private int value;
    private boolean isEmpty;
    private Dice dice;

    /**
     * creates a Space object setting color, value and isempty
     * @param color the color to be set
     * @param value the value to be set
     */
    public Space(Colors color, int value){
        this.color = color;
        this.value = value;
        isEmpty = true;
    }

    /**
     * gets the color of the space
     * @return the color of the space
     */
    public Colors getColor() {
        return color;
    }

    /**
     * sets the color of space
     * @param color the color to be set
     */
    public void setColor(Colors color) {
        this.color = color;
    }

    /**
     * gets the value of the space
     * @return the value of the space
     */
    public int getValue() {
        return value;
    }

    /**
     * sets the value of the space
     * @param value the value to be set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * checks if the space is empty
     * @return true if the space is empty, false otherwise
     */
    public boolean getIsEmpty() {
        return isEmpty;
    }

    /**
     * sets if the space is empty or not
     * @param empty the boolean to be set
     */
    public void setIsEmpty(boolean empty) {
        isEmpty = empty;
    }

    /**
     * gets the die inside the space
     * @return the dice inside the space
     */
    public Dice getDice() {
        return dice;
    }

    /**
     * sets the die inside the space
     * @param dice the die to be set. if dice==null it means you are freeing the space
     */
    public void setDice(Dice dice) {
        this.dice = dice;
        if (dice == null)
            setIsEmpty(true);
        else
            setIsEmpty(false);
    }
}
