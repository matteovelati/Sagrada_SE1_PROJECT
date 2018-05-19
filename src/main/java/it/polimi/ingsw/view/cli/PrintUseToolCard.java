package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class PrintUseToolCard implements Serializable {

    public static void print(GameModel gameModel, ToolCard toolCard, ArrayList<Integer> choices){

        Scanner input;

        switch (toolCard.getNumber()){
            case 1:
                break;
            case 2:
                break;
            case 3:                 //COPPER FOIL BURNISHER
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                System.out.println("SELECT THE ROW OF THE DICE TO MOVE");
                input = new Scanner(System.in);
                choices.add(input.nextInt());
                System.out.println("SELECT THE COLUMN OF THE DICE TO MOVE");
                input = new Scanner(System.in);
                choices.add(input.nextInt());
                System.out.println("SELECT THE NEW ROW OF THE DICE");
                input = new Scanner(System.in);
                choices.add(input.nextInt());
                System.out.println("SELECT THE NEW COLUMN OF THE DICE");
                input = new Scanner(System.in);
                choices.add(input.nextInt());
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:                 //CORK BACKED STRAIGHTEDGE
                PrintDraft.print(gameModel.getField().getDraft());
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            default:
                break;
        }
    }
}
