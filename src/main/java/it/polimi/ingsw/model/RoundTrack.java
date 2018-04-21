package it.polimi.ingsw.model;

import java.util.ArrayList;

public class RoundTrack {

    private ArrayList<Dice> grid;

    public RoundTrack(){
        grid = new ArrayList<Dice>(10);
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
