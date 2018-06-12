package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.PublicObjective;

import java.io.Serializable;
import java.util.ArrayList;

public class PrintPublicObjectives implements Serializable {

    /**
     * prints the public objectives
     * @param publicObjectives the list of 3 public objectives of the game
     */
    public static void print(ArrayList<PublicObjective> publicObjectives){
        for(PublicObjective po : publicObjectives){
            System.out.println("TITLE: "+ po.getTitle());
            System.out.println("DESCRIPTION: "+ po.getDescr()+"\n");
        }
    }
}
