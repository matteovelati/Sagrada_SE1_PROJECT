package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;


public class RoundTrack implements Serializable {

    private static RoundTrack instance = null;
    private ArrayList<Dice> grid;
    private int round;

    private RoundTrack(){
        grid = new ArrayList<>(10);
        round = 1;
    }

    public static RoundTrack getInstance(){
        if (instance == null)
            instance = new RoundTrack();
        return instance;
    }

    public void setGrid(Dice dice){
            grid.add(dice);
    }

    public void incrementRound(){
        round++;
    }

    public ArrayList<Dice> getGrid(){
        return grid;
    }

    public int getRound(){
        return round;
    }

    public Dice changeDice(int i, Dice dice){       //metodo toolcard
        Dice dicetmp = grid.get(i);
        grid.set(i, dice);
        return dicetmp;
    }
}
