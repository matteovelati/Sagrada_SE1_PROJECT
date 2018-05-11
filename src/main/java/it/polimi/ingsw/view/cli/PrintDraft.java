package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Draft;

public class PrintDraft {

    public static void print(Draft draft){
        for(int i=0; i<draft.getDraft().size(); i++){
            System.out.println(i+1 +") "+ draft.getDraft().get(i).getColor() + draft.getDraft().get(i).getValue());
        }
    }
}
