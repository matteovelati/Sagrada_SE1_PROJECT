package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.States;
import it.polimi.ingsw.view.View;

import java.util.Scanner;

public class ViewCLI extends View implements ViewObserverCLI {

    private States state;
    private String user;
    private Scanner input;


    public ViewCLI(){
        input = new Scanner(System.in);
        System.out.println("ENTER YOUR USERNAME:");
        user = input.next();
    }

    @Override
    public void update(GameModel gameModel) {
        int tmp;
        if(gameModel.getState().equals(States.ERROR)){
            System.out.println("SELECTION ERROR. PLEASE DO IT AGAIN CORRECTLY");
        }
        else{
            state = gameModel.getState();
        }

        switch (state){
            case SELECTWINDOW:
                System.out.println("SELECT YOUR WINDOW!");
                PrintSchemeCard.print(gameModel.getSchemeCards());
                input = new Scanner(System.in);
                setChoose1(input.nextInt());
                break;
            case SELECTMOVE1:
                tmp = ShowGameStuff.print(gameModel);
                while(tmp != 0){
                    tmp = ShowGameStuff.print(gameModel);
                }
                PrintSelectMove1.print();
                input = new Scanner(System.in);
                setChoose1(input.nextInt());
                break;
            case SELECTMOVE2:
                tmp = ShowGameStuff.print(gameModel);
                while(tmp != 0){
                    tmp = ShowGameStuff.print(gameModel);
                }
                PrintSelectMove2.print();
                input = new Scanner(System.in);
                setChoose1(input.nextInt());
                break;
            case PUTDICEINWINDOW:
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                System.out.println("CHOOSE A ROW TO PUT YOUR DICE");
                input = new Scanner(System.in);
                setChoose1(input.nextInt());
                System.out.println("CHOOSE A COLUMN TO PUT YOUR DICE");
                input = new Scanner(System.in);
                setChoose2(input.nextInt());
                break;
            case SELECTDRAFT:
                tmp = ShowGameStuff.print(gameModel);
                while(tmp != 0){
                    tmp = ShowGameStuff.print(gameModel);
                }
                System.out.println("SELECT A DICE");
                PrintDraft.print(gameModel.getField().getDraft());
                input = new Scanner(System.in);
                setChoose1(input.nextInt());
                break;
            case SELECTCARD:
                tmp = ShowGameStuff.print(gameModel);
                while(tmp != 0){
                    tmp = ShowGameStuff.print(gameModel);
                }
                System.out.println("SELECT A TOOLCARD");
                PrintToolCard.print(gameModel.getField().getToolCards());
                input = new Scanner(System.in);
                setChoose1(input.nextInt());
                break;
            case USETOOLCARD:
                //Da decidere assieme come farlo
                break;
            case ENDROUND:
                System.out.println("END OF ROUND "+ gameModel.getField().getRoundTrack().getRound());
                break;
            case SCORECALCULATION:
                break;
            case ERROR:
                break;
            default:
                System.out.println("ERRORE INVIO VIEW");
                break;
        }
    }
}
