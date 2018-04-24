package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Bag {

    private static Bag instance;
    private ArrayList<Dice> bag;

    private Bag(){
        bag = new ArrayList<Dice>(90);

        for(int i=0; i<18; i++){
            Dice dice = new Dice(Colors.B);
            Dice dice2 = new Dice(Colors.P);
            Dice dice3 = new Dice(Colors.G);
            Dice dice4 = new Dice(Colors.R);
            Dice dice5 = new Dice(Colors.Y);
            bag.add(dice);
        }
    }

    public static Bag getInstance(){
        if (instance == null)
            instance = new Bag();
        return instance;
    }


}
