package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class PrintUseToolCard2 implements Serializable {

    private static final String STOP = "ENTER [-1] TO STOP. YOU WILL LOSE YOUR TOKENS!!!";

    public static void print(GameModel gameModel, ToolCard toolCard, ArrayList<Integer> choices){

        Scanner input;
        int tmp;

        switch (toolCard.getNumber()){
            case 1: case 5: case 10:         //GROZING PLIERS & LENS CUTTER & GRINDING STONE
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                System.out.println("SELECT A ROW TO PUT THE DIE");
                System.out.println(STOP);
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if (tmp == -1) {
                    choices.add(0, tmp);
                    break;
                }
                choices.add(tmp-1);
                System.out.println("SELECT A COLUMN TO PUT THE DIE");
                input = new Scanner(System.in);
                choices.add(input.nextInt()-1);
                break;
            case 4:                 //LATHEKIN
                System.out.println("SELECT FROM YOUR WINDOW THE DICE TO MOVE");
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                System.out.println("SELECT THE ACTUAL ROW");
                System.out.println(STOP);
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if (tmp == -1) {
                    choices.add(0, tmp);
                    break;
                }
                choices.add(tmp-1);
                System.out.println("SELECT THE ACTUAL COLUMN");
                input = new Scanner(System.in);
                choices.add(input.nextInt()-1);
                System.out.println("SELECT THE NEW ROW");
                input = new Scanner(System.in);
                choices.add(input.nextInt()-1);
                System.out.println("SELECT THE NEW COLUMN");
                input = new Scanner(System.in);
                choices.add(input.nextInt()-1);
                break;
            case 6:                 //FLUX BRUSH
                System.out.println("YOU HAVE ROLLED THE DIE IN POSITION "+ choices.get(0)+1);
                PrintDraft.print(gameModel.getField().getDraft());
                System.out.println("PUT THIS DICE IN YOUR WINDOW");
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                System.out.println("SELECT A ROW TO PUT THE DIE");
                System.out.println(STOP);
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if (tmp == -1) {
                    choices.add(0, tmp);
                    break;
                }
                choices.add(tmp-1);
                System.out.println("SELECT A COLUMN TO PUT THE DIE");
                input = new Scanner(System.in);
                choices.add(input.nextInt()-1);
                break;
            case 11:                //FLUX REMOVER
                System.out.println("COLOR IS: "+ gameModel.getField().getDraft().getDraft().get(choices.get(0)).getColor());
                System.out.println("TYPE A NEW VALUE OF THE DIE");
                System.out.println(STOP);
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if (tmp == -1) {
                    choices.add(0, tmp);
                    break;
                }
                choices.add(tmp-1);
                break;
            case 12:                //TAP WHEEL
                System.out.println("DO YOU WANT TO MOVE ANOTHER DIE?\n1) YES\n2) NO");
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if(tmp == 1){
                    choices.add(tmp);
                    System.out.println("SELECT FROM YOUR WINDOW THE DIE TO MOVE");
                    PrintWindow.print(gameModel.getActualPlayer().getWindow());
                    System.out.println("SELECT THE ACTUAL ROW");
                    input = new Scanner(System.in);
                    choices.add(input.nextInt()-1);
                    System.out.println("SELECT THE ACTUAL COLUMN");
                    input = new Scanner(System.in);
                    choices.add(input.nextInt()-1);
                    System.out.println("SELECT THE NEW ROW");
                    input = new Scanner(System.in);
                    choices.add(input.nextInt()-1);
                    System.out.println("SELECT THE NEW COLUMN");
                    input = new Scanner(System.in);
                    choices.add(input.nextInt()-1);
                }
                break;
            default :
                System.out.println("ERRORE");
        }
    }
}
