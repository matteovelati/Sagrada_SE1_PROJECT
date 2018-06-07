package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.PrivateObjective;

import java.io.Serializable;

public class PrintPrivateObjective implements Serializable {

    private static final String ANSI_RESET = "\u001b[0m";
    private static final String ANSI_BOLD = "\u001b[1m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_PURPLE	= "\u001B[35m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    /**
     * prints the private objective
     * @param privateObjective the player's private objective
     */
    public static void print(PrivateObjective privateObjective){
        Colors colortmp = Colors.W;
        if (privateObjective.getColor() == Colors.R)    colortmp = Colors.R;
        else if (privateObjective.getColor() == Colors.G)   colortmp = Colors.G;
        else if (privateObjective.getColor() == Colors.P)   colortmp = Colors.P;
        else if (privateObjective.getColor() == Colors.B)   colortmp = Colors.B;
        else if (privateObjective.getColor() == Colors.Y)   colortmp = Colors.Y;

        switch (colortmp){
            case R:
                System.out.println(ANSI_BOLD + "TITLE: "+ ANSI_RED + privateObjective.getName());
                System.out.println(ANSI_BOLD + "DESCRIPTION: " + ANSI_RED + privateObjective.getDescription());
                break;
            case G:
                System.out.println(ANSI_BOLD + "TITLE: "+ ANSI_GREEN + privateObjective.getName());
                System.out.println(ANSI_BOLD + "DESCRIPTION: "+ ANSI_GREEN + privateObjective.getDescription());
                break;
            case P:
                System.out.println(ANSI_BOLD + "TITLE: "+ ANSI_PURPLE + privateObjective.getName());
                System.out.println(ANSI_BOLD + "DESCRIPTION: "+ ANSI_PURPLE + privateObjective.getDescription());
                break;
            case B:
                System.out.println(ANSI_BOLD + "TITLE: "+ ANSI_BLUE + privateObjective.getName());
                System.out.println(ANSI_BOLD + "DESCRIPTION: "+ ANSI_BLUE + privateObjective.getDescription());
                break;
            case Y:
                System.out.println(ANSI_BOLD + "TITLE: "+ ANSI_YELLOW + privateObjective.getName());
                System.out.println(ANSI_BOLD + "DESCRIPTION: "+ ANSI_YELLOW + privateObjective.getDescription());
                break;
            default:
                System.out.println(ANSI_BOLD + "TITLE: "+ privateObjective.getName());
                System.out.println(ANSI_BOLD + "DESCRIPTION: "+ privateObjective.getDescription());
                break;
        }

        System.out.println(ANSI_RESET);
    }
}
