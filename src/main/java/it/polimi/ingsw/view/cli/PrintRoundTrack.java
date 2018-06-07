package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.RoundTrack;

import java.io.Serializable;

public class PrintRoundTrack implements Serializable {

    /**
     * prints the roundtrack
     * @param roundTrack the roundtrack of the game
     */
    public static void print(RoundTrack roundTrack){
        for(int i=0; i<roundTrack.getGrid().size(); i++){
            System.out.println(i+1 +") " + roundTrack.getGrid().get(i).getValue() + roundTrack.getGrid().get(i).getColor());
        }
        System.out.println();
    }
}
