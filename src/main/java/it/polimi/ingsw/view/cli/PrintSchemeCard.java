package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.SchemeCard;

import java.io.Serializable;

public class PrintSchemeCard implements Serializable {

    public static void print(SchemeCard schemeCard1, SchemeCard schemeCard2) {
            System.out.println("\n1)");
            PrintWindow.print(schemeCard1.getFront());
            System.out.println("\n2)");
            PrintWindow.print(schemeCard1.getBack());
            System.out.println("\n3)");
            PrintWindow.print(schemeCard2.getFront());
            System.out.println("\n4)");
            PrintWindow.print(schemeCard2.getBack());
    }
}
