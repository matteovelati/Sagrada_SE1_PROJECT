package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Draft {

    private static Draft instance = null;
    private ArrayList<Dice> draft;

    private Draft(){
        draft = new ArrayList<Dice>();
    }

    public static Draft getInstance(){
        if (instance == null)
            instance = new Draft();
        return instance;
    }

    public void setDraft(Dice dice){
        dice.setValue();
        draft.add(dice);
    }

    public ArrayList<Dice> getDraft(){
        return draft;
    }

    public Dice extract(int i) throws IndexOutOfBoundsException{
        return null;
    }
}
