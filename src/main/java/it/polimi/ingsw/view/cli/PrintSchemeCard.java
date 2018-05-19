package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.SchemeCard;

import java.io.Serializable;
import java.util.ArrayList;

public class PrintSchemeCard implements Serializable {

    public static void print(SchemeCard schemeCard1, SchemeCard schemeCard2) {
            System.out.println("1)");
            PrintWindow.print(schemeCard1.getFront());
            System.out.println("2)");
            PrintWindow.print(schemeCard2.getBack());
    }
}
