package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class PrintUseToolCard implements Serializable {

    private static final String stop = "ENTER [-1] TO STOP. YOU WILL NOT BE SET AS USED";
    private static int tmp;

    /**
     * prints the possible input choices to use a toolcard
     * @param gameModel the gamemodel of the match
     * @param toolCard the list of 3 toolcards of the match
     * @param choices the list of integer that contains the client's inputs
     */
    public static void print(GameModel gameModel, ToolCard toolCard, ArrayList<Integer> choices){

        Scanner input;
        choices.clear();

        switch (toolCard.getNumber()){
            case 1:                 //GROZING PLIERS
                if (!selectDraft(gameModel, choices))
                    break;
                System.out.println("DO YOU WANT TO INCREASE OR DECREASE THE VALUE?");
                System.out.println("1) INCREASE\n2) DECREASE");
                do {
                    input = new Scanner(System.in);
                    tmp = input.nextInt();
                    if (tmp == 2) {
                        choices.add(-2);
                    } else if (tmp == 1) {
                        choices.add(1);
                    }else if(tmp == -1){
                        choices.add(0, tmp);
                        break;
                    } else {
                        System.out.println("Input error! Do it again correctly");
                    }
                }while(tmp<1 || tmp>2);
                break;
            case 2: case 3: case 4:                //EGLOMISE BRUSH & COPPER FOIL BURNISHER
                System.out.println("SELECT FROM YOUR WINDOW THE DIE TO BE MOVED");
                if (!selectPosition(gameModel, choices, true, true))
                    break;
                if(!selectPosition(gameModel, choices, false, false))
                    break;
                break;
            case 5:                 //LENS CUTTER
                if(!selectDraft(gameModel, choices))
                    break;
                System.out.println("SELECT A DIE FROM THE ROUNDTRACK");
                PrintRoundTrack.print(gameModel.getField().getRoundTrack());
                verifyInput(choices, 1, gameModel);
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
                if (!verifyInput(choices, 1, gameModel))
                    break;
                System.out.println("SELECT FROM YOUR WINDOW THE DIE TO BE MOVED");
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                if(!selectPosition(gameModel, choices, false, true))
                    break;
                if(!selectPosition(gameModel, choices, false, false))
                    break;
                break;

            default:    //GLAZING HAMMER - Vengono mischiati i dadi nella draft
                break;
        }
    }

    /**
     * asks to insert a number and verifies if the client's input is '-1' or isn't correct
     * @param choices the list of integer that contains the client's inputs
     * @param check a value to know what's the bounds of input
     * @param gameModel the gamemodel of the match
     * @return false if the input was -1, true otherwise
     */
    private static boolean verifyInput(ArrayList<Integer> choices, int check, GameModel gameModel){
        do {
            Scanner input = new Scanner(System.in);
            tmp = input.nextInt();
            if (tmp == -1) {
                choices.add(0, tmp);
                return false;
            }
        }while(!correctInput(tmp, check, gameModel));
        choices.add(tmp - 1);
        return true;
    }

    /**
     * asks the client to select a die from the draft
     * @param gameModel the gamemodel of the match
     * @param choices the list of integer that contains the client's inputs
     * @return false if the input was -1, true otherwise
     */
    private static boolean selectDraft(GameModel gameModel, ArrayList<Integer> choices){
        System.out.println("SELECT A DIE FROM THE DRAFT");
        System.out.println(stop);
        PrintDraft.print(gameModel.getField().getDraft());
        return (verifyInput(choices, 0, gameModel));
    }

    /**
     * prints some messages to ask the client to select the positions of the die to be moved and his next positions
     * @param gameModel the gamemodel of the match
     * @param choices the list of integer that contains the client's inputs
     * @param verify a boolean to know if it's necessary to verify the input -1
     * @param first a boolean to know what kind of message print to the client
     * @return false if (verify == true) and client's input was -1
     */
    private static boolean selectPosition(GameModel gameModel, ArrayList<Integer> choices, boolean verify, boolean first){

        if (verify){
            System.out.println(stop);
            PrintWindow.print(gameModel.getActualPlayer().getWindow());
        }
        if (first)
            System.out.println("SELECT THE ROW OF THE DIE TO BE MOVED");
        else
            System.out.println("SELECT THE ROW TO INSERT THE DIE");
        if (!verifyInput(choices, 2, gameModel))
            return false;

        if (first)
            System.out.println("SELECT THE COLUMN OF THE DIE TO BE MOVED");
        else
            System.out.println("SELECT THE COLUMN TO INSERT THE DIE");
        return verifyInput(choices, 3, gameModel);
    }

    /**
     * verify if the client's input is correct
     * @param i the client's input
     * @param check a value to know what's the bounds of input
     * @param gameModel the gamemodel of the match
     * @return true if the input is correct, false otherwise
     */
    private static boolean correctInput(int i, int check, GameModel gameModel){
        if(check == 0){             //check draft
            return checkInput(i, gameModel.getField().getDraft().getDraft().size()+1);
        }
        else if (check == 1){       //check roundtrack
            return checkInput(i, gameModel.getField().getRoundTrack().getGrid().size()+1);
        }
        else if (check == 2){       //check row
            return checkInput(i, 5);
        }
        else if (check == 3){       //check column
            return checkInput(i, 6);
        }
        return false;               //method error
    }

    /**
     * verifies if the input is correct or not
     * @param i the client's input
     * @param j the maximum input accepted
     * @return true if the input is correct, false otherwise
     */
    private static boolean checkInput(int i, int j){
        if(!(i > 0 && i < j))
            return error();
        else
            return true;
    }

    /**
     * prints a error message
     * @return false
     */
    private static boolean error(){
        System.out.println("Input error! Do it again correctly");
        return false;
    }
}
