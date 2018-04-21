package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Draft {

    private ArrayList<Dice> draft;

    public Draft(){
        draft = new ArrayList<Dice>();
    }

    public void setDraft(Dice dice){
        dice.setValue();
        draft.add(dice);
    }

    public ArrayList<Dice> getDraft() {
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

    public void print(){
        for(int i=0; i<draft.size(); i++){
            System.out.println(draft.get(i).getValue()+" "+draft.get(i).getColor());
        }
    }

    public int size(){
        return draft.size();
    }
}
