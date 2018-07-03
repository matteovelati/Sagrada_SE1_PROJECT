package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Window;
import java.io.Serializable;

public class PrintWindow implements Serializable {

    private static final String FORMAT = "       ";
    private static final String ANSI_RESET = "\u001b[0m";
    private static final String ANSI_BOLD = "\u001b[1m";
    private static final String ANSI_REVERSE = "\u001b[7m";
    private static final String ANSI_WHITEONRED = "\u001b[30;41m";
    private static final String ANSI_WHITEONGREEN = "\u001b[30;42m";
    private static final String ANSI_WHITEONPURPLE = "\u001b[30;45m";
    private static final String ANSI_WHITEONBLUE = "\u001b[30;44m";
    private static final String ANSI_WHITEONYELLOW = "\u001b[30;43m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_PURPLE	= "\u001B[35m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    /**
     * prints the matrix of spaces and his dice
     * @param window the player's window
     */
    public static void print(Window window){
        System.out.println(ANSI_BOLD + window.getName() +"  "+window.getDifficulty());
        Colors colortmp = Colors.W;
        for(int i=0; i<4; i++){
            System.out.println("\n| - - - | - - - | - - - | - - - | - - - |");
            System.out.print("|");
            for(int j=0; j<5; j++){
                if (window.getWindow()[i][j].getColor().equals(Colors.W))   colortmp = Colors.W;
                else if (window.getWindow()[i][j].getColor().equals(Colors.R))   colortmp = Colors.R;
                else if (window.getWindow()[i][j].getColor().equals(Colors.G))   colortmp = Colors.G;
                else if (window.getWindow()[i][j].getColor().equals(Colors.P))   colortmp = Colors.P;
                else if (window.getWindow()[i][j].getColor().equals(Colors.B))   colortmp = Colors.B;
                else if (window.getWindow()[i][j].getColor().equals(Colors.Y))   colortmp = Colors.Y;

                switch (colortmp){
                    case R:
                        if(window.getWindow()[i][j].getIsEmpty()){
                            System.out.print(ANSI_WHITEONRED + FORMAT + ANSI_RESET);
                            System.out.print("|");
                        }
                        else {
                            System.out.print(" " + ANSI_RED + ANSI_REVERSE + "[" + window.getWindow()[i][j].getDice().getValue() + ":" + window.getWindow()[i][j].getDice().getColor() + "]" + ANSI_RESET + " ");
                            System.out.print("|");
                        }
                        break;
                    case G:
                        if(window.getWindow()[i][j].getIsEmpty()){
                            System.out.print(ANSI_WHITEONGREEN + FORMAT + ANSI_RESET);
                            System.out.print("|");
                        }
                        else {
                            System.out.print(" " + ANSI_GREEN  + ANSI_REVERSE + "[" + window.getWindow()[i][j].getDice().getValue() + ":" + window.getWindow()[i][j].getDice().getColor() + "]"+ ANSI_RESET + " ");
                            System.out.print("|");
                        }
                        break;
                    case P:
                        if(window.getWindow()[i][j].getIsEmpty()){
                            System.out.print(ANSI_WHITEONPURPLE + FORMAT + ANSI_RESET);
                            System.out.print("|");
                        }
                        else {
                            System.out.print(" " +ANSI_PURPLE + ANSI_REVERSE + "[" + window.getWindow()[i][j].getDice().getValue() + ":" + window.getWindow()[i][j].getDice().getColor() + "]"+ ANSI_RESET + " ");
                            System.out.print("|");
                        }
                        break;
                    case B:
                        if(window.getWindow()[i][j].getIsEmpty()){
                            System.out.print(ANSI_WHITEONBLUE + FORMAT + ANSI_RESET);
                            System.out.print("|");
                        }
                        else {
                            System.out.print(" "+ANSI_BLUE + ANSI_REVERSE + "[" + window.getWindow()[i][j].getDice().getValue() + ":" + window.getWindow()[i][j].getDice().getColor() + "]"+ ANSI_RESET + " ");
                            System.out.print("|");
                        }
                        break;
                    case Y:
                        if(window.getWindow()[i][j].getIsEmpty()){
                            System.out.print(ANSI_WHITEONYELLOW + FORMAT + ANSI_RESET);
                            System.out.print("|");
                        }
                        else {
                            System.out.print(" " + ANSI_YELLOW + ANSI_REVERSE + "[" + window.getWindow()[i][j].getDice().getValue() + ":" + window.getWindow()[i][j].getDice().getColor() + "]"+ ANSI_RESET + " ");
                            System.out.print("|");
                        }
                        break;
                    default:
                        if(window.getWindow()[i][j].getIsEmpty()){
                            System.out.print("   " + ((window.getWindow()[i][j].getValue() == 0) ? " " : window.getWindow()[i][j].getValue())  +"   "+ ANSI_RESET);
                            System.out.print("|");
                        }
                        else {
                            System.out.print(" [" + window.getWindow()[i][j].getDice().getValue() + ":" + window.getWindow()[i][j].getDice().getColor() + "]"+ ANSI_RESET + " ");
                            System.out.print("|");
                        }
                        break;
                }
            }
        }
        System.out.println("\n| - - - | - - - | - - - | - - - | - - - |\n");
    }
}
