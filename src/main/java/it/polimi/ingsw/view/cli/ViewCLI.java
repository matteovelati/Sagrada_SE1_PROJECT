package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.States;

import java.util.Scanner;

public class ViewCLI implements ViewObserverCLI {

    private States state;
    private String user;
    private Scanner input;
    private int choose1;
    private int choose2;
    private int i;

    public ViewCLI(){
        input = new Scanner(System.in);
        System.out.println("ENTER YOUR USERNAME:");
        user = input.next();
    }

    public int getChoose1() {
        return choose1;
    }

    public int getChoose2() {
        return choose2;
    }

    @Override
    public void update(GameModel gameModel) {
        if(gameModel.getState().equals(States.ERROR)){
            System.out.println("SELECTION ERROR. PLEASE DO IT AGAIN CORRECTLY");
        }

        switch (state){
            case SELECTWINDOW:
                System.out.println("SELECT YOUR WINDOW!");
                PrintSchemeCard.print(gameModel.getSchemeCards());
                choose1 = input.nextInt();
                break;
            case SELECTDRAFT:
                i = ShowGameStuff.print(gameModel);
                while(i != 0){
                    i = ShowGameStuff.print(gameModel);
                }
                System.out.println("SELECT A DICE");
                PrintDraft.print(gameModel.getField().getDraft());
                choose1 = input.nextInt();
                break;
            case SELECTCARD:
                i = ShowGameStuff.print(gameModel);
                while(i != 0){
                    i = ShowGameStuff.print(gameModel);
                }
                System.out.println("SELECT A TOOLCARD");
                PrintToolCard.print(gameModel.getField().getToolCards());
                choose1 = input.nextInt();
                break;
            default:
                System.out.println("ERRORE INVIO VIEW");
        }
    }
}
