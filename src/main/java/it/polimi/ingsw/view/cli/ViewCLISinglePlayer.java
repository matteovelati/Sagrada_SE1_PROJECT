package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.RemoteGameController;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.RemoteGameModel;
import it.polimi.ingsw.model.States;
import it.polimi.ingsw.view.RemoteView;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewCLISinglePlayer extends UnicastRemoteObject implements RemoteView, Serializable {

    private States state;
    private String user;
    private transient Scanner input;
    private boolean endGame;
    private int choose1;
    private int choose2;
    private ArrayList<Integer> choices;
    private boolean online;
    private RemoteGameModel gameModel;
    private RemoteGameController network;

    public ViewCLISinglePlayer(RemoteGameController network) throws RemoteException {
        System.out.println("WELCOME TO SAGRADA! \n\n\n");
        System.out.println("When playing Sagrada by yourself, you're trying to beat a Target Score." +
        "The Target Score is the sum of the values from all the dice on the RoundTrack at the end of the game. \n");
        choices = new ArrayList<>();
        setOnline(true);
        endGame = false;
        this.network = network;
        gameModel = network.getGameModel();
        input = new Scanner(System.in);
        System.out.println("ENTER YOUR USERNAME:");
        this.user = input.next().toUpperCase();
    }


    /**
     * sets if the client is online or not
     * @param online the boolean to be set
     */
    @Override
    public synchronized void setOnline(boolean online){
        this.online = online;
        if(!online){
            this.print("\n\nYOU ARE NOW INACTIVE! TO JOIN AGAIN THE MATCH, PLEASE PRESS 0");
        }
    }

    /**
     * gets if player is online or not
     * @return true if the player is online, false otherwise
     */
    @Override
    public synchronized boolean getOnline(){
        return online;
    }

    /**
     * gets the client's username
     * @return the client's username
     */
    @Override
    public String getUser() {
        return user;
    }

    /**
     * gets choose1
     * @return first choice of the client
     */
    @Override
    public int getChoose1() {
        return choose1;
    }

    /**
     * gets choose2
     * @return second choice of the client
     */
    @Override
    public int getChoose2() {
        return choose2;
    }

    /**
     * gets if the game is ended or not
     * @return true if game is ended, false otherwise
     */
    @Override
    public boolean getEndGame() {
        return endGame;
    }

    /**
     * gets the list of inputs of the client
     * @return an arraylist of client's inputs
     */
    @Override
    public ArrayList<Integer> getChoices(){
        return choices;
    }

    /**
     * sets choose1
     * @param choose1 the int to be set
     */
    private void setChoose1(int choose1){
        this.choose1 = choose1;
    }

    /**
     * sets choose2
     * @param choose2 the int to be set
     */
    private void setChoose2(int choose2){
        this.choose2 = choose2;
    }

    /**
     * chooses the next state according to the actual one
     * @throws RemoteException if the reference could not be accessed
     */
    private void run() throws RemoteException {

        state = gameModel.getState();

        switch (state){
            case LOBBY:
                System.out.println("THE GAME IS STARTING...");
                break;
            case ENDROUND:
                viewEndRound();
                break;
            case ENDMATCH:
                viewEndMatch();
                break;
            case SELECTWINDOW:
                viewSelectWindow();
                break;
            case SELECTMOVE1:
                viewSelectMove1();
                break;
            case SELECTMOVE2:
                viewSelectMove2();
                break;
            case PUTDICEINWINDOW:
                viewPutDiceInWindow();
                break;
            case SELECTDRAFT:
                viewSelectDraft();
                break;
            case SELECTCARD:
                viewSelectCard();
                break;
            case USETOOLCARD:
                viewUseToolCard();
                break;
            case USETOOLCARD2:
                viewUseToolCard2();
                break;
            case USETOOLCARD3:
                viewUseToolCard3();
                break;
            case ERROR:
                viewError();
                break;
            default:
                assert false;
        }
    }

    /**
     * prints a message for each player to notify them the end of a round
     * @throws RemoteException if the reference could not be accessed
     */
    private void viewEndRound() throws RemoteException{
        System.out.println("\n\nEND OF ROUND " + gameModel.getField().getRoundTrack().getRound() +"\n\n");
        if(user.equals(gameModel.getActualPlayer().getUsername())){
            network.update(this);
        }
    }

    /**
     * prints the final score for each player
     * @throws RemoteException if the reference could not be accessed
     */
    private void viewEndMatch() throws RemoteException {
        int myscore = gameModel.getPlayers().get(0).getFinalScore();
        int rtscore = gameModel.getField().getRoundTrack().calculateRoundTrack();
        System.out.println("YOUR FINAL SCORE IS: " + myscore);
        System.out.println("THE TARGET SCORE IS: " + rtscore);
        if (myscore > rtscore)
            System.out.println("\nYOU WON!!!");
        else if (myscore == rtscore)
            System.out.println("\nIT'S A DRAW !!");
        else
            System.out.println("\nYOU LOST...    :'(");
    }

    /**
     * prints the 2 schemecards (4 window)
     * @throws RemoteException if the reference could not be accessed
     */
    private void viewSelectWindow() throws RemoteException {
        System.out.println("SELECT YOUR WINDOW!");
        PrintSchemeCard.print(gameModel.getSchemeCards().get(0), gameModel.getSchemeCards().get(1));
        input = new Scanner(System.in);
        while(!input.hasNextInt())
            input = new Scanner(System.in);
        setChoose1(input.nextInt());
        network.update(this);
    }

    /**
     * prints client's input possible choices
     * @throws RemoteException if the reference could not be accessed
     */
    private void viewSelectMove1() throws RemoteException {
        int tmp = ShowGameStuff.print((GameModel) gameModel, true);
        while (tmp != 0) {
            tmp = ShowGameStuff.print((GameModel) gameModel, true);
        }
        PrintSelectMove1.print();
        input = new Scanner(System.in);
        while(!input.hasNextInt())
            input = new Scanner(System.in);
        setChoose1(input.nextInt());
        network.update(this);
    }

    /**
     * prints client's input possible choices
     * @throws RemoteException if the reference could not be accessed
     */
    private void viewSelectMove2() throws RemoteException {
        int tmp = ShowGameStuff.print((GameModel) gameModel, true);
        while (tmp != 0) {
            tmp = ShowGameStuff.print((GameModel) gameModel, true);
        }
        PrintSelectMove2.print();
        input = new Scanner(System.in);
        while(!input.hasNextInt())
            input = new Scanner(System.in);
        setChoose1(input.nextInt());
        network.update(this);
    }

    /**
     * prints the player's window and asks him the i,j position to insert it
     * @throws RemoteException if the reference could not be accessed
     */
    private void viewPutDiceInWindow() throws RemoteException {
        PrintWindow.print(gameModel.getActualPlayer().getWindow());
        System.out.println("CHOOSE A ROW TO PUT YOUR DICE (-1 TO ABORT)");
        input = new Scanner(System.in);
        while(!input.hasNextInt())
            input = new Scanner(System.in);
        setChoose1(input.nextInt());
        if (choose1 != -1) {
            System.out.println("CHOOSE A COLUMN TO PUT YOUR DICE (-1 TO ABORT)");
            input = new Scanner(System.in);
            while (!input.hasNextInt())
                input = new Scanner(System.in);
            setChoose2(input.nextInt());
        }
        network.update(this);
    }

    /**
     * prints the list of dice in the draft
     * @throws RemoteException if the reference could not be accessed
     */
    private void viewSelectDraft() throws RemoteException {
        System.out.println("SELECT A DICE (-1 TO ABORT)");
        PrintDraft.print(gameModel.getField().getDraft());
        input = new Scanner(System.in);
        while(!input.hasNextInt())
            input = new Scanner(System.in);
        setChoose1(input.nextInt());
            network.update(this);
    }

    /**
     * prints toolcards available
     * @throws RemoteException if the reference could not be accessed
     */
    private void viewSelectCard() throws RemoteException {
        System.out.println("SELECT A TOOLCARD (-1 TO ABORT)");
        PrintToolCard.print(gameModel.getField().getToolCards());
        input = new Scanner(System.in);
        while(!input.hasNextInt())
            input = new Scanner(System.in);
        setChoose1(input.nextInt());
            network.update(this);
    }

    /**
     * prints selection menu for toolcards
     * @throws RemoteException  if the reference could not be accessed
     */
    private void viewUseToolCard() throws RemoteException {
        PrintUseToolCard.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices);
        network.update(this);

    }

    /**
     * prints selection menu for toolcards
     * @throws RemoteException if the reference could not be accessed
     */
    private void viewUseToolCard2() throws RemoteException {
        PrintUseToolCard2.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices);
        network.update(this);

    }

    /**
     * prints selection menu for toolcards
     * @throws RemoteException if the reference could not be accessed
     */
    private void viewUseToolCard3() throws RemoteException {
        PrintUseToolCard3.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices);
        network.update(this);
    }

    /**
     * print error message
     * @throws RemoteException if the reference could not be accessed
     */
    private void viewError() throws RemoteException {
        System.out.println("PLEASE DO IT AGAIN CORRECTLY!");
        network.update(this);
    }

    @Override
    public void print(String s){
        System.out.println(s);
    }

    /**
     * prints an error message
     * @param error the error message to be printed
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void printError(String error) throws RemoteException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            System.out.println(error);
        }
    }

    /**
     *
     * @param gameModel the gamemodel of the match
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void update(RemoteGameModel gameModel) throws RemoteException {
        this.gameModel = gameModel;
        this.run();
    }
}