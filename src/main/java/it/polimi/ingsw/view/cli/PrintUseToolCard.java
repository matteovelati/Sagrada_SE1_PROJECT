package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class PrintUseToolCard implements Serializable {

    private static final String stop = "ENTER [-1] TO STOP. YOU WILL NOT LOSE YOUR TOKENS";

    public static void print(GameModel gameModel, ToolCard toolCard, ArrayList<Integer> choices){

        Scanner input;
        int tmp;
        choices.clear();

        switch (toolCard.getNumber()){
            case 1:                 //GROZING PLIERS
                if (!selectDraft(gameModel, choices))
                    break;
                System.out.println("DO YOU WANT TO INCREASE OR DECREASE THE VALUE?");
                System.out.println("1) INCREASE\n2) DECREASE");
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if(tmp == 2){
                    choices.add(-2);
                }
                else{
                    choices.add(1);
                }
                break;
            case 2: case 3: case 4:                //EGLOMISE BRUSH & COPPER FOIL BURNISHER
                System.out.println("SELECT FROM YOUR WINDOW THE DIE TO BE MOVED");
                if (!selectPosition(gameModel, choices, true, true))
                    break;
                selectPosition(gameModel, choices, false, false);
                break;
            case 5:                 //LENS CUTTER
                if(!selectDraft(gameModel, choices))
                    break;
                System.out.println("SELECT A DIE FROM THE ROUNDTRACK");
                PrintRoundTrack.print(gameModel.getField().getRoundTrack());
                verifyInput(choices);
                break;
            case 6:                 //FLUX BRUSH
                selectDraft(gameModel, choices);
                break;
            case 11:                 //FLUX REMOVER
                selectDraft(gameModel, choices);
                break;
            case 8: case 9:          //RUNNING PLIERS & CORK BACKED STRAIGHTEDGE
                if (!selectDraft(gameModel, choices))
                    break;
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                selectPosition(gameModel, choices, false, false);
                break;
            case 10:                //GRINDING STONE
                selectDraft(gameModel, choices);
                break;
            case 12:                //TAP WHEEL
                System.out.println("SELECT A DIE FROM THE ROUNDRACK");
                System.out.println(stop);
                PrintRoundTrack.print(gameModel.getField().getRoundTrack());
                if (!verifyInput(choices))
                    break;
                System.out.println("SELECT FROM YOUR WINDOW THE DIE TO BE MOVED");
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                selectPosition(gameModel, choices, false, true);
                selectPosition(gameModel, choices, false, false);
                break;

            default:    //GLAZING HAMMER - Vengono mischiati i dadi nella draft
                break;
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

    private static boolean selectDraft(GameModel gameModel, ArrayList<Integer> choices){
        System.out.println("SELECT A DIE FROM THE DRAFT");
        System.out.println(stop);
        PrintDraft.print(gameModel.getField().getDraft());
        return (verifyInput(choices));
    }

    private static boolean selectPosition(GameModel gameModel, ArrayList<Integer> choices, boolean verify, boolean first){
        Scanner input;
        if (verify){
            System.out.println(stop);
            PrintWindow.print(gameModel.getActualPlayer().getWindow());
        }
        if (first)
            System.out.println("SELECT THE ROW OF THE DIE TO BE MOVED [0,4]");
        else
            System.out.println("SELECT THE ROW TO INSERT THE DIE IN [0,4]");
        if (verify){
            if (!verifyInput(choices))
                return false;
        }
        else {
            input = new Scanner(System.in);
            choices.add(input.nextInt() - 1);
        }
        if (first)
            System.out.println("SELECT THE COLUMN OF THE DIE TO BE MOVED [0,5]");
        else
            System.out.println("SELECT THE COLUMN TO INSERT THE DIE IN [0,5]");
        input = new Scanner(System.in);
        choices.add(input.nextInt()-1);
        return true;
    }
}
