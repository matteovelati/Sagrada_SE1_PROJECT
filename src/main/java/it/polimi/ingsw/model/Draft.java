package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Random;

public class Draft {

    private static Draft instance = null;
    private ArrayList<Dice> draft;
    private Dice dice;

    private Draft(){
        draft = new ArrayList<Dice>();
    }

    public static Draft getInstance(){
        if (instance == null)
            instance = new Draft();
        return instance;
    }

    public void setDraft(Bag bag){
        Random r = new Random();
        dice = bag.extract((r.nextInt(bag.getBag().size()) + 1));
        dice.setValue();
        draft.add(dice);
    }

    public ArrayList<Dice> getDraft(){
        return draft;
    }

    public Dice extract(int i) throws IndexOutOfBoundsException{
        try{
            return draft.remove(i);
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Errore estrazione");
            return null;
        }
    }

    public void addDice(Dice dice){     //metodo toolcard
        draft.add(dice);
    }


}
