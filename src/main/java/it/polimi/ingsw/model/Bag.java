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
        }

        for(int i=0; i<18; i++){
            Dice dice = new Dice(Colors.P);
            bag.add(dice);
        }

        for(int i=0; i<18; i++){
            Dice dice = new Dice(Colors.G);
            bag.add(dice);
        }

        for(int i=0; i<18; i++){
            Dice dice = new Dice(Colors.G);
            bag.add(dice);
        }

        for(int i=0; i<18; i++){
            Dice dice = new Dice(Colors.Y);
            bag.add(dice);
        }
    }

    public static Bag getInstance(){
        if (instance == null)
            instance = new Bag();
        return instance;
    }


}
