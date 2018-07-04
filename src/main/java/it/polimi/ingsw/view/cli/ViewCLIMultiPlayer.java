package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.RemoteGameController;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.RemoteGameModel;
import it.polimi.ingsw.model.States;
import it.polimi.ingsw.view.RemoteView;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ViewCLIMultiPlayer extends ViewCLI implements Serializable {


    /**
     * creates a ViewCLIMultiPlayer object checking if the username is correct and if the game is already started
     * initializes an arraylist of integer which will contains client's inputs
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    public ViewCLIMultiPlayer() throws IOException {

        super();
        if(!socketConnection)
            verifyServerConnection();
        singlePlayer = false;

        System.out.println("WELCOME TO SAGRADA! \n\n\n");
        if (network.getMultiPlayerStarted()) {
            gameModel = network.getGameModel();
            if (gameModel.getState().equals(States.LOBBY)) {
                do {
                    setUser();
                } while (!verifyUser(user));
                if(socketConnection){
                    ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
                    ob.writeObject(this);
                    try {
                        updateSocket();
                    } catch (ClassNotFoundException e) {
                        //
                    }
                }
                else {
                    network.addObserver(this);
                    network.update(this);
                }
            } else {
                if (!verifyReconnection()) {
                    System.out.println("OPS! THE GAME IS ALREADY STARTED!\n\nCOME BACK LATER!");
                    System.exit(0);
                } else {
                    do {
                        setUser();
                    } while (!verifyUserCrashed(user));
                    if(socketConnection){
                        ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
                        ob.writeObject(user);
                        System.out.println("\n\nJOINING AGAIN THE MATCH...");
                        try {
                            updateSocket();
                        } catch (ClassNotFoundException e) {
                            //
                        }
                    }
                    else {
                        network.reAddObserver(this);
                        network.setPlayerOnline(user, true);
                        System.out.println("\n\nJOINING AGAIN THE MATCH...");
                    }
                }
            }
        } else if (!network.getSinglePlayerStarted()) {
            if(!socketConnection) {
                network.createGameModel(0);
                gameModel = network.getGameModel();
                if (gameModel.getPlayers().isEmpty())
                    setUser();
                else {
                    do {
                        setUser();
                    } while (!verifyUser(user));
                }
                network.addObserver(this);
                network.update(this);
            }
        } else {
            System.out.println("OPS! THE GAME IS ALREADY STARTED!\n\nCOME BACK LATER!");
            System.exit(0);
        }
    }

    /**
     * Only for RMI clients.
     * Every 2 seconds verifies if the Server is up.
     * If not, shuts down the client.
     */
    private void verifyServerConnection(){
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    network.getMultiPlayerStarted();
                    verifyServerConnection();
                }catch (RemoteException e){
                    System.out.println(SHUTDOWN);
                    System.exit(0);
                }
            }
        },2000);
    }

    /**
     * verifies the possibility to join again the game already started
     * @return true if you can join again the match, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    private boolean verifyReconnection() throws RemoteException{
        for(int i=0; i<gameModel.getObservers().size(); i++){
            if((gameModel.getObservers() == null ||gameModel.getObservers().get(i) == null) &&
                    (gameModel.getObserverSocket() == null ||gameModel.getObserverSocket().get(i)==null))
                return true;
        }
        return false;
    }

    /**
     * if the timer expired, set the player offline
     */
    public void socketTimeOut(){
        new Thread(() -> {
            try {
                ObjectInputStream ob = new ObjectInputStream(socket.getInputStream());
                gameModel = (RemoteGameModel) ob.readObject();
                setDeleteConnectionSocket(false);
                for(Player x : gameModel.getPlayers()){
                    if(x.getUsername().equals(user) && !x.getOnline()) {
                        setOnline(false);
                    }
                }
            }catch (IOException e){
                //
            }catch (ClassNotFoundException e1){
                //
            }
        }).start();
    }

    /**
     * verifies if the client has entered a not valid username
     * @param s the name of the client to be verified
     * @return true if the username is valid, false otherwise
     * @throws RemoteException if the reference could not be accessed
     */
    private boolean verifyUser(String s) throws RemoteException{
        for(int i=0; i<gameModel.getPlayers().size(); i++){
            if(s.equals(gameModel.getPlayers().get(i).getUsername())){
                System.out.println("THIS USERNAME ALREADY EXISTS");
                return false;
            }
        }
        return true;
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
                    for(int i =0; i<gameModel.getObservers().size(); i++){
                        if((gameModel.getObservers()!=null && gameModel.getObservers().get(i)!=null && gameModel.getObservers().get(i).getUser().equals(s))
                                || (gameModel.getObserverSocket()!=null && gameModel.getObserverSocket().get(i)!=null && gameModel.getPlayers().get(i).getUsername().equals(s)))
                            return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * modifies the view based on the current state
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void run() throws IOException {
        returnOnline = false;
        state = gameModel.getState();
        switch (state){
            case RESTART:
                viewRestart();
                break;
            case LOBBY:
                viewLobby();
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
                printPlayersOnline();
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
     * prints the name of each player and if he's online or not
     * @throws RemoteException if the reference could not be accessed
     */
    private void printPlayersOnline() throws RemoteException{
        for (Player p: gameModel.getPlayers()){
            System.out.println(p.getUsername() + " IS "+ ((p.getOnline())?"ONLINE":"OFFLINE"));
        }
    }

    /**
     * prints the players in the lobby
     * @throws RemoteException if the reference could not be accessed
     */
    private void viewLobby() throws RemoteException {
        System.out.println("GAMERS IN THE LOBBY:");
        for(Player x: gameModel.getPlayers()){
            System.out.println("- "+ x.getUsername());
        }

    }

    /**
     * prints a message for each player to notify them the end of a round
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewEndRound() throws IOException {
        System.out.println("\n\nEND OF ROUND " + gameModel.getField().getRoundTrack().getRound() +"\n\n");
        if(user.equals(gameModel.getActualPlayer().getUsername())){
            if(socketConnection){
                ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
                ob.writeObject(this);
            }
            else
                network.update(this);
        }
    }

    /**
     * prints a message to ask each player if wants to play again saving the choice in a boolean "restart"
     */
    private void viewRestart(){
        if(getOnline()) {
            System.out.println("\n\nDO YOU WANT TO PLAY AGAIN ?\n[0] NO\n[1] YES");
            while (true) {
                input = new Scanner(System.in);
                while (!input.hasNextInt())
                    input = new Scanner(System.in);
                int tmp = input.nextInt();
                if (tmp == 1) {
                    restart = true;
                    break;
                } else if (tmp == 0) {
                    restart = false;
                    break;
                }
            }
            if (restart) {
                new Thread(() -> {
                    try {
                        new ViewCLIMultiPlayer();
                    } catch (IOException e) {
                        //do nothing
                    }
                }).start();
            }
        }else
            System.exit(0);
    }

    /**
     * prints the final score for each player
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewEndMatch() throws IOException {
        boolean win = true;
        int myScore = 0;
        for(Player x : gameModel.getPlayers()){
            if(user.equals(x.getUsername())){
                myScore = x.getFinalScore();
                System.out.println("YOUR FINAL SCORE IS: "+ myScore +"\n");
                break;
            }
        }
        for (Player x : gameModel.getPlayers()){
            if(!user.equals(x.getUsername()))
                System.out.println(x.getUsername() +"'S FINAL SCORE: "+ x.getFinalScore());
            if(myScore < x.getFinalScore())
                win = false;
        }
        if (win)
            System.out.println("\nYOU WON!!!");
        else
            System.out.println("\nYOU LOST...    :'(");
        int i =0;
        for(i =0; i<gameModel.getPlayers().size(); i++){
            if(gameModel.getPlayers().get(i).getOnline())
                break;
        }
        if (user.equals(gameModel.getPlayers().get(i).getUsername())){
            if (socketConnection){
                ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
                obj.writeObject(this);
            }
            else
                network.update(this);
        }
    }

    /**
     * prints the 2 schemecards (4 window)
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewSelectWindow() throws IOException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if(socketConnection){
                startTimerSocket = true;
                ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
                ob.writeObject(this);
                socketTimeOut();
            }
            else
                network.startTimer(this, null);
            startTimerSocket = false;
            System.out.println("SELECT YOUR WINDOW!");
            PrintSchemeCard.print(gameModel.getSchemeCards().get(0), gameModel.getSchemeCards().get(1));
            input = new Scanner(System.in);
            while(!input.hasNextInt())
                input = new Scanner(System.in);
            setChoose1(input.nextInt());
            notifyNetwork();
        }
        else{
            System.out.println("\n\nWAIT YOUR TURN...\n\n");
        }
    }

    /**
     * prints client's input possible choices
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewSelectMove1() throws IOException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if(socketConnection){
                startTimerSocket = true;
                ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
                ob.writeObject(this);
                socketTimeOut();
            }
            else
                network.startTimer(this, null);
            startTimerSocket = false;
            int tmp = ShowGameStuff.print((GameModel) gameModel, false);
            while (tmp != 0) {
                tmp = ShowGameStuff.print((GameModel) gameModel, false);
            }
            if(getOnline()) {
                PrintSelectMove1.print();
                input = new Scanner(System.in);
                while(!input.hasNextInt())
                    input = new Scanner(System.in);
                setChoose1(input.nextInt());
                notifyNetwork();
            }
            else{
                if(socketConnection){
                    returnOnline = true;
                    ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
                    ob.writeObject(this);
                    this.setOnline(true);
                }
                else {
                    network.setPlayerOnline(user, true);
                    this.setOnline(true);
                }
            }
        }
        else{
            System.out.println("\n\nWAIT YOUR TURN...\n\n");
        }
    }

    /**
     * prints client's input possible choices
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewSelectMove2() throws IOException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if(socketConnection)
                socketTimeOut();
            int tmp = ShowGameStuff.print((GameModel) gameModel, false);
            while (tmp != 0) {
                tmp = ShowGameStuff.print((GameModel) gameModel, false);
            }
            if(getOnline()) {
                PrintSelectMove2.print();
                input = new Scanner(System.in);
                while(!input.hasNextInt())
                    input = new Scanner(System.in);
                setChoose1(input.nextInt());
                notifyNetwork();
            }
            else{
                if(socketConnection){
                    returnOnline = true;
                    ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
                    ob.writeObject(this);
                    this.setOnline(true);
                }
                else {
                    network.setPlayerOnline(user, true);
                    this.setOnline(true);
                }
            }
        }
    }

    /**
     * prints the player's window and asks him the i,j position to insert it
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewPutDiceInWindow() throws IOException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if(socketConnection)
                socketTimeOut();
            PrintWindow.print(gameModel.getActualPlayer().getWindow());
            System.out.println("CHOOSE A ROW TO PUT YOUR DICE (-1 TO ABORT)");
            input = new Scanner(System.in);
            while(!input.hasNextInt())
                input = new Scanner(System.in);
            setChoose1(input.nextInt());
            if(getOnline()) {
                if (choose1 != -1) {
                    System.out.println("CHOOSE A COLUMN TO PUT YOUR DICE (-1 TO ABORT)");
                    input = new Scanner(System.in);
                    while (!input.hasNextInt())
                        input = new Scanner(System.in);
                    setChoose2(input.nextInt());
                }
                notifyNetwork();
            }
            else{
                if(socketConnection){
                    returnOnline = true;
                    ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
                    ob.writeObject(this);
                    this.setOnline(true);
                }
                else {
                    network.setPlayerOnline(user, true);
                    this.setOnline(true);
                }
            }
        }
    }

    /**
     * prints the list of dice in the draft
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewSelectDraft() throws IOException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if(socketConnection)
                socketTimeOut();
            System.out.println("SELECT A DICE (-1 TO ABORT)");
            PrintDraft.print(gameModel.getField().getDraft());
            input = new Scanner(System.in);
            while(!input.hasNextInt())
                input = new Scanner(System.in);
            setChoose1(input.nextInt());
            notifyNetwork();
        }
    }

    /**
     * prints toolcards available
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewSelectCard() throws IOException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if(socketConnection)
                socketTimeOut();
            System.out.println("SELECT A TOOLCARD (-1 TO ABORT)");
            PrintToolCard.print(gameModel.getField().getToolCards());
            input = new Scanner(System.in);
            while(!input.hasNextInt())
                input = new Scanner(System.in);
            setChoose1(input.nextInt());
            notifyNetwork();
        }
    }

    /**
     * prints selection menu for toolcards
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewUseToolCard() throws IOException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if(socketConnection)
                socketTimeOut();
            PrintUseToolCard.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices, this);
            notifyNetwork();
        }
    }

    /**
     * prints selection menu for toolcards
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewUseToolCard2() throws IOException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if(socketConnection)
                socketTimeOut();
            PrintUseToolCard2.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices, this);
            notifyNetwork();
        }
    }

    /**
     * prints selection menu for toolcards
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewUseToolCard3() throws IOException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if(socketConnection)
                socketTimeOut();
            PrintUseToolCard3.print((GameModel) gameModel, gameModel.getActualPlayer().getToolCardSelected(), choices, this);
            notifyNetwork();
        }
    }

    /**
     * print error message
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void viewError() throws IOException {
        if(user.equals(gameModel.getActualPlayer().getUsername())) {
            if(socketConnection)
                socketTimeOut();
            System.out.println("PLEASE DO IT AGAIN CORRECTLY!");
            if(socketConnection){
                setDeleteConnectionSocket(true);
                ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
                ob.writeObject(this);
            }
            else
                network.update(this);
        }
    }

    /**
     * If the client is online notifies the Server for the model update, else
     * notifies the Server to set Online the client.
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    private void notifyNetwork() throws IOException {
        if (getOnline()) {
            if(socketConnection){
                setDeleteConnectionSocket(true);
                ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
                ob.writeObject(this);
            }
            else
                network.update(this);
        } else {
            if(socketConnection){
                returnOnline = true;
                ObjectOutputStream ob = new ObjectOutputStream(socket.getOutputStream());
                ob.writeObject(this);
                this.setOnline(true);
            }
            else {
                network.setPlayerOnline(user, true);
                this.setOnline(true);
            }
        }
    }

    /**
     * modifies the view based on the current state
     * @param gameModel the gamemodel of the match
     */
   @Override
   public void update(RemoteGameModel gameModel){
       this.gameModel = gameModel;
       try {
           this.run();
       } catch (IOException e) {
           //do nothing
       }
   }

    /**
     * modifies the view based on the current state
     * check if the server has been shut down
     * @throws IOException if an I/O error occurs while reading stream header
     * @throws ClassNotFoundException if class of a serialized object cannot be found
     */
    public void updateSocket() throws IOException, ClassNotFoundException {
        while(!endGame) {
            if(!getDeleteConnectionSocket()) {
                try {
                    ObjectInputStream ob = new ObjectInputStream(socket.getInputStream());
                    this.gameModel = (RemoteGameModel) ob.readObject();
                    if (getOnline() && this.gameModel.getUpdateSocket()) {
                        this.run();
                    }
                }
                catch (SocketException e){
                    System.out.println(SHUTDOWN);
                    System.exit(0);
                }
            }
        }
    }

    /**
     * gets if has started a singleplayer match
     * @return always false
     */
    @Override
    public boolean getSinglePlayer(){
        return false;
    }
}
