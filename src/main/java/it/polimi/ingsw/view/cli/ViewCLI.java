package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.RemoteGameController;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.RemoteGameModel;
import it.polimi.ingsw.model.States;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewCLI extends UnicastRemoteObject implements ViewObserver, RemoteView, Serializable {

    private States oldState;
    private States state;
    private String user;
    private transient Scanner input;
    private boolean endGame;
    private int choose1;
    private int choose2;
    private ArrayList<Integer> choices;

    private RemoteGameModel gameModel;

    private RemoteGameController network;


    public ViewCLI(RemoteGameController network) throws RemoteException {
        choices = new ArrayList<Integer>();
        endGame = false;
        input = new Scanner(System.in);
        System.out.println("ENTER YOUR USERNAME:");
        user = input.next();
        oldState = States.BEFORE;
        this.network = network;
        gameModel = network.getGameModel();
        network.addObserver(this);
        network.update(this);

    }

    @Override
    public String getUser() throws RemoteException {
        return user;
    }

    @Override
    public int getChoose1() throws RemoteException {
        return choose1;
    }

    @Override
    public int getChoose2() throws RemoteException {
        return choose2;
    }

    @Override
    public boolean getEndGame() throws RemoteException {
        return endGame;
    }

    @Override
    public ArrayList<Integer> getChoices() throws RemoteException{
        return choices;
    }

    public void setChoose1(int choose1){
        this.choose1 = choose1;
    }

    public void setChoose2(int choose2){
        this.choose2 = choose2;
    }

    public void run() throws RemoteException {

        int tmp;
        state = gameModel.getState();
            switch (state) {
                case LOBBY:
                    if(!state.equals(oldState)) {
                        System.out.println("WAIT OTHER PLAYERS JOIN THE GAME!");
                        oldState = state;
                    }
                    break;
                case SELECTWINDOW:
                    if(user.equals(gameModel.getActualPlayer().getUsername())) {
                        System.out.println("SELECT YOUR WINDOW!");
                        PrintSchemeCard.print(gameModel.getSchemeCards().get(0), gameModel.getSchemeCards().get(1));
                        input = new Scanner(System.in);
                        setChoose1(input.nextInt());
                        oldState = state;
                        network.update(this);
                    }
                    break;
                case SELECTMOVE1:
                    if(user.equals(gameModel.getActualPlayer().getUsername())) {
                        tmp = ShowGameStuff.print((GameModel) gameModel);
                        while (tmp != 0) {
                            tmp = ShowGameStuff.print((GameModel) gameModel);
                        }
                        PrintSelectMove1.print();
                        input = new Scanner(System.in);
                        setChoose1(input.nextInt());
                        network.update(this);
                        oldState = state;
                    }
                    break;
                case SELECTMOVE2:
                    if(user.equals(gameModel.getActualPlayer().getUsername())) {
                        tmp = ShowGameStuff.print((GameModel) gameModel);
                        while (tmp != 0) {
                            tmp = ShowGameStuff.print((GameModel) gameModel);
                        }
                        PrintSelectMove2.print();
                        input = new Scanner(System.in);
                        setChoose1(input.nextInt());
                        network.update(this);
                        oldState = state;
                    }
                    break;
                case PUTDICEINWINDOW:
                    if(user.equals(gameModel.getActualPlayer().getUsername())) {
                        PrintWindow.print(gameModel.getActualPlayer().getWindow());
                        System.out.println("CHOOSE A ROW TO PUT YOUR DICE");
                        input = new Scanner(System.in);
                        setChoose1(input.nextInt());
                        System.out.println("CHOOSE A COLUMN TO PUT YOUR DICE");
                        input = new Scanner(System.in);
                        setChoose2(input.nextInt());
                        network.update(this);
                        oldState = state;
                    }
                    break;
                case SELECTDRAFT:
                    if(user.equals(gameModel.getActualPlayer().getUsername())) {
                        System.out.println("SELECT A DICE");
                        PrintDraft.print(gameModel.getField().getDraft());
                        input = new Scanner(System.in);
                        setChoose1(input.nextInt());
                        network.update(this);
                        oldState = state;
                    }
                    break;
                case SELECTCARD:
                    if(user.equals(gameModel.getActualPlayer().getUsername())) {
                        System.out.println("SELECT A TOOLCARD");
                        PrintToolCard.print(gameModel.getField().getToolCards());
                        input = new Scanner(System.in);
                        setChoose1(input.nextInt());
                        network.update(this);
                        oldState = state;
                    }
                    break;
                case USETOOLCARD:
                    if(user.equals(gameModel.getActualPlayer().getUsername())) {
                        PrintUseToolCard.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices);
                        network.update(this);
                    }
                    break;
                case USETOOLCARD2:
                    if(user.equals(gameModel.getActualPlayer().getUsername())) {
                        PrintUseToolCard2.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices);
                        network.update(this);
                    }
                    break;
                case USETOOLCARD3:
                    if(user.equals(gameModel.getActualPlayer().getUsername())) {
                        PrintUseToolCard3.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices);
                        network.update(this);
                    }
                    break;
                case ENDROUND:
                    System.out.println("END OF ROUND " + gameModel.getField().getRoundTrack().getRound());
                    network.update(this);
                    oldState = state;
                    break;
                case ENDMATCH:
                    for(Player x : gameModel.getPlayers()){
                        System.out.println(x.getUsername() +"'s FINAL SCORE: "+ x.getFinalScore());
                    }
                    break;
                case ERROR:
                    if(user.equals(gameModel.getActualPlayer().getUsername())) {
                        System.out.println("SELECTION ERROR. PLEASE DO IT AGAIN CORRECTLY!");
                        network.update(this);
                        oldState = state;
                    }
                    break;
                default:
                    System.out.println("ERRORE INVIO VIEW");
                    break;
            }
        }


   @Override
   public void print(String s){
       System.out.println(s);
   }

    @Override
    public void update(RemoteGameModel gameModel) throws RemoteException {
        this.gameModel = gameModel;
        this.run();
    }


}
