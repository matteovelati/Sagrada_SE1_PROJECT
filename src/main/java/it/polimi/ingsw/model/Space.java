package it.polimi.ingsw.model;

public class Space {

    private Colors color;
    private int value;
    private boolean isEmpty;
    private Dice dice;

    public Space(Colors color, int value){
        this.color = color;
        this.value = value;
        isEmpty = true;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(boolean empty) {
        isEmpty = empty;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
        setIsEmpty(false);
    }
}
