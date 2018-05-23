package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.ToolCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class PrintUseToolCard implements Serializable {

    private static final String stop = "ENTER [-1] TO STOP. YOU WILL NOT LOSE YOUR TOKENS";

    public static void print(GameModel gameModel, ToolCard toolCard, ArrayList<Integer> choices){

        Scanner input;
        choices.clear();
        int tmp;

        switch (toolCard.getNumber()){
            case 1:                 //GROZING PLIERS
                System.out.println("WHICH DIE DO YOU WANT TO MODIFY?");
                System.out.println(stop);
                PrintDraft.print(gameModel.getField().getDraft());
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if (tmp == -1) {
                    choices.set(0, tmp);
                    break;
                }
                choices.add(tmp-1);
                System.out.println("DO YOU WANT TO INCREASE OR DECREASE THE VALUE?");
                System.out.println("1) INCREASE\n2) DECREASE");
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if(tmp == 2){
                    choices.add(-1);
                }
                else{
                    choices.add(1);
                }
                break;
            case 2: case 3:                 //EGLOMISE BRUSH & COPPER FOIL BURNISHER
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                System.out.println("SELECT THE ROW OF THE DICE TO MOVE");
                System.out.println(stop);
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if (tmp == -1) {
                    choices.set(0, tmp);
                    break;
                }
                choices.add(tmp-1);
                System.out.println("SELECT THE COLUMN OF THE DICE TO MOVE");
                input = new Scanner(System.in);
                choices.add(input.nextInt()-1);
                System.out.println("SELECT THE NEW ROW OF THE DICE");
                input = new Scanner(System.in);
                choices.add(input.nextInt()-1);
                System.out.println("SELECT THE NEW COLUMN OF THE DICE");
                input = new Scanner(System.in);
                choices.add(input.nextInt()-1);
                break;
            case 4:                 //LATHEKIN
                System.out.println("SELECT FROM YOUR WINDOW THE DICE TO MOVE");
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                System.out.println("SELECT THE ACTUAL ROW");
                System.out.println(stop);
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if (tmp == -1) {
                    choices.set(0, tmp);
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
            case 5:                 //LENS CUTTER
                System.out.println("SELECT A DICE FROM THE DRAFT");
                System.out.println(stop);
                PrintDraft.print(gameModel.getField().getDraft());
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if (tmp == -1) {
                    choices.set(0, tmp);
                    break;
                }
                choices.add(tmp-1);
                System.out.println("SELECT A DICE FROM THE ROUNDTRACK");
                PrintRoundTrack.print(gameModel.getField().getRoundTrack());
                input = new Scanner(System.in);
                choices.add(input.nextInt()-1);
                break;
            case 6:                 //FLUX BRUSH
                System.out.println("SELECT A DICE FROM THE DRAFT TO RE-ROLL");
                PrintDraft.print(gameModel.getField().getDraft());
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if (tmp == -1) {
                    choices.set(0, tmp);
                    break;
                }
                choices.add(tmp-1);
                break;
            case 7:                 //GLAZING HAMMER
                //Vengono mischiati i dadi nella draft
                break;
            case 8: case 11:                 //RUNNING PLIERS & FLUX REMOVER
                System.out.println("SELECT A DICE FROM THE DRAFT");
                System.out.println(stop);
                PrintDraft.print(gameModel.getField().getDraft());
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if (tmp == -1) {
                    choices.set(0, tmp);
                    break;
                }
                choices.add(tmp-1);
                break;
            case 9:                 //CORK BACKED STRAIGHTEDGE
                System.out.println("SELECT A DICE FROM THE DRAFT");
                System.out.println(stop);
                PrintDraft.print(gameModel.getField().getDraft());
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if (tmp == -1) {
                    choices.set(0, tmp);
                    break;
                }
                choices.add(tmp-1);
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                System.out.println("SELECT A ROW TO PUT THE DICE");
                input = new Scanner(System.in);
                choices.add(input.nextInt()-1);
                System.out.println("SELECT A COLUMN TO PUT THE DICE");
                input = new Scanner(System.in);
                choices.add(input.nextInt()-1);
                break;
            case 10:                //GRINDING STONE
                System.out.println("SELECT A DICE IN THE DRAFT TO FLIP");
                System.out.println(stop);
                PrintDraft.print(gameModel.getField().getDraft());
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if (tmp == -1) {
                    choices.set(0, tmp);
                    break;
                }
                choices.add(tmp-1);
                break;
            case 12:                //TAP WHEEL
                System.out.println("SELECT A DICE FROM THE ROUNDRACK");
                System.out.println(stop);
                PrintRoundTrack.print(gameModel.getField().getRoundTrack());
                input = new Scanner(System.in);
                tmp = input.nextInt();
                if (tmp == -1) {
                    choices.set(0, tmp);
                    break;
                }
                choices.add(tmp-1);
                System.out.println("SELECT FROM YOUR WINDOW THE DICE TO MOVE");
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
                break;
            default:
                break;
        }
    }
}
