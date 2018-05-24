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
        choices = new ArrayList<>();
        endGame = false;
        input = new Scanner(System.in);
        System.out.println("ENTER YOUR USERNAME:");
        user = input.next();
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

    private void setChoose1(int choose1){
        this.choose1 = choose1;
    }

    private void setChoose2(int choose2){
        this.choose2 = choose2;
    }

    private void run() throws RemoteException {
        state = gameModel.getState();

        if(state.equals(States.LOBBY)){
            System.out.println("WAIT OTHER PLAYERS JOIN THE GAME");
            return;
        }
        if(state.equals(States.ENDROUND)){
            System.out.println("END OF ROUND " + gameModel.getField().getRoundTrack().getRound());
            if(user.equals(gameModel.getActualPlayer().getUsername())){
                network.update(this);
            }
            return;
        }
        if(state.equals(States.ENDMATCH)){
            for(Player x : gameModel.getPlayers()){
                System.out.println(x.getUsername() +"'s FINAL SCORE: "+ x.getFinalScore());
            }
            return;
        }
        if(user.equals(gameModel.getActualPlayer().getUsername())){
            int tmp;
            if(state.equals(States.SELECTWINDOW)){
                System.out.println("SELECT YOUR WINDOW!");
                PrintSchemeCard.print(gameModel.getSchemeCards().get(0), gameModel.getSchemeCards().get(1));
                input = new Scanner(System.in);
                setChoose1(input.nextInt());
                network.update(this);
                return;
            }
            if(state.equals(States.SELECTMOVE1)){
                tmp = ShowGameStuff.print((GameModel) gameModel);
                while (tmp != 0) {
                    tmp = ShowGameStuff.print((GameModel) gameModel);
                }
                PrintSelectMove1.print();
                input = new Scanner(System.in);
                setChoose1(input.nextInt());
                network.update(this);
            }
            if(state.equals(States.SELECTMOVE2)){
                tmp = ShowGameStuff.print((GameModel) gameModel);
                while (tmp != 0) {
                    tmp = ShowGameStuff.print((GameModel) gameModel);
                }
                PrintSelectMove2.print();
                input = new Scanner(System.in);
                setChoose1(input.nextInt());
                network.update(this);
                return;
            }
            if(state.equals(States.PUTDICEINWINDOW)){
                PrintWindow.print(gameModel.getActualPlayer().getWindow());
                System.out.println("CHOOSE A ROW TO PUT YOUR DICE");
                input = new Scanner(System.in);
                setChoose1(input.nextInt());
                System.out.println("CHOOSE A COLUMN TO PUT YOUR DICE");
                input = new Scanner(System.in);
                setChoose2(input.nextInt());
                network.update(this);
                return;
            }
            if(state.equals(States.SELECTDRAFT)){
                System.out.println("SELECT A DICE");
                PrintDraft.print(gameModel.getField().getDraft());
                input = new Scanner(System.in);
                setChoose1(input.nextInt());
                network.update(this);
                return;
            }
            if(state.equals(States.SELECTCARD)){
                System.out.println("SELECT A TOOLCARD (-1 TO ABORT)");
                PrintToolCard.print(gameModel.getField().getToolCards());
                input = new Scanner(System.in);
                setChoose1(input.nextInt());
                network.update(this);
                return;
            }
            if(state.equals(States.USETOOLCARD)){
                PrintUseToolCard.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices);
                network.update(this);
                return;
            }
            if(state.equals(States.USETOOLCARD2)){
                PrintUseToolCard2.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices);
                network.update(this);
                return;
            }
            if(state.equals(States.USETOOLCARD3)){
                PrintUseToolCard3.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices);
                network.update(this);
                return;
            }
            if(state.equals(States.ERROR)){
                System.out.println("SELECTION ERROR. PLEASE DO IT AGAIN CORRECTLY!");
                network.update(this);
                return;
            }
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
