package it.polimi.ingsw.model;

import java.io.Serializable;

public class ChangePlayer implements Serializable {

    public static int clockwise(int player, int size){
        player ++;
        if(player == size){
            player = 0;
        }
        return player;
    }

    public static int antiClockwise(int player, int size){
        player --;
        if(player == -1){
            player = size-1;
        }
        return player;
    }

}
