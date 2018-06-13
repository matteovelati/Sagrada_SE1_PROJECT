package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Draft implements Serializable {

    private static Draft instance = null;
    private ArrayList<Dice> draft;

    /**
     * creates a Draft object
     * initialize an arraylist of Dice objects
     */
    private Draft(){
        draft = new ArrayList<>();
    }

    /**
     * if the draft already exists, the method returns the Draft object,
     * otherwise it creates a new draft.
     * @return the instance of the Draft class
     */
    public static Draft getInstance(){
        if (instance == null)
            instance = new Draft();
        return instance;
    }

    public static synchronized void reset(){    //TEST METHOD
        instance = null;
    }

    /**
     * gets the list of dice inside the draft
     * @return the list of dice inside the Draft
     */
    public ArrayList<Dice> getDraft(){
        return draft;
    }

    /**
     * randomly extracts a die from the bag
     * randomly sets a value for the die
     * finally adds the die to the draft as the last element fo the list
     * @param bag the bag from which to extract the die
     */
    public void setDraft(Bag bag){
        Random r = new Random();
        Dice dice = bag.extract((r.nextInt(bag.getBag().size())));
        dice.setValue();
        addDice(dice);
    }

    /**
     * extracts the die at index i from the draft
     * @param i the index of the die to be extracted
     * @return the extracted Dice object
     */
    public Dice extract(int i){
        try{
            return draft.remove(i);
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Errore estrazione");
            return null;
        }
    }

    /**
     * adds a die to the draft as the last element of the list
     * @param dice the die to be added
     */
    public void addDice(Dice dice){     //metodo toolcard
        draft.add(dice);
    }

}
