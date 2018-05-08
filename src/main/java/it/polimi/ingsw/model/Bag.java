package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Bag {

    private static Bag instance;
    private ArrayList<Dice> bag;

    private Bag(){
        bag = new ArrayList<Dice>(90);

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
    }

    public static Bag getInstance(){
        if (instance == null)
            instance = new Bag();
        return instance;
    }

    public ArrayList<Dice> getBag() {
        return bag;
    }

    public Dice extract(int i){
        return bag.remove(i);
    }
}
