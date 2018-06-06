package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class PrintUseToolCard3 implements Serializable {

    private static final String STOP = "ENTER [-1] TO STOP. YOU WILL LOSE YOUR TOKENS!!!";

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
                }while(!correctInput(tmp, 2, gameModel));
                choices.add(tmp-1);
                System.out.println("SELECT THE COLUMN TO INSERT THE DIE");
                do {
                    input = new Scanner(System.in);
                    tmp = input.nextInt();
                    if (tmp == -1) {
                        choices.add(0, tmp);
                        return;
                    }
                }while(!correctInput(tmp, 3, gameModel));
        }
    }

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

    private static boolean checkInput(int i, int j){
        if(!(i > 0 && i < j))
            return error();
        else
            return true;
    }

    private static boolean error(){
        System.out.println("Input error! Do it again correctly");
        return false;
    }
}
