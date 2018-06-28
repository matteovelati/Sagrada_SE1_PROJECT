package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.view.RemoteView;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class PrintUseToolCard implements Serializable {

    private static final String STOP = "ENTER [-1] TO STOP. THE CARD WILL NOT BE SET AS USED";
    private static int tmp;

    /**
     * prints the possible input choices to use a toolcard
     * @param gameModel the gamemodel of the match
     * @param toolCard the list of 3 toolcards of the match
     * @param choices the list of integer that contains the client's inputs
     */
    public static void print(GameModel gameModel, ToolCard toolCard, ArrayList<Integer> choices, RemoteView view) throws RemoteException {

        Scanner input;
        choices.clear();

        switch (toolCard.getNumber()){
            case 1:                 //GROZING PLIERS
                if (!selectDraft(gameModel, choices, view))
                    break;
                System.out.println("DO YOU WANT TO INCREASE OR DECREASE THE VALUE?");
                System.out.println("1) INCREASE\n2) DECREASE");
                do {
                    input = new Scanner(System.in);
                    while (!input.hasNextInt())
                        input = new Scanner(System.in);
                    tmp = input.nextInt();
                    if (!view.getOnline())
                        return;
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
            case 2: case 3: case 4:       //EGLOMISE BRUSH & COPPER FOIL BURNISHER & LATHEKIN
                System.out.println("SELECT FROM YOUR WINDOW THE DIE TO BE MOVED");
                if (!selectPosition(gameModel, choices, true, true, view))
                    break;
                if(!selectPosition(gameModel, choices, false, false, view))
                    break;
                break;
            case 5:                 //LENS CUTTER
                if(!selectDraft(gameModel, choices, view))
                    break;
                System.out.println("SELECT A DIE FROM THE ROUNDTRACK");
                PrintRoundTrack.print(gameModel.getField().getRoundTrack(), 0);
                verifyInput(choices, 1, gameModel, view);
                break;
            case 6:                 //FLUX BRUSH
                selectDraft(gameModel, choices, view);
                break;
            case 11:                 //FLUX REMOVER
                selectDraft(gameModel, choices, view);
                break;
            case 8: case 9:          //RUNNING PLIERS & CORK BACKED STRAIGHTEDGE
                if (!selectDraft(gameModel, choices, view))
                    break;
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                selectPosition(gameModel, choices, false, false, view);
                break;
            case 10:                //GRINDING STONE
                selectDraft(gameModel, choices, view);
                choices.add(0);
                break;
            case 12:                //TAP WHEEL
                System.out.println("SELECT A DIE FROM THE ROUNDRACK");
                System.out.println(STOP);
                PrintRoundTrack.print(gameModel.getField().getRoundTrack(), 0);
                if (!verifyInput(choices, 1, gameModel, view))
                    break;
                System.out.println("SELECT FROM YOUR WINDOW THE DIE TO BE MOVED");
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                if(!selectPosition(gameModel, choices, false, true, view))
                    break;
                if(!selectPosition(gameModel, choices, false, false, view))
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
    private static boolean verifyInput(ArrayList<Integer> choices, int check, GameModel gameModel, RemoteView view) throws RemoteException{
        do {
            Scanner input = new Scanner(System.in);
            while (!input.hasNextInt())
                input = new Scanner(System.in);
            tmp = input.nextInt();
           // if (!view.getOnline())
             //   return false;
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
    private static boolean selectDraft(GameModel gameModel, ArrayList<Integer> choices, RemoteView view) throws RemoteException{
        System.out.println("SELECT A DIE FROM THE DRAFT");
        System.out.println(STOP);
        PrintDraft.print(gameModel.getField().getDraft());
        return (verifyInput(choices, 0, gameModel, view));
    }

    /**
     * prints some messages to ask the client to select the positions of the die to be moved and his next positions
     * @param gameModel the gamemodel of the match
     * @param choices the list of integer that contains the client's inputs
     * @param verify a boolean to know if it's necessary to verify the input -1
     * @param first a boolean to know what kind of message print to the client
     * @return false if (verify == true) and client's input was -1
     */
    private static boolean selectPosition(GameModel gameModel, ArrayList<Integer> choices, boolean verify, boolean first, RemoteView view) throws RemoteException{

        if (verify){
            System.out.println(STOP);
            PrintWindow.print(gameModel.getActualPlayer().getWindow());
        }
        if (first)
            System.out.println("SELECT THE ROW OF THE DIE TO BE MOVED");
        else
            System.out.println("SELECT THE ROW TO INSERT THE DIE");
        if (!verifyInput(choices, 2, gameModel, view))
            return false;

        if (first)
            System.out.println("SELECT THE COLUMN OF THE DIE TO BE MOVED");
        else
            System.out.println("SELECT THE COLUMN TO INSERT THE DIE");
        return verifyInput(choices, 3, gameModel, view);
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
