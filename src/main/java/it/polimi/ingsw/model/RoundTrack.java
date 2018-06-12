package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;


public class RoundTrack implements Serializable {

    private static RoundTrack instance = null;
    private ArrayList<Dice> grid;
    private int round;

    /**
     * creates a Roundtrack object setting round
     * initialize an arraylist of 10 dice elements
     */
    private RoundTrack(){
        grid = new ArrayList<>(10);
        round = 1;
    }

    /**
     * if the roundtrack already exists, the method returns the Roundtrack object,
     * otherwise it creates a new rountrack.
     * @return the instance of the Roundtrack class
     */
    public static RoundTrack getInstance(){
        if (instance == null)
            instance = new RoundTrack();
        return instance;
    }

    /**
     * adds the die to the arraylist of dice.
     * @param dice the die to be added
     */
    public void setGrid(Dice dice){
            grid.add(dice);
    }

    /**
     * increments the round
     */
    public void incrementRound(){
        round++;
    }

    /**
     * gets the list of dice
     * @return the list of dice
     */
    public ArrayList<Dice> getGrid(){
        return grid;
    }

    /**
     * gets the round
     * @return the round
     */
    public int getRound(){
        return round;
    }

    /**
     * calculates the sum of dice's values and return it
     * @return the sum of dice's value on the grid
     */
    public int calculateRoundTrack() {
        int score = 0;
        for (Dice d : grid){
            score += d.getValue();
        }
        return score;
    }

    /**
     * switches one die with one die of the roundtrack
     * @param i the index of the roundtrack's die
     * @param dice the die to be exchanged
     * @return the substituted roundtrack's die
     */
    public Dice changeDice(int i, Dice dice){       //metodo toolcard
        Dice dicetmp = grid.get(i);
        grid.set(i, dice);
        return dicetmp;
    }
}
