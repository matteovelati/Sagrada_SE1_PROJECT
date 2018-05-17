package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.ToolCard;

import java.io.Serializable;
import java.util.ArrayList;

public class PrintToolCard implements Serializable {

    public static void print(ArrayList<ToolCard> toolCards){
        for(int i=0; i<toolCards.size(); i++){
            //ho bisogno della descrizione e difficoltà
        }
    }
}
