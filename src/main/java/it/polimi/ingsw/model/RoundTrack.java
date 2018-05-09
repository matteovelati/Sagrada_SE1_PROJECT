package it.polimi.ingsw.model;

import java.util.ArrayList;


public class RoundTrack {

    private static RoundTrack instance = null;
    private ArrayList<Dice> grid;
    private Dice dicetmp;

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

    public ArrayList<Dice> getGrid(){
        return grid;
    }

    public int getRound(){
        return grid.size() + 1;
    }

    public Dice changeDice(int i, Dice dice){       //metodo toolcard
        dicetmp = grid.get(i);
        grid.set(i, dice);
        return dicetmp;
    }
}
