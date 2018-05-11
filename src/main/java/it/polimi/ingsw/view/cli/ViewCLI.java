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

    public ViewCLI(){
        input = new Scanner(System.in);
        System.out.println("ENTER YOUR USERNAME:");
        user = input.next();
    }

    @Override
    public void update(GameModel gameModel) {
        if(gameModel.getTurn().getActualPlayer().getUsername().equals(user)){
            if(state.equals(States.ERROR)){
                System.out.println("SELECTION ERROR. PLEASE DO IT AGAIN CORRECTLY");
            }
            else{
                state = gameModel.getState();
            }

            //DEVO CREARE UNA FUNZIONE CHE CHIEDE AL GIOCATORE SE VUOLE VEDERE:
            //  - LA SUA WINDOW
            //  - LA DRAFT
            //  - LE TOOLCARDS
            //  - LE WINDOW DEGLI AVVERSARI

            switch (state){
                case SELECTWINDOW:
                    System.out.println("SELECT YOUR WINDOW!");
                    PrintSchemeCard.print(gameModel.getSchemeCards());
                    choose1 = input.nextInt();
                    break;
                case SELECTDRAFT:
                    System.out.println("SELECT A DICE");
                    PrintDraft.print(gameModel.getField().getDraft());
                    choose1 = input.nextInt();
                    break;
                case SELECTCARD:
                    System.out.println("SELECT A TOOLCARD");
                    PrintToolCard.print(gameModel.getField().getToolCards());
                    choose1 = input.nextInt();
                    break;

                default:
                    System.out.println("ERRORE INVIO VIEW");
            }
        }
        else{
            System.out.println("WAIT YOUR TURN");
        }
    }
}
