package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.RemoteGameController;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.RemoteGameModel;
import it.polimi.ingsw.model.States;
import it.polimi.ingsw.view.RemoteView;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
    private String ipAddress;
    private boolean socketConnection;
    private transient Socket socket;
    private boolean restart;
    private int level;

    /**
     * creates a ViewCLISinglePlayer object checking if the username is correct and if the game is already started
     * initializes an arraylist of integer which will contains client's inputs
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    public ViewCLISinglePlayer() throws IOException{
        choices = new ArrayList<>();
        choices.add(-1);
        connectionRequest();

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
                        network.updateSP(this);
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
            network.updateSP(this);
        }
        else {
            System.out.println("OPS! THE GAME IS ALREADY STARTED!\n\nCOME BACK LATER!");
            System.exit(0);
        }
    }

    /**
     * Request of connection type you want to use. You have to insert the Server ip address.
     * If you insert a wrong ip address you have to restart the client for a new connection
     * request.
     */
    private void connectionRequest() {
        int tmp;
        System.out.println("IP ADDRESS: ");
        input = new Scanner(System.in);
        ipAddress = input.next();

        System.out.println("WHICH CONNECTION DO YOU WANT TO USE?\n1) RMI\n2) SOCKET");
        while (true) {
            input = new Scanner(System.in);
            while (!input.hasNextInt()) {
                System.out.println("Please insert a number.");
                input = new Scanner(System.in);
            }
            tmp = input.nextInt();
            if (tmp == 1 || tmp == 2)
                break;
            else
                System.out.println("Please select 1 or 2");
        }

        if(tmp == 1){       //RMI CONNECTION
            socketConnection = false;
            socket = null;
            try {
                Registry registry = LocateRegistry.getRegistry(ipAddress);
                network = (RemoteGameController) registry.lookup("network");
            } catch (RemoteException e) {
                System.out.println("\n\nTHIS IP ADDRESS DOES NOT EXIST");
                System.exit(0);
            } catch (NotBoundException e){
                System.out.println("\n\nOPS... AN ERROR OCCURRED. PLEASE RESTART THE GAME.");
                System.exit(0);
            }
        }
        else{                           //SOCKET CONNECTION
            socketConnection = true;
            try {
                socket = new Socket(ipAddress, 1337);
                System.out.println("CHOOSE A LEVEL OF DIFFICULTY FROM 1 (BEGINNER) TO 5 (EXTREME)");
                input = new Scanner(System.in);
                do {
                    while (!input.hasNextInt())
                        input = new Scanner(System.in);
                    level = input.nextInt();
                } while (level < 1 || level > 5);
                ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
                obj.writeObject(this);
                ObjectInputStream ob = new ObjectInputStream(socket.getInputStream());
                network = (RemoteGameController) ob.readObject();
            } catch (IOException e) {
                System.out.println("\n\nTHIS IP ADDRESS DOES NOT EXIST");
                System.exit(0);
            } catch (ClassNotFoundException e) {
                //do nothing
            }
        }
        setOnline(true);
    }

    /**
     * asks the client to enter an username
     * finally transform it in upper case
     */
    private void setUser() {
        input = new Scanner(System.in);
        System.out.println("ENTER YOUR USERNAME:");
        this.user = input.next().toUpperCase();
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
                if(x.getOnline())
                    return false;
                else{
                    for(RemoteView y : gameModel.getObservers()){
                        if(y!=null && y.getUser().equals(s))
                            return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * gets the level of difficulty of the single player match
     * @return a int between 1 and 5 which is the level chosen
     */
    @Override
    public int getLevel(){
        return level;
    }

    /**
     * gets if it's needed to start the timer
     * @return always false in singleplayer mode
     */
    @Override
    public boolean getStartTimerSocket() {
        return false;
    }

    /**
     * gets if a player has to be set online
     * @return always false in singleplayer mode
     */
    @Override
    public boolean getReturnOnline(){
        return false;
    }

    /**
     * gets if the socket timeout connection needs be deleted or not
     * @return always false in singleplayer mode
     */
    @Override
    public boolean getDeleteConnectionSocket() {
        return false;
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
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if (socketConnection){
                ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
                obj.writeObject(this);
            }
            else
                network.updateSP(this);
        }
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
        if (socketConnection){
            ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
            obj.writeObject(this);
        }
        else
            network.updateSP(this);

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
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if (socketConnection){
                ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
                obj.writeObject(this);
            }
            else
                network.updateSP(this);
        }
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
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if (socketConnection){
                ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
                obj.writeObject(this);
            }
            else
                network.updateSP(this);
        }
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
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if (socketConnection){
                ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
                obj.writeObject(this);
            }
            else
                network.updateSP(this);
        }
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
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if (socketConnection){
                ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
                obj.writeObject(this);
            }
            else
                network.updateSP(this);
        }
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
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if (socketConnection){
                ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
                obj.writeObject(this);
            }
            else
                network.updateSP(this);
        }
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
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if (socketConnection){
                ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
                obj.writeObject(this);
            }
            else
                network.updateSP(this);
        }
    }

    /**
     * prints selection menu for toolcards
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewUseToolCard() throws IOException {
        PrintUseToolCard.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices, this);
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if (socketConnection){
                ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
                obj.writeObject(this);
            }
            else
                network.updateSP(this);
        }
    }

    /**
     * prints selection menu for toolcards
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewUseToolCard2() throws IOException {
        PrintUseToolCard2.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices, this);
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if (socketConnection){
                ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
                obj.writeObject(this);
            }
            else
                network.updateSP(this);
        }
    }

    /**
     * prints selection menu for toolcards
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewUseToolCard3() throws IOException {
        PrintUseToolCard3.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices, this);
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if (socketConnection){
                ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
                obj.writeObject(this);
            }
            else
                network.updateSP(this);
        }
    }

    /**
     * print error message
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewError() throws IOException {
        System.out.println("PLEASE DO IT AGAIN CORRECTLY!");
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if (socketConnection){
                ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
                obj.writeObject(this);
            }
            else
                network.updateSP(this);
        }
    }

    /**
     * print a message
     * @param s the message to be printed
     */
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
     * modifies the view based on the current state
     * @param gameModel the gamemodel of the match
     */
    @Override
    public void update(RemoteGameModel gameModel) {
        this.gameModel = gameModel;
        try {
            this.runSP();
        } catch (IOException e) {
            System.out.println("SEEMS LIKE THE SERVER HAS BEEN SHUT DOWN");
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
     * gets if the client is connected with socket
     * @return true if the client is connected with socket
     */
    @Override
    public boolean getSocketConnection(){
        return socketConnection;
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
                    System.out.println("SEEMS LIKE THE SERVER HAS BEEN SHUT DOWN");
                    System.exit(0);
                }
        }
    }
}