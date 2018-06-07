package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.Random;

public class Dice implements Serializable {

    private Colors color;
    private int value;

    /**
     * creates a new colored Dice object
     * @param color the color of the new die
     */
    public Dice(Colors color){
        this.color = color;
    }

    /**
     * gets the color of the die
     * @return the color of the die
     */
    public Colors getColor() {
        return color;
    }

    /**
     * gets the value of the die
     * @return the value of the die
     */
    public int getValue() {
        return value;
    }

    /**
     * randomly generates and sets a number between 1 and 6 for the new die created
     */
    public void setValue() {
        Random r = new Random();
        this.value = r.nextInt(6) + 1;
    }

    /**
     * allows the player to set a value to a specific die
     * @param value the value to be set
     */
    public void modifyValue(int value){     //metodo toolcard
        this.value = value;
    }

}
