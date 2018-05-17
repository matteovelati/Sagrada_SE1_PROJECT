package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.RemoteGameController;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.States;
import it.polimi.ingsw.view.View;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ViewCLI extends SubjectView implements ViewObserver, RemoteView, Serializable {

    private States oldState;
    private States state;
    private String user;
    private transient Scanner input;
    private boolean endGame;
    private int choose1;
    private int choose2;

    private GameModel gameModel;

    private RemoteGameController network;


    public ViewCLI(RemoteGameController network) throws RemoteException {
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

    public void setChoose1(int choose1){
        this.choose1 = choose1;
    }

    public void setChoose2(int choose2){
        this.choose2 = choose2;
    }

    public void run() throws InterruptedException, RemoteException {

            while(!endGame) {
                //gameModel = network.getGameModel();
                //System.out.println(state);
                if (!oldState.equals(gameModel.getState())) {
                    int tmp;
                    if (gameModel.getState().equals(States.ERROR)) {
                        System.out.println("SELECTION ERROR. PLEASE DO IT AGAIN CORRECTLY");
                    } else {
                        state = gameModel.getState();
                        oldState = state;
                    }

                    switch (state) {
                        case LOBBY:
                            System.out.println("WAIT OTHER PLAYERS JOIN THE GAME!");
                            break;
                        case SELECTWINDOW:
                            System.out.println("SELECT YOUR WINDOW!");
                            PrintSchemeCard.print(gameModel.getSchemeCards());
                            input = new Scanner(System.in);
                            setChoose1(input.nextInt());
                            network.update(this);
                            break;
                        case SELECTMOVE1:
                            tmp = ShowGameStuff.print(gameModel);
                            while (tmp != 0) {
                                tmp = ShowGameStuff.print(gameModel);
                            }
                            PrintSelectMove1.print();
                            input = new Scanner(System.in);
                            setChoose1(input.nextInt());
                            network.update(this);
                            break;
                        case SELECTMOVE2:
                            tmp = ShowGameStuff.print(gameModel);
                            while (tmp != 0) {
                                tmp = ShowGameStuff.print(gameModel);
                            }
                            PrintSelectMove2.print();
                            input = new Scanner(System.in);
                            setChoose1(input.nextInt());
                            network.update(this);
                            break;
                        case PUTDICEINWINDOW:
                            PrintWindow.print(gameModel.getActualPlayer().getWindow());
                            System.out.println("CHOOSE A ROW TO PUT YOUR DICE");
                            input = new Scanner(System.in);
                            setChoose1(input.nextInt());
                            System.out.println("CHOOSE A COLUMN TO PUT YOUR DICE");
                            input = new Scanner(System.in);
                            setChoose2(input.nextInt());
                            network.update(this);
                            break;
                        case SELECTDRAFT:
                            tmp = ShowGameStuff.print(gameModel);
                            while (tmp != 0) {
                                tmp = ShowGameStuff.print(gameModel);
                            }
                            System.out.println("SELECT A DICE");
                            PrintDraft.print(gameModel.getField().getDraft());
                            input = new Scanner(System.in);
                            setChoose1(input.nextInt());
                            network.update(this);
                            break;
                        case SELECTCARD:
                            tmp = ShowGameStuff.print(gameModel);
                            while (tmp != 0) {
                                tmp = ShowGameStuff.print(gameModel);
                            }
                            System.out.println("SELECT A TOOLCARD");
                            PrintToolCard.print(gameModel.getField().getToolCards());
                            input = new Scanner(System.in);
                            setChoose1(input.nextInt());
                            network.update(this);
                            break;
                        case USETOOLCARD:
                            //Da decidere assieme come farlo
                            network.update(this);
                            break;
                        case ENDROUND:
                            System.out.println("END OF ROUND " + gameModel.getField().getRoundTrack().getRound());
                            network.update(this);
                            break;
                        case SCORECALCULATION:
                            network.update(this);
                            break;
                        case ERROR:
                            network.update(this);
                            break;
                        default:
                            System.out.println("ERRORE INVIO VIEW");
                            break;
                    }
                }
            }
        }


    @Override
    public void update(GameModel gameModel) throws RemoteException {
        this.gameModel = network.getGameModel();
    }


}
