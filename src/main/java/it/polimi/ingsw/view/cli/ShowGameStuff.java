package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameModel;
import java.util.Scanner;

public class ShowGameStuff {

    /**
     * prints the client's possible choices
     * @param gameModel the gamemodel of the match
     * @return the client's input
     */
    public static int print(GameModel gameModel, boolean SP) {

        System.out.println("\nWHAT DO YOU WANT TO DO?");
        System.out.println("1) SEE YOUR WINDOW");
        System.out.println("2) SEE THE DRAFT");
        System.out.println("3) SEE THE TOOLCARDS");
        System.out.println("4) SEE THE PUBLIC OBJECTIVES");
        System.out.println("5) SEE YOUR PRIVATE OBJECTIVES");
        System.out.println("6) SEE THE ROUNDTRACK");
        if (!SP) {
            System.out.println("7) SEE THE WINDOWS OF OTHER PLAYERS");
            System.out.println("8) SEE YOUR TOKENS");
        }
        System.out.println("0) CONTINUE WITH YOUR TURN");

        Scanner input = new Scanner(System.in);
        while(!input.hasNextInt())
            input = new Scanner(System.in);
        int choose = input.nextInt();

        switch (choose){
            case 1:
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                break;
            case 2:
                PrintDraft.print(gameModel.getField().getDraft());
                break;
            case 3:
                PrintToolCard.print(gameModel.getField().getToolCards());
                break;
            case 4:
                PrintPublicObjectives.print(gameModel.getField().getPublicObjectives());
                break;
            case 5:
                PrintPrivateObjective.print(gameModel.getActualPlayer().getPrivateObjectives());
                break;
            case 6:
                if (!SP)
                    PrintRoundTrack.print(gameModel.getField().getRoundTrack(), 0);
                else
                    PrintRoundTrack.print(gameModel.getField().getRoundTrack(), gameModel.getField().getRoundTrack().calculateRoundTrack());
                break;
            case 7:
                if (!SP) {
                    for (int i = 0; i < gameModel.getPlayers().size(); i++) {
                        if (!gameModel.getPlayers().get(i).equals(gameModel.getActualPlayer())) {
                            System.out.println(gameModel.getPlayers().get(i).getUsername() + "'s WINDOW");
                            PrintWindow.print(gameModel.getPlayers().get(i).getWindow());
                        }
                    }
                }
                break;
            case 8:
                if (!SP)
                    System.out.println(gameModel.getActualPlayer().getTokens());
                break;
            case 0:
                break;
            default :
                break;
        }
        return choose;
    }
}
