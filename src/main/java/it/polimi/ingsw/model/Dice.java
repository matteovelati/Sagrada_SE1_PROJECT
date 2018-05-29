package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.Random;

public class Dice implements Serializable {

    private Colors color;
    private int value;

    public Dice(Colors color){
        this.color = color;
    }

    public Colors getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public void setValue() {
        Random r = new Random();
        this.value = r.nextInt(6) + 1;
    }

    public boolean modifyValue(int value){     //metodo toolcard
        this.value = value;
        return true;
    }

}
