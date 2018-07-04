package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.RemoteGameController;
import it.polimi.ingsw.model.RemoteGameModel;
import it.polimi.ingsw.model.States;
import it.polimi.ingsw.view.RemoteView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class ViewCLI implements RemoteView, Serializable {

    protected final String SHUTDOWN = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nSEEMS LIKE THE SERVER HAS BEEN SHUT DOWN";

    protected boolean singlePlayer;
    protected States state;
    protected String user;
    protected transient Scanner input;
    protected boolean endGame;
    protected int choose1;
    protected int choose2;
    protected ArrayList<Integer> choices;
    protected boolean online;
    protected RemoteGameModel gameModel;
    protected RemoteGameController network;
    protected String ipAddress;
    protected boolean returnOnline;
    protected boolean socketConnection;
    protected boolean startTimerSocket;
    protected boolean deleteConnectionSocket;
    protected transient Socket socket;
    protected boolean restart;
    protected int level;

    /**
     * initializes the local variables and set the client connection
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    public ViewCLI() throws IOException {
        choices = new ArrayList<>(1);
        restart = false;
        endGame = false;
        if(getSinglePlayer())
            choices.add(-1);
        else
            choose1 = 1;
        connectionRequest();

    }

    /**
     * gets the level of difficulty of a singleplayer match
     * @return always 0, meaning multiplayer
     */
    @Override
    public int getLevel(){
        return level;
    }

    /**
     * gets if the socket timeout connection needs be deleted or not
     * @return true if the socket connection needs to be deleted, false otherwise
     */
    @Override
    public synchronized boolean getDeleteConnectionSocket() {
        return deleteConnectionSocket;
    }

    /**
     * sets if the socket timeout connection needs to be deleted or not
     * @param x the boolean to be set
     */
    public synchronized void setDeleteConnectionSocket(boolean x){
        this.deleteConnectionSocket = x;
    }

    /**
     * gets if a player has to be set online
     * @return true if the player has to be set online
     */
    @Override
    public boolean getReturnOnline(){
        return returnOnline;
    }

    /**
     * gets if it's needed to start the timer
     * @return true if the timer has to be started
     */
    @Override
    public boolean getStartTimerSocket() {
        return startTimerSocket;
    }

    /**
     * sets if the client is online or not
     * @param online the boolean to be set
     */
    @Override
    public synchronized void setOnline(boolean online) throws RemoteException {
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
     * gets if has started a singleplayer match
     * @return always false
     */
    @Override
    public boolean getSinglePlayer(){
        return singlePlayer;
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
     * sets choose1
     * @param choose1 the int to be set
     */
    protected void setChoose1(int choose1){
        this.choose1 = choose1;
    }

    /**
     * sets choose2
     * @param choose2 the int to be set
     */
    protected void setChoose2(int choose2){
        this.choose2 = choose2;
    }

    /**
     * initializes the client connection with an ip address request and a connection type
     * request. If the Client inserts an inexistent ip address, shut down the client.
     * @throws IOException any exception thrown by the underlying OutputStream
     */
    protected void connectionRequest() throws IOException {
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
                UnicastRemoteObject.exportObject(this, 0);
            } catch (RemoteException e) {
                System.out.println("\n\nTHIS IP ADDRESS DOES NOT EXIST");
                System.exit(0);
            } catch (Exception e){
                System.out.println("\n\nOPS... AN ERROR OCCURRED. PLEASE RESTART THE GAME.");
                System.exit(0);
            }
        }
        else{                           //SOCKET CONNECTION
            socketConnection = true;
            try {
                socket = new Socket(ipAddress, 1337);
                if(getSinglePlayer()){
                    System.out.println("CHOOSE A LEVEL OF DIFFICULTY FROM 1 (BEGINNER) TO 5 (EXTREME)");
                    input = new Scanner(System.in);
                    do {
                        while (!input.hasNextInt())
                            input = new Scanner(System.in);
                        level = input.nextInt();
                    } while (level < 1 || level > 5);
                }
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
        startTimerSocket = false;
        deleteConnectionSocket = false;
        returnOnline = false;
        setOnline(true);
    }

    /**
     * sets the username of the client
     */
    protected void setUser() {
        input = new Scanner(System.in);
        System.out.println("ENTER YOUR USERNAME:");
        this.user = input.next().toUpperCase();
    }
}
