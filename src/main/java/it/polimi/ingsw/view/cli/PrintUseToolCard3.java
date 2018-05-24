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

        switch (toolCard.getNumber()){
            case 11:            //FLUX REMOVER
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                System.out.println("SELECT A ROW TO PUT THE DICE");
                System.out.println(STOP);
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if (tmp == -1) {
                    choices.add(0, tmp);
                    break;
                }
                choices.add(tmp-1);
                System.out.println("SELECT A COLUMN TO PUT THE DICE");
                input = new Scanner(System.in);
                choices.add(input.nextInt()-1);
                break;
            default :
                System.out.println("ERRORE");
        }
    }
}
