package it.polimi.ingsw.model;

import java.util.ArrayList;

public class RoundTrack {

    private static RoundTrack instance = null;

    private ArrayList<Dice> grid;

    private RoundTrack(){
        grid = new ArrayList<Dice>(10);
    }

    public static RoundTrack getInstance(){
        if (instance == null)
            instance = new RoundTrack();
        return instance;
    }

    public void setGrid(Dice dice){
        if(this.getRound() < 10) {
            grid.add(dice);
        }
        else{
            System.out.println("Error");
        }

    }

    public Dice getGrid(int i){
        return grid.get(i);
    }

    public int getRound(){
        return grid.size() + 1;
    }
}
