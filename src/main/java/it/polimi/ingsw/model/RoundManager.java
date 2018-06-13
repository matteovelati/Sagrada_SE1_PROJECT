package it.polimi.ingsw.model;

import java.io.Serializable;

public class RoundManager implements Serializable {

    private static RoundManager instance = null;

    private int turn;
    private int counter;
    private int firstMove;

    /**
     * creates a RoundManager object setting some values
     */
    private RoundManager(){
        turn = 1;
        counter = 1;
        firstMove = 0;
    }

    /**
     * if the roundmanager already exists, the method returns the Roundmanager object,
     * otherwise it creates a new roundmanager.
     * @return the instance of the Roundmanager class
     */
    public static RoundManager getInstance(){
        if (instance == null)
            instance = new RoundManager();
        return instance;
    }

    public static synchronized void reset (){   //TEST METHOD
        instance = null;
    }

    /**
     * gets the turn
     * @return the turn
     */
    public int getTurn() {
        return turn;
    }

    /**
     * gets the counter
     * @return the counter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * gets the firstmove
     * @return the firstmove
     */
    public int getFirstMove() {
        return firstMove;
    }

    /**
     * sets the firstmove
     * @param firstMove the firstmove to be set
     */
    public void setFirstMove(int firstMove){
        this.firstMove = firstMove;
    }

    /**
     * according to the turn, it compares counter with nPlayers to select the next actualplayer
     * @param actualPlayer the actualplayer
     * @param nPlayers the total number of players in the match
     * @return the index of the new actualplayer
     */
    public int changeActualPlayer(int actualPlayer, int nPlayers){

        switch(turn) {
            case 1:
                if (counter < nPlayers) {
                    actualPlayer = ChangePlayer.clockwise(actualPlayer, nPlayers);
                    counter++;
                }
                else if (counter == nPlayers) {
                    counter --;
                    turn = 2;
                }
                break;
            case 2:
                if(counter > 0){
                    counter--;
                    actualPlayer = ChangePlayer.antiClockwise(actualPlayer, nPlayers);
                }
                else if(counter == 0){
                    counter = 1;
                    turn = 1;
                    actualPlayer = ChangePlayer.clockwise(actualPlayer, nPlayers);
                }
                break;
            default:
                assert false;
        }
        return actualPlayer;
    }

    /**
     * manages the remaining dice in the draft putting them in the roundtrack
     * increments the index of rounds
     * @param draft is the draft of the match
     * @param roundTrack is the roundtrack of the match
     */
    public void endRound(Draft draft, RoundTrack roundTrack){
        while(!draft.getDraft().isEmpty())
            roundTrack.setGrid(draft.getDraft().remove(0));
        roundTrack.incrementRound();
    }
}
