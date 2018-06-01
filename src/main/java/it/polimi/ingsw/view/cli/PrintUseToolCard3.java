package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class PrintUseToolCard3 implements Serializable {

    private static final String STOP = "ENTER [-1] TO STOP. YOU WILL LOSE YOUR TOKENS!!!";

    public static void print(GameModel gameModel, ToolCard toolCard, ArrayList<Integer> choices){

        if (toolCard.getNumber() == 11){    //FLUX REMOVER
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                System.out.println("SELECT THE ROW TO INSERT THE DIE IN [0,4]");
                System.out.println(STOP);
                Scanner input = new Scanner(System.in);
                int tmp = input.nextInt();
                if (tmp == -1) {
                    choices.add(0, tmp);
                    return;
                }
                choices.add(tmp-1);
                System.out.println("SELECT THE COLUMN TO INSERT THE DIE IN [0,5]");
                input = new Scanner(System.in);
                choices.add(input.nextInt()-1);
        }
    }
}
