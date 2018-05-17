package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.SchemeCard;

import java.io.Serializable;
import java.util.ArrayList;

public class PrintSchemeCard implements Serializable {

    public static void print(ArrayList<SchemeCard> schemeCards){
        int j=1;
        for(int i=0; i<schemeCards.size(); i++){
            System.out.println(j +")");
            PrintWindow.print(schemeCards.get(i).getFront());
            j++;
            System.out.println(j +")");
            PrintWindow.print(schemeCards.get(i).getBack());
            j++;
        }
    }
}
