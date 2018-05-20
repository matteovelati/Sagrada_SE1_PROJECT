package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class PrintUseToolCard3 implements Serializable {

    public static void print(GameModel gameModel, ToolCard toolCard, ArrayList<Integer> choices){

        Scanner input;

        switch (toolCard.getNumber()){
            case 11:
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                System.out.println("SELECT A ROW TO PUT THE DICE");
                input = new Scanner(System.in);
                choices.add(input.nextInt());
                System.out.println("SELECT A COLUMN TO PUT THE DICE");
                input = new Scanner(System.in);
                choices.add(input.nextInt());
                break;
            default :
                System.out.println("ERRORE");
        }
    }
}
