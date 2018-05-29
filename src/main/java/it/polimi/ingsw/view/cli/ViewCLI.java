package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.RemoteGameController;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.RemoteGameModel;
import it.polimi.ingsw.model.States;
import it.polimi.ingsw.view.RemoteView;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewCLI extends UnicastRemoteObject implements RemoteView, Serializable {

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
        System.out.println("WELCOME TO SAGRADA! \n\n\n");
        choices = new ArrayList<>();
        endGame = false;
        this.network = network;
        gameModel = network.getGameModel();
        if(gameModel.getState().equals(States.LOBBY)) {
            network.addObserver(this);
            do {
                setUser();
            } while (!verifyUser(user));
            network.update(this);
        }
        else{
            System.out.println("OPS! THE GAME IS ALREADY STARTED!\n\nCOME BACK LATER!");
            System.exit(0);
        }
    }


    private void setUser() {
        input = new Scanner(System.in);
        System.out.println("ENTER YOUR USERNAME:");
        this.user = input.next().toUpperCase();
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public int getChoose1() {
        return choose1;
    }

    @Override
    public int getChoose2() {
        return choose2;
    }

    @Override
    public boolean getEndGame() {
        return endGame;
    }

    @Override
    public ArrayList<Integer> getChoices(){
        return choices;
    }

    private void setChoose1(int choose1){
        this.choose1 = choose1;
    }

    private void setChoose2(int choose2){
        this.choose2 = choose2;
    }

    private void run() throws RemoteException {
        state = gameModel.getState();

        if(state.equals(States.LOBBY)){
            viewLobby();
            return;
        }
        if(state.equals(States.ENDROUND)){
            viewEndRound();
            return;
        }
        if(state.equals(States.ENDMATCH)){
            viewEndMatch();
            return;
        }
        if(state.equals(States.SELECTWINDOW)){
            viewSelectWindow();
            return;
        }
        if(state.equals(States.SELECTMOVE1)){
            viewSelectMove1();
            return;
        }
        if(state.equals(States.SELECTMOVE2)){
            viewSelectMove2();
            return;
        }
        if(state.equals(States.PUTDICEINWINDOW)){
            viewPutDiceInWindow();
            return;
        }
        if(state.equals(States.SELECTDRAFT)){
            viewSelectDraft();
            return;
        }
        if(state.equals(States.SELECTCARD)){
            viewSelectCard();
            return;
        }
        if(state.equals(States.USETOOLCARD)){
            viewUseToolCard();
            return;
        }
        if(state.equals(States.USETOOLCARD2)){
            viewUseToolCard2();
            return;
        }
        if(state.equals(States.USETOOLCARD3)){
            viewUseToolCard3();
            return;
        }
        if(state.equals(States.ERROR)){
            viewError();
            return;
        }
    }

    private boolean verifyUser(String s) throws RemoteException{
        for(int i=0; i<gameModel.getPlayers().size(); i++){
            if(s.equals(gameModel.getPlayers().get(i).getUsername())){
                System.out.println("THIS USERNAME ALREADY EXISTS");
                return false;
            }
        }
        return true;
    }

    private void viewLobby() throws RemoteException {
        System.out.println("GAMERS IN THE LOBBY:");
        for(Player x: gameModel.getPlayers()){
            System.out.println("- "+ x.getUsername());
        }
    }

    private void viewEndRound() throws RemoteException {
        System.out.println("END OF ROUND " + gameModel.getField().getRoundTrack().getRound());
        if(user.equals(gameModel.getActualPlayer().getUsername())){
            network.update(this);
        }
    }

    private void viewEndMatch() throws RemoteException {
        for(Player x : gameModel.getPlayers()){
            System.out.println(x.getUsername() +"'s FINAL SCORE: "+ x.getFinalScore());
        }
    }

    private void viewSelectWindow() throws RemoteException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            System.out.println("SELECT YOUR WINDOW!");
            PrintSchemeCard.print(gameModel.getSchemeCards().get(0), gameModel.getSchemeCards().get(1));
            input = new Scanner(System.in);
            setChoose1(input.nextInt());
            network.update(this);
        }
    }

    private void viewSelectMove1() throws RemoteException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            int tmp = ShowGameStuff.print((GameModel) gameModel);
            while (tmp != 0) {
                tmp = ShowGameStuff.print((GameModel) gameModel);
            }
            PrintSelectMove1.print();
            input = new Scanner(System.in);
            setChoose1(input.nextInt());
            network.update(this);
        }
    }

    private void viewSelectMove2() throws RemoteException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            int tmp = ShowGameStuff.print((GameModel) gameModel);
            while (tmp != 0) {
                tmp = ShowGameStuff.print((GameModel) gameModel);
            }
            PrintSelectMove2.print();
            input = new Scanner(System.in);
            setChoose1(input.nextInt());
            network.update(this);
        }
    }

    private void viewPutDiceInWindow() throws RemoteException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            PrintWindow.print(gameModel.getActualPlayer().getWindow());
            System.out.println("CHOOSE A ROW TO PUT YOUR DICE (-1 TO ABORT)");
            input = new Scanner(System.in);
            setChoose1(input.nextInt());
            if (choose1 != -1) {
                System.out.println("CHOOSE A COLUMN TO PUT YOUR DICE (-1 TO ABORT)");
                input = new Scanner(System.in);
                setChoose2(input.nextInt());
            }
            network.update(this);
        }
    }

    private void viewSelectDraft() throws RemoteException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            System.out.println("SELECT A DICE (-1 TO ABORT)");
            PrintDraft.print(gameModel.getField().getDraft());
            input = new Scanner(System.in);
            setChoose1(input.nextInt());
            network.update(this);
        }
    }

    private void viewSelectCard() throws RemoteException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            System.out.println("SELECT A TOOLCARD (-1 TO ABORT)");
            PrintToolCard.print(gameModel.getField().getToolCards());
            input = new Scanner(System.in);
            setChoose1(input.nextInt());
            network.update(this);
        }
    }

    private void viewUseToolCard() throws RemoteException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            PrintUseToolCard.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices);
            network.update(this);
        }
    }

    private void viewUseToolCard2() throws RemoteException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            PrintUseToolCard2.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices);
            network.update(this);
        }
    }

    private void viewUseToolCard3() throws RemoteException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            PrintUseToolCard3.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices);
            network.update(this);
        }
    }

    private void viewError() throws RemoteException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            System.out.println("PLEASE DO IT AGAIN CORRECTLY!");
            network.update(this);
        }
    }

   @Override
   public void print(String s){
       System.out.println(s);
   }

   @Override
   public void printError(String error) throws RemoteException {
       if(user.equals(gameModel.getActualPlayer().getUsername())) {
           System.out.println(error);
       }
   }

    @Override
    public void update(RemoteGameModel gameModel) throws RemoteException {
        this.gameModel = gameModel;
        this.run();
    }


}
