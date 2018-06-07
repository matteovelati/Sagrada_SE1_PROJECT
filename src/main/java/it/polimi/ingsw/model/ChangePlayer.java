package it.polimi.ingsw.model;

import java.io.Serializable;

public class ChangePlayer implements Serializable {

    /**
     * calculates the index of the next player of the round in a clockwise order
     * when the player index reach the size of players arraylist, this method reset the counter to 0
     * @param player the index of the actual player
     * @param size the size of the players arraylist
     * @return the index of the new actual player
     */
    public static int clockwise(int player, int size){
        player ++;
        if(player == size){
            player = 0;
        }
        return player;
    }

    /**
     * calculates the index of the next player of the round in an anticlockwise order
     * when the player index reach the size of players arraylist, this method reset the counter to 0
     * @param player the index of the actual player
     * @param size the size of the players arraylist
     * @return the index of the new actual player
     */
    public static int antiClockwise(int player, int size){
        player --;
        if(player == -1){
            player = size-1;
        }
        return player;
    }

}
