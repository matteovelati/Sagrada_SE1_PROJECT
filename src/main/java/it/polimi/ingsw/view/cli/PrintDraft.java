package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Draft;

import java.io.Serializable;

public class PrintDraft implements Serializable {

    /**
     * prints the draft
     * @param draft the draft of the game
     */
    public static void print(Draft draft){
        for(int i=0; i<draft.getDraft().size(); i++){
            System.out.println(i+1 +") "+ draft.getDraft().get(i).getValue() + draft.getDraft().get(i).getColor());
        }
        System.out.println();
    }
}
