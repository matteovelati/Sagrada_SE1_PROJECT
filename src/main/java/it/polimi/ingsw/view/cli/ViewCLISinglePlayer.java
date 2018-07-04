package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.RemoteGameModel;
import it.polimi.ingsw.model.States;

import java.io.*;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ViewCLISinglePlayer extends ViewCLI implements Serializable {


    /**
     * creates a ViewCLISinglePlayer object checking if the username is correct and if the game is already started
     * initializes an arraylist of integer which will contains client's inputs
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    public ViewCLISinglePlayer() throws IOException{
        super();

        System.out.println("WELCOME TO SAGRADA! \n\n\n");
        System.out.println(" When playing Sagrada by yourself, you're trying to beat a Target Score. " +
        "The Target Score is the sum of the values from all the dice on the RoundTrack at the end of the game. \n");
        restart = false;
        endGame = false;
        if (network.getSinglePlayerStarted()){
            gameModel = network.getGameModel();
            if(gameModel.getState().equals(States.LOBBY)){
                if(socketConnection) {
                    setUser();
                    ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
                    obj.writeObject(this);
                    try {
                        updateSocket();
                    } catch (ClassNotFoundException e) {
                        //do nothing
                    }
                }
            }
            else {
                if (gameModel.getPlayers().get(0).getOnline()){
                    System.out.println("OPS! THE GAME IS ALREADY STARTED!\n\nCOME BACK LATER!");
                    System.exit(0);
                }
                else {
                    do {
                        setUser();
                    } while (!verifyUserCrashed(user));
                    if (socketConnection) {
                        ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
                        obj.writeObject(user);
                        try {
                            updateSocket();
                        } catch (ClassNotFoundException e) {
                            //do nothing
                        }
                    } else {
                        network.reAddObserver(this);
                        network.setPlayerOnline(user, true);
                        System.out.println("\n\nJOINING AGAIN THE MATCH...");
                        network.update(this);
                    }
                }
            }
        }
        else if (!network.getMultiPlayerStarted()){
            input = new Scanner(System.in);
            System.out.println("ENTER YOUR USERNAME:");
            this.user = input.next().toUpperCase();
            System.out.println("CHOOSE A LEVEL OF DIFFICULTY FROM 1 (BEGINNER) TO 5 (EXTREME)");
            input = new Scanner(System.in);
            do {
                while (!input.hasNextInt())
                    input = new Scanner(System.in);
                level = input.nextInt();
            } while (level < 1 || level > 5);
            network.createGameModel(level);
            gameModel = network.getGameModel();
            network.addObserver(this);
            network.startTimerSP(this);
            network.update(this);
        }
        else {
            System.out.println("OPS! THE GAME IS ALREADY STARTED!\n\nCOME BACK LATER!");
            System.exit(0);
        }
    }

    /**
     * verifies if some client has lost connection to the main server
     * @param s the name of the client to be verified
     * @return true if the client has lost connection, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    private boolean verifyUserCrashed(String s) throws RemoteException {
        for(Player x : gameModel.getPlayers()){
            if(x.getUsername().equals(s)){
                return !x.getOnline();
            }
        }
        return false;
    }

    /**
     * modifies the view based on the current state
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void runSP() throws IOException {

        state = gameModel.getState();

        switch (state){
            case RESTART:
                if (restart)
                    new ViewCLISinglePlayer();
                else
                    System.exit(0);
                break;
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
                viewSelectDraft(false);
                break;
            case SELECTCARD:
                viewSelectCard();
                break;
            case SELECTDIE:
                viewSelectDraft(true);
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
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewEndRound() throws IOException{
        System.out.println("\n\nEND OF ROUND " + gameModel.getField().getRoundTrack().getRound() +"\n\n");
        notifyNetwork();
    }

    /**
     * prints the final score for each player
     * asks to start another match
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewEndMatch() throws IOException {
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
        System.out.println("\n\nDO YOU WANT TO PLAY AGAIN ?\n[0] NO\n[1] YES");
        while (true) {
            input = new Scanner(System.in);
            while (!input.hasNextInt())
                input = new Scanner(System.in);
            int tmp = input.nextInt();
            if (tmp == 1) {
                restart = true;
                break;
            }
            else if (tmp == 0) {
                restart = false;
                break;
            }
        }
        notifyNetwork();

    }

    /**
     * prints the 2 schemecards (4 window)
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewSelectWindow() throws IOException {
        System.out.println("SELECT YOUR WINDOW!");
        PrintSchemeCard.print(gameModel.getSchemeCards().get(0), gameModel.getSchemeCards().get(1));
        input = new Scanner(System.in);
        while(!input.hasNextInt())
            input = new Scanner(System.in);
        setChoose1(input.nextInt());
        notifyNetwork();
    }

    /**
     * prints client's input possible choices
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewSelectMove1() throws IOException {
        int tmp = ShowGameStuff.print((GameModel) gameModel, true);
        while (tmp != 0) {
            tmp = ShowGameStuff.print((GameModel) gameModel, true);
        }
        PrintSelectMove1.print();
        input = new Scanner(System.in);
        while(!input.hasNextInt())
            input = new Scanner(System.in);
        setChoose1(input.nextInt());
        notifyNetwork();
    }

    /**
     * prints client's input possible choices
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewSelectMove2() throws IOException {
        int tmp = ShowGameStuff.print((GameModel) gameModel, true);
        while (tmp != 0) {
            tmp = ShowGameStuff.print((GameModel) gameModel, true);
        }
        PrintSelectMove2.print();
        input = new Scanner(System.in);
        while(!input.hasNextInt())
            input = new Scanner(System.in);
        setChoose1(input.nextInt());
        notifyNetwork();
    }

    /**
     * prints the player's window and asks him the i,j position to insert it
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewPutDiceInWindow() throws IOException {
        PrintWindow.print(gameModel.getActualPlayer().getWindow());
        System.out.println("CHOOSE A ROW TO PUT YOUR DIE (-1 TO ABORT)");
        input = new Scanner(System.in);
        while(!input.hasNextInt())
            input = new Scanner(System.in);
        setChoose1(input.nextInt());
        if (choose1 != -1) {
            System.out.println("CHOOSE A COLUMN TO PUT THE DIE (-1 TO ABORT)");
            input = new Scanner(System.in);
            while (!input.hasNextInt())
                input = new Scanner(System.in);
            setChoose2(input.nextInt());
        }
        notifyNetwork();
    }

    /**
     * prints the list of dice in the draft
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewSelectDraft(boolean toolCard) throws IOException {
        if (toolCard)
            System.out.println("SELECT A DIE THAT MATCHES THE COLOR OF THE TOOLCARD, IT WILL BE REMOVED FROM GAME (-1 TO ABORT)");
        else
            System.out.println("SELECT A DIE (-1 TO ABORT)");
        PrintDraft.print(gameModel.getField().getDraft());
        input = new Scanner(System.in);
        while(!input.hasNextInt())
            input = new Scanner(System.in);
        setChoose1(input.nextInt());
        notifyNetwork();
    }

    /**
     * prints toolcards available
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewSelectCard() throws IOException {
        System.out.println("SELECT A TOOLCARD (-1 TO ABORT)");
        PrintToolCard.print(gameModel.getField().getToolCards());
        input = new Scanner(System.in);
        while(!input.hasNextInt())
            input = new Scanner(System.in);
        setChoose1(input.nextInt());
        notifyNetwork();
    }

    /**
     * prints selection menu for toolcards
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewUseToolCard() throws IOException {
        PrintUseToolCard.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices, this);
        notifyNetwork();
    }

    /**
     * prints selection menu for toolcards
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewUseToolCard2() throws IOException {
        PrintUseToolCard2.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices, this);
        notifyNetwork();
    }

    /**
     * prints selection menu for toolcards
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewUseToolCard3() throws IOException {
        PrintUseToolCard3.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices, this);
        notifyNetwork();
    }

    /**
     * print error message
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewError() throws IOException {
        System.out.println("PLEASE DO IT AGAIN CORRECTLY!");
        notifyNetwork();
    }

    /**
     * modifies the view based on the current state
     * @param gameModel the gamemodel of the match
     */
    @Override
    public void update(RemoteGameModel gameModel) {
        this.gameModel = gameModel;
        try {
            this.runSP();
        } catch (IOException e) {
            System.out.println(SHUTDOWN);
            System.exit(0);
        }
    }

    /**
     * gets if has started a singleplayer match
     * @return always true
     */
    @Override
    public boolean getSinglePlayer(){
        return true;
    }

    /**
     * based on the type of connection, it calls an update to the Server
     * @throws IOException if an I/O error occurs while reading stream header
     */
    private void notifyNetwork() throws IOException {
        if (socketConnection){
            ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
            obj.writeObject(this);
        }
        else
            network.update(this);
    }

    /**
     * modifies the view based on the current state
     * check if the server has been shut down
     * @throws IOException if an I/O error occurs while reading stream header
     * @throws ClassNotFoundException if class of a serialized object cannot be found
     */
    public void updateSocket() throws IOException, ClassNotFoundException {
        while(!endGame) {
                try {
                    ObjectInputStream ob = new ObjectInputStream(socket.getInputStream());
                    this.gameModel = (RemoteGameModel) ob.readObject();
                    if (this.gameModel.getUpdateSocket()) {
                        new Thread(() -> {
                            try {
                                runSP();
                            } catch (IOException e) {
                                //do nothing
                            }
                        }).start();
                    }
                }
                catch (StreamCorruptedException e1) {
                    System.out.println("OPS! AN ERROR OCCURRED. PLEASE RESTART THE CLIENT");
                    System.exit(0);
                }
                catch (SocketException e){
                    System.out.println(SHUTDOWN);
                    System.exit(0);
                }
        }
    }
}