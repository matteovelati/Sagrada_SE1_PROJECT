package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class PrintUseToolCard3 implements Serializable {

    private static final String STOP = "ENTER [-1] TO STOP. WARNING: THE CARD WILL BE SET AS USED";

    /**
     * prints some messages to ask the client to select the positions where he would like to place the die
     * @param gameModel the gamemodel of the match
     * @param toolCard the list of 3 toolcards of the match
     * @param choices the list of integer that contains the client's inputs
     */
    public static void print(GameModel gameModel, ToolCard toolCard, ArrayList<Integer> choices){
        Scanner input;
        int tmp;

        if (toolCard.getNumber() == 11){    //FLUX REMOVER
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                System.out.println("SELECT THE ROW TO INSERT THE DIE");
                System.out.println(STOP);
                do {
                    input = new Scanner(System.in);
                    tmp = input.nextInt();
                    if (tmp == -1) {
                        choices.add(0, tmp);
                        return;
                    }
                }while(!correctInput(tmp, 2));
                try {
                    choices.get(2);
                    choices.set(2, tmp-1);
                } catch (IndexOutOfBoundsException e){
                    choices.add(tmp-1);
                }
                System.out.println("SELECT THE COLUMN TO INSERT THE DIE");
                do {
                    input = new Scanner(System.in);
                    tmp = input.nextInt();
                    if (tmp == -1) {
                        choices.add(0, tmp);
                        return;
                    }
                }while(!correctInput(tmp, 3));
                try {
                    choices.get(3);
                    choices.set(3, tmp-1);
                } catch (IndexOutOfBoundsException e){
                    choices.add(tmp-1);
                }
        }
    }

    /**
     * verify if the client's input is correct
     * @param i the client's input
     * @param check a value to know what's the bounds of input
     * @return true if the input is correct, false otherwise
     */
    private static boolean correctInput(int i, int check){
        if (check == 2){       //check row
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
