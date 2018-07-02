package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Bag implements Serializable {

    private static Bag instance;
    private ArrayList<Dice> bag;

    /**
     * creates a Bag object which contains 90 colored dice.
     * the dice are stocked in an arraylist of Dice objects.
     * the number of dice are 18 for each color.
     * the bag is finally shuffled
     */
    private Bag(){
        bag = new ArrayList<>(90);

        for(int i=0; i<18; i++){
            Dice dice = new Dice(Colors.B);
            bag.add(dice);
            Dice dice2 = new Dice(Colors.G);
            bag.add(dice2);
            Dice dice3 = new Dice(Colors.P);
            bag.add(dice3);
            Dice dice4 = new Dice(Colors.R);
            bag.add(dice4);
            Dice dice5 = new Dice(Colors.Y);
            bag.add(dice5);
        }
        Collections.shuffle(bag);
    }

    /**
     * if the bag already exists, the method returns the Bag object,
     * otherwise it creates a new bag.
     * @return the instance of the Bag class
     */
    public static Bag getInstance(){
        if (instance == null)
            instance = new Bag();
        return instance;
    }

    /**
     * deletes the instance of this class to restart the game
     */
    public static synchronized void reset(){        //TEST METHOD + RESTART GAME
        instance = null;
    }

    /**
     * gets the list of dice inside the bag
     * @return the list of dice inside the bag.
     */
    public ArrayList<Dice> getBag() {
        return bag;
    }

    /**
     * allows the player to extract a die from the bag
     * @param i the index of element to be removed
     * @return the die that was removed from the bag
     */
    public Dice extract(int i){
        return bag.remove(i);
    }
}
