package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.ToolCard;

import java.io.Serializable;
import java.util.ArrayList;

public class PrintToolCard implements Serializable {

    private static final String ANSI_RESET = "\u001b[0m";
    private static final String ANSI_BOLD = "\u001b[1m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_PURPLE	= "\u001B[35m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    /**
     * prints the toolcards
     * @param toolCards the list of toolcards of the game
     */

    public static void print(ArrayList<ToolCard> toolCards){
        Colors colortmp = Colors.W;
        for(int i=0; i<toolCards.size(); i++){
            if (toolCards.get(i).getColor() == Colors.R)    colortmp = Colors.R;
            else if (toolCards.get(i).getColor() == Colors.G)   colortmp = Colors.G;
            else if (toolCards.get(i).getColor() == Colors.P)   colortmp = Colors.P;
            else if (toolCards.get(i).getColor() == Colors.B)   colortmp = Colors.B;
            else if (toolCards.get(i).getColor() == Colors.Y)   colortmp = Colors.Y;

            switch (colortmp){
                case R:
                    System.out.println(ANSI_BOLD + (i+1) +") Title: " + ANSI_RED + toolCards.get(i).getTitle());
                    System.out.println(ANSI_BOLD + "Description: " + ANSI_RED + toolCards.get(i).getDescr());
                    break;
                case G:
                    System.out.println(ANSI_BOLD + (i+1) +") Title: " + ANSI_GREEN + toolCards.get(i).getTitle());
                    System.out.println(ANSI_BOLD + "Description: " + ANSI_GREEN + toolCards.get(i).getDescr());
                    break;
                case P:
                    System.out.println(ANSI_BOLD + (i+1) +") Title: " + ANSI_PURPLE + toolCards.get(i).getTitle());
                    System.out.println(ANSI_BOLD + "Description: " + ANSI_PURPLE + toolCards.get(i).getDescr());
                    break;
                case B:
                    System.out.println(ANSI_BOLD + (i+1) +") Title: " + ANSI_BLUE + toolCards.get(i).getTitle());
                    System.out.println(ANSI_BOLD + "Description: " + ANSI_BLUE + toolCards.get(i).getDescr());
                    break;
                case Y:
                    System.out.println(ANSI_BOLD + (i+1) +") Title: " + ANSI_YELLOW + toolCards.get(i).getTitle());
                    System.out.println(ANSI_BOLD + "Description: " + ANSI_YELLOW + toolCards.get(i).getDescr());
                    break;
                default:
                    System.out.println(ANSI_BOLD + (i+1) +") Title: " + toolCards.get(i).getTitle());
                    System.out.println(ANSI_BOLD + "Description: " + toolCards.get(i).getDescr());
                    break;

            }
            System.out.println(ANSI_RESET);
        }
    }
}

