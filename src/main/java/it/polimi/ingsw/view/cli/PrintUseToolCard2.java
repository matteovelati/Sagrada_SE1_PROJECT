package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class PrintUseToolCard2 implements Serializable {

    public static void print(GameModel gameModel, ToolCard toolCard, ArrayList<Integer> choices){

        Scanner input;
        int tmp;

        switch (toolCard.getNumber()){
            case 4:                 //LATHEKIN
                System.out.println("SELECT FROM YOUR WINDOW THE DICE TO MOVE");
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                System.out.println("SELECT THE ACTUAL ROW");
                input = new Scanner(System.in);
                choices.add(input.nextInt());
                System.out.println("SELECT THE ACTUAL COLUMN");
                input = new Scanner(System.in);
                choices.add(input.nextInt());
                System.out.println("SELECT THE NEW ROW");
                input = new Scanner(System.in);
                choices.add(input.nextInt());
                System.out.println("SELECT THE NEW COLUMN");
                input = new Scanner(System.in);
                choices.add(input.nextInt());
                break;
            case 6:                 //FLUX BRUSH
                System.out.println("YOU HAVE ROLLED THE DICE IN POSITION "+ choices.get(0));
                PrintDraft.print(gameModel.getField().getDraft());
                System.out.println("PUT THIS DICE IN YOUR WINDOW");
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                System.out.println("SELECT A ROW TO PUT THE DICE");
                input = new Scanner(System.in);
                choices.add(input.nextInt());
                System.out.println("SELECT A COLUMN TO PUT THE DICE");
                input = new Scanner(System.in);
                choices.add(input.nextInt());
                break;
            case 11:                //FLUX REMOVER
                System.out.println("TYPE A NEW VALUE OF THE DICE");
                input = new Scanner(System.in);
                choices.add(input.nextInt());
                break;
            case 12:                //TAP WHEEL
                System.out.println("DO YOU WANT TO MOVE ANOTHER DICE?\n1) YES\n2) NO");
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if(tmp == 1){
                    choices.set(1, 1);
                    System.out.println("SELECT FROM YOUR WINDOW THE DICE TO MOVE");
                    PrintWindow.print(gameModel.getActualPlayer().getWindow());
                    System.out.println("SELECT THE ACTUAL ROW");
                    input = new Scanner(System.in);
                    choices.add(input.nextInt());
                    System.out.println("SELECT THE ACTUAL COLUMN");
                    input = new Scanner(System.in);
                    choices.add(input.nextInt());
                    System.out.println("SELECT THE NEW ROW");
                    input = new Scanner(System.in);
                    choices.add(input.nextInt());
                    System.out.println("SELECT THE NEW COLUMN");
                    input = new Scanner(System.in);
                    choices.add(input.nextInt());
                }
                break;
            default :
                System.out.println("ERRORE");
        }
    }
}
