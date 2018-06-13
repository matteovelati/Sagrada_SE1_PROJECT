package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class PrintUseToolCard2 implements Serializable {

    private static final String INPUT_ERR = "Input error! Do it again correctly";
    private static final String STOP = "ENTER [-1] TO STOP. WARNING: THE CARD WILL BE SET AS USED";
    private static int tmp;

    /**
     * prints the possible input choices to use a toolcard
     * @param gameModel the gamemodel of the match
     * @param toolCard the list of 3 toolcards of the match
     * @param choices the list of integer that contains the client's inputs
     */
    public static void print(GameModel gameModel, ToolCard toolCard, ArrayList<Integer> choices){

        Scanner input;

        switch (toolCard.getNumber()){
            case 1: case 5: case 10:         //GROZING PLIERS & LENS CUTTER & GRINDING STONE
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                System.out.println("SELECT THE ROW TO INSERT THE DIE");
                System.out.println(STOP);
                do {
                    input = new Scanner(System.in);
                    tmp = input.nextInt();
                    if (tmp == -1) {
                        choices.add(0, tmp);
                        break;
                    }
                }while(!correctInput(tmp, 2, gameModel));
                if(choices.get(0) == -1)
                    break;
                choices.add(tmp-1);
                System.out.println("SELECT THE COLUMN TO INSERT THE DIE");
                do {
                    input = new Scanner(System.in);
                    tmp = input.nextInt();
                    if (tmp == -1) {
                        choices.add(0, tmp);
                        break;
                    }
                }while(!correctInput(tmp, 3, gameModel));
                if(choices.get(0) == -1)
                    break;
                choices.add(tmp-1);
                break;
            case 4:                 //LATHEKIN
                System.out.println("SELECT FROM YOUR WINDOW THE DIE TO BE MOVED");
                if (!selectPosition(gameModel, choices, true, true))
                    break;
                if(!selectPosition(gameModel, choices, false, false))
                    break;
                break;
            case 6:                 //FLUX BRUSH
                System.out.println("YOU HAVE ROLLED THE DIE IN POSITION "+ choices.get(0)+1);
                PrintDraft.print(gameModel.getField().getDraft());
                System.out.println("INSERT THIS DICE IN YOUR WINDOW");
                if(!selectPosition(gameModel, choices, true, false))
                    break;
                break;
            case 11:                //FLUX REMOVER
                System.out.println("COLOR IS: "+ gameModel.getField().getDraft().getDraft().get(choices.get(0)).getColor());
                System.out.println("CHOOSE A NEW VALUE FOR THE DIE");
                System.out.println(STOP);
                do {
                    input = new Scanner(System.in);
                    tmp = input.nextInt();
                    if (tmp == -1) {
                        choices.add(0, tmp);
                        break;
                    }
                    if(tmp<1 || tmp>6)
                        System.out.println(INPUT_ERR);
                }while(tmp<1 || tmp>6);
                choices.add(tmp);
                break;
            case 12:                //TAP WHEEL
                System.out.println("DO YOU WANT TO MOVE ANOTHER DIE?\n1) YES\n2) NO");
                do {
                    input = new Scanner(System.in);
                    tmp = input.nextInt();
                    if(tmp<1 || tmp>2)
                        System.out.println(INPUT_ERR);
                }while(tmp<1 || tmp>2);
                choices.add(tmp);
                if(tmp == 1){
                    System.out.println("SELECT FROM YOUR WINDOW THE DIE TO BE MOVED");
                    PrintWindow.print(gameModel.getActualPlayer().getWindow());
                    if(!selectPosition(gameModel, choices, false, true))
                        break;
                    if(!selectPosition(gameModel, choices, false, false))
                        break;
                }
                break;
            default :
                System.out.println("ERRORE");
        }
    }

    /**
     * asks to insert a number and verifies if the client's input is '-1'
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
     * prints some messages to ask the client to select the positions of the die to be moved and his next positions
     * @param gameModel the gamemodel of the match
     * @param choices the list of integer that contains the client's inputs
     * @param verify a boolean to know if it's necessary to verify the input -1
     * @param first a boolean to know what kind of message print to the client
     * @return false if (verify == true) and client's input was -1
     */
    private static boolean selectPosition(GameModel gameModel, ArrayList<Integer> choices, boolean verify, boolean first){

        if (verify){
            System.out.println(STOP);
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
        if (check == 2){            //check row
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
        System.out.println(INPUT_ERR);
        return false;
    }
}
