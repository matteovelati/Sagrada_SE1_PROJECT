package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.PublicObjective;

import java.io.Serializable;
import java.util.ArrayList;

public class PrintPublicObjectives implements Serializable {

    public static void print(ArrayList<PublicObjective> publicObjectives){
        for(int i=0; i<publicObjectives.size(); i++){
            System.out.println("TITLE: "+ publicObjectives.get(i).getTitle());
            System.out.println("DESCRIPTION: "+ publicObjectives.get(i).getDescr()+"\n");
        }
    }
}
