package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class PrintUseToolCard2 implements Serializable {

    private static final String STOP = "ENTER [-1] TO STOP. YOU WILL LOSE YOUR TOKENS!!!";

    public static void print(GameModel gameModel, ToolCard toolCard, ArrayList<Integer> choices){

        Scanner input;
        int tmp;

        switch (toolCard.getNumber()){
            case 1: case 5: case 10:         //GROZING PLIERS & LENS CUTTER & GRINDING STONE
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                System.out.println("SELECT THE ROW TO INSERT THE DIE IN [0,4]");
                System.out.println(STOP);
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if (tmp == -1) {
                    choices.add(0, tmp);
                    break;
                }
                choices.add(2, tmp-1);
                System.out.println("SELECT THE COLUMN TO INSERT THE DIE IN [0,5]");
                input = new Scanner(System.in);
                choices.add(3, input.nextInt()-1);
                break;
            case 4:                 //LATHEKIN
                System.out.println("SELECT FROM YOUR WINDOW THE DIE TO BE MOVED");
                if (!selectActualPosition(gameModel, choices, true))
                    break;
                selectNextPosition(gameModel, choices, false);
                break;
            case 6:                 //FLUX BRUSH
                System.out.println("YOU HAVE ROLLED THE DIE IN POSITION "+ choices.get(0)+1);
                PrintDraft.print(gameModel.getField().getDraft());
                System.out.println("INSERT THIS DICE IN YOUR WINDOW");
                selectNextPosition(gameModel, choices, true);
                break;
            case 11:                //FLUX REMOVER
                System.out.println("COLOR IS: "+ gameModel.getField().getDraft().getDraft().get(choices.get(0)).getColor());
                System.out.println("CHOOSE A NEW VALUE FOR THE DIE");
                System.out.println(STOP);
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if (tmp == -1) {
                    choices.add(0, tmp);
                    break;
                }
                choices.add(tmp);
                break;
            case 12:                //TAP WHEEL
                System.out.println("DO YOU WANT TO MOVE ANOTHER DIE?\n1) YES\n2) NO");
                input = new Scanner(System.in);
                tmp = input.nextInt();
                choices.add(tmp);
                if(tmp == 1){
                    System.out.println("SELECT FROM YOUR WINDOW THE DIE TO BE MOVED");
                    PrintWindow.print(gameModel.getActualPlayer().getWindow());
                    selectActualPosition(gameModel, choices, false);
                    selectNextPosition(gameModel, choices, false);
                }
                break;
            default :
                System.out.println("ERRORE");
        }
    }

    private static boolean verifyInput(ArrayList<Integer> choices){
        Scanner input = new Scanner(System.in);
        int tmp = input.nextInt();
        if (tmp == -1) {
            choices.add(0, tmp);
            return false;
        }
        choices.add(tmp-1);
        return true;
    }

    private static boolean selectActualPosition(GameModel gameModel, ArrayList<Integer> choices, boolean verify){
        Scanner input;
        if (verify){
            System.out.println(STOP);
            PrintWindow.print(gameModel.getActualPlayer().getWindow());
        }
        System.out.println("SELECT THE ROW OF THE DIE TO BE MOVED [0,4]");
        if (verify){
            if (!verifyInput(choices))
                return false;
        }
        else {
            input = new Scanner(System.in);
            choices.add(input.nextInt() - 1);
        }
        System.out.println("SELECT THE COLUMN OF THE DIE TO BE MOVED [0,5]");
        input = new Scanner(System.in);
        choices.add(input.nextInt()-1);
        return true;
    }

    private static boolean selectNextPosition(GameModel gameModel, ArrayList<Integer> choices, boolean verify){
        Scanner input;
        if (verify){
            System.out.println(STOP);
            PrintWindow.print(gameModel.getActualPlayer().getWindow());
        }
        System.out.println("SELECT THE ROW TO INSERT THE DIE IN [0,4]");
        if (verify){
            if (!verifyInput(choices))
                return false;
        }
        else {
            input = new Scanner(System.in);
            choices.add(input.nextInt() - 1);
        }
        System.out.println("SELECT THE COLUMN TO INSERT THE DIE IN [0,5]");
        input = new Scanner(System.in);
        choices.add(input.nextInt()-1);
        return true;
    }
}
