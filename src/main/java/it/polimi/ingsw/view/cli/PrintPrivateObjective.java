package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.PrivateObjective;

import java.io.Serializable;
import java.util.ArrayList;

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
     * @param privateObjectives the player's arraylist of private objectives
     */
    public static void print(ArrayList<PrivateObjective> privateObjectives) {
        for (PrivateObjective po : privateObjectives) {
            Colors colortmp = Colors.W;
            if (po.getColor() == Colors.R) colortmp = Colors.R;
            else if (po.getColor() == Colors.G) colortmp = Colors.G;
            else if (po.getColor() == Colors.P) colortmp = Colors.P;
            else if (po.getColor() == Colors.B) colortmp = Colors.B;
            else if (po.getColor() == Colors.Y) colortmp = Colors.Y;

            switch (colortmp) {
                case R:
                    System.out.println(ANSI_BOLD + "TITLE: " + ANSI_RED + po.getName());
                    System.out.println(ANSI_BOLD + "DESCRIPTION: " + ANSI_RED + po.getDescription());
                    break;
                case G:
                    System.out.println(ANSI_BOLD + "TITLE: " + ANSI_GREEN + po.getName());
                    System.out.println(ANSI_BOLD + "DESCRIPTION: " + ANSI_GREEN + po.getDescription());
                    break;
                case P:
                    System.out.println(ANSI_BOLD + "TITLE: " + ANSI_PURPLE + po.getName());
                    System.out.println(ANSI_BOLD + "DESCRIPTION: " + ANSI_PURPLE + po.getDescription());
                    break;
                case B:
                    System.out.println(ANSI_BOLD + "TITLE: " + ANSI_BLUE + po.getName());
                    System.out.println(ANSI_BOLD + "DESCRIPTION: " + ANSI_BLUE + po.getDescription());
                    break;
                case Y:
                    System.out.println(ANSI_BOLD + "TITLE: " + ANSI_YELLOW + po.getName());
                    System.out.println(ANSI_BOLD + "DESCRIPTION: " + ANSI_YELLOW + po.getDescription());
                    break;
                default:
                    System.out.println(ANSI_BOLD + "TITLE: " + po.getName());
                    System.out.println(ANSI_BOLD + "DESCRIPTION: " + po.getDescription());
                    break;
            }

            System.out.println("\n" + ANSI_RESET);
        }
    }
}
