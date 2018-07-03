package it.polimi.ingsw.model;

import it.polimi.ingsw.view.RemoteView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameModel implements RemoteGameModel, Serializable {

    private transient ArrayList<Socket> listSocket = new ArrayList<>();
    private List<RemoteView> list = new ArrayList<>();
    private ArrayList<Player> players;
    private ArrayList<Colors> allColors = new ArrayList<>(5);
    private Field field;
    private Bag bag;
    private ArrayList<SchemeCard> schemeCards;
    private States state;
    private Player actualPlayer;
    private RoundManager roundManager;
    private static GameModel instance;
    private boolean updateSocket;

    /**
     * creates a GameModel object which contains instance of Field, Bag and Roundmanager
     * initialize an arraylist of players and sets the first state of the game (LOBBY)
     * @param level identifies if is a singleplayer match [1-5] or a multiplayer match [0]
     */
    private GameModel(int level){
        updateSocket = true;
        this.players = new ArrayList<>(1);
        this.state = States.LOBBY;
        field = Field.getInstance(level);
        bag = Bag.getInstance();
        roundManager = RoundManager.getInstance();
        setAllColors();
    }

    /**
     * if the gamemodel already exists, the method returns the Gamemodel object,
     * otherwise it creates a new GameModel.
     * @param level identifies if is a singleplayer match [1-5] or a multiplayer match [0]
     * @return the instance of the GameModel class
     */
    public static GameModel getInstance(int level){
        if (instance == null)
            instance = new GameModel(level);
        return instance;
    }

    /**
     * gets if socket needs to be updated or not
     * @return true if socket needs to be updated, false otherwise
     */
    @Override
    public boolean getUpdateSocket() {
        return updateSocket;
    }

    /**
     * sets if socket needs to be updated
     * @param updateSocket the boolean to be set
     */
    @Override
    public void setUpdateSocket(boolean updateSocket) {
        this.updateSocket = updateSocket;
    }

    /**
     * deletes the instance of this class to restart the game
     */
    public static synchronized void reset() {    //TEST METHOD + RESTART GAME
        instance = null;
    }

    /**
     * sets the schemecards of the match, 2 for each player (4 window)
     */
    public void setSchemeCards(){
        schemeCards = new ArrayList<>();
        ArrayList<Integer> allSchemeCards = new ArrayList<>();
        Random r = new Random();
        for (int x=1; x<13; x++){
            allSchemeCards.add(x);
        }
        for (int i=0; i<2*players.size(); i++){
            schemeCards.add(new SchemeCard(allSchemeCards.remove(r.nextInt(allSchemeCards.size()))));
        }
    }

    /**
     * sets the draft based on the players in the game (always 4 dice if singleplayer)
     */
    public void setDraft(){
        for (int i = 0; i < (2*players.size())+1; i++)
            field.setDraft();
        if (players.size() == 1)
            field.setDraft();
    }

    /**
     * adds a new player to the match and sets the first actualplayer
     * @param player the player to be added
     */
    public void setPlayers(Player player){
        this.players.add(player);
        actualPlayer = this.players.get(0);
    }

    /**
     * sets the new state of the gamemodel and notifies all the observer of the state changed
     * @param state the state to be set
     */
    public void setState(States state){
        this.state = state;
        notifyObservers(this);
    }

    /**
     * sets the actual player
     * @param i the index of the new actual player
     */
    public void setActualPlayer(int i){
        roundManager.setFirstMove(0);
        actualPlayer = players.get(i);
    }

    /**
     * adds five colors to an arraylist
     */
    private void setAllColors(){
        allColors.add(Colors.B);
        allColors.add(Colors.Y);
        allColors.add(Colors.G);
        allColors.add(Colors.R);
        allColors.add(Colors.P);
    }

    /**
     * gets the state of gamemodel
     * @return the actual state of game model
     */
    @Override
    public States getState(){
        return state;
    }

    /**
     * gets the arraylist of players in the match
     * @return the arraylist of player in the match
     */
    @Override
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * gets the field of the match
     * @return the field of the match
     */
    @Override
    public Field getField() {
        return field;
    }

    /**
     * gets the bag of the match
     * @return the bag of the match
     */
    @Override
    public Bag getBag() {
        return bag;
    }

    /**
     * gets the schemecards of the match
     * @return an arraylist of schemecards of the match
     */
    @Override
    public ArrayList<SchemeCard> getSchemeCards() {
        return schemeCards;
    }

    /**
     * gets the actual player
     * @return the actual player
     */
    @Override
    public Player getActualPlayer(){
        return actualPlayer;
    }

    /**
     * gets the roundmanager
     * @return return the round manager
     */
    @Override
    public RoundManager getRoundManager(){
        return roundManager;
    }

    /**
     * gets all the colors of the Colors's enumeration
     * @return a list which contains 5 colors
     */
    @Override
    public ArrayList<Colors> getAllColors(){
        Collections.shuffle(allColors);
        return allColors;
    }

    /**
     * sets the window chosen to the actual player
     * @param i the index of the window chosen
     */
    public void playerSetWindow(int i){
        actualPlayer.setWindow(schemeCards.get(0), schemeCards.get(1), i);
    }

    /**
     * picks a die from the draft
     * @param i the index of the die to be extracted
     */
    public void playerPickDice(int i){
        actualPlayer.pickDice(field.getDraft(), i);
    }

    /**
     * puts the picked die in the window at position [i,j]
     * @param i the row
     * @param j the column
     * @return true if the die has been added, false otherwise
     */
    public boolean playerPutDice(int i, int j){
        return actualPlayer.putDice(i, j);
    }

    /**
     * selects a toolcard
     * @param i the index of the toolcard selected
     * @return true if the toolcard can be used and has been selected, false otherwise
     */
    public boolean playerSelectToolCardMP(int i){
        return actualPlayer.selectToolCardMP(field.getToolCards(), i);
    }

    /**
     * selects a toolcard
     * @param i the index of the toolcard selected
     * @return true if the toolcard can be used and has been selected, false otherwise
     */
    public boolean playerSelectToolCardSP(int i){
        return actualPlayer.selectToolCardSP(field.getToolCards(), i);
    }

    /**
     * uses a toolcard
     * @param input the arraylist of client's inputs
     * @return true if the toolcard has been used, false otherwise
     */
    public boolean playerUseToolCard(ArrayList<Integer> input){
        return actualPlayer.useToolCard(this, input);
    }

    /**
     * calulates the next actual player
     * @param actualPlayer the index of the actual player
     * @return the index of the new actual player
     */
    public int nextPlayer(int actualPlayer){
        return roundManager.changeActualPlayer(actualPlayer, players.size());
    }

    /**
     * manages the remaining dice in the draft putting them in the roundtrack
     * increments the index of rounds
     */
    public void endRound(){
        roundManager.endRound(field.getDraft(), field.getRoundTrack());
    }

    /**
     * adds a die to the draft
     */
    public void putDiceInDraft(){
        field.getDraft().addDice(actualPlayer.getDice());
    }

    /**
     * decrease the player's token
     */
    public void decreaseToken(){
        actualPlayer.decreaseToken();
    }

    /**
     * notifies all the not null observers (rmi+socket)
     * @param gameModel the gamemodel of the match
     */
    public void notifyObservers(GameModel gameModel) {
        new Thread(() -> {
            int tmp = 0;
            boolean socketActualPlayer = false;
            try {
                for(RemoteView observer: getObservers()) {
                    if(observer!=null) {
                        if (!actualPlayer.getUsername().equals(observer.getUser())) {
                            if (observer.getOnline()) {
                                observer.update(gameModel);
                            }
                        } else {
                            tmp = getObservers().indexOf(observer);
                        }
                    }
                }
            } catch (RemoteException e) {
                //do nothing
            }
            for(Socket observer: getObserverSocket()) {
                if(observer!=null) {
                    if (!actualPlayer.getUsername().equals(getPlayers().get(getObserverSocket().indexOf(observer)).getUsername())) {
                        try {
                            if(getPlayers().get(getObserverSocket().indexOf(observer)).getOnline()) {
                                ObjectOutputStream ob = new ObjectOutputStream(observer.getOutputStream());
                                ob.writeObject(gameModel);
                            }
                        } catch (IOException e) {
                            //do nothing
                        }
                    } else {
                        socketActualPlayer = true;
                        tmp = getObserverSocket().indexOf(observer);
                    }
                }
            }
            try {
                if(socketActualPlayer){
                    ObjectOutputStream ob = new ObjectOutputStream(getObserverSocket().get(tmp).getOutputStream());
                    ob.writeObject(gameModel);
                }
                else
                    getObservers().get(tmp).update(gameModel);
            }catch (Exception e){
                //DO NOTHING
            }
        }).start();

    }

    /**
     * gets the list of SOCKET observers in game
     * @return the list of SOCKET observers in game
     */
    @Override
    public ArrayList<Socket> getObserverSocket(){
        return listSocket;
    }

    /**
     * adds a socket observer to the match
     * @param socket the socket observer to be added
     */
    public void addObserverSocket(Socket socket){
        listSocket.add(socket);
        list.add(null);
    }

    /**
     * removes an RMI observer from the observers's list (setting him as 'null')
     * @param socket the observer to be removed
     */
    public void removeObserverSocket(Socket socket){
        listSocket.set(getObserverSocket().indexOf(socket), null);
    }

    /**
     * adds again a socket observer after he has lost connection
     * @param socket the observer to be added
     * @param user the name of the observer
     */
    public void reAddObserverSocket(Socket socket, String user){
        int index = 0;
        for(Player x : players){
            if(x.getUsername().equals(user)) {
                index = players.indexOf(x);
                break;
            }
        }
        listSocket.set(index, socket);
    }

    /**
     * gets the list of RMI observers in game
     * @return the list of RMI observers in game
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public List<RemoteView> getObservers(){
        return list;
    }

    /**
     * adds an RMI observer at the beginning
     * @param observer the observer to be added
     */
    @Override
    public void addObserver(RemoteView observer){
        list.add(observer);
        listSocket.add(null);
    }

    /**
     * removes an RMI observer from the observers's list (setting him as 'null')
     * @param observer the observer to be removed
     */
    @Override
    public void removeObserver(RemoteView observer){
        list.set(getObservers().indexOf(observer), null);
    }

    /**
     * adds again an RMI observer after he has lost connection
     * @param observer the observer to be added
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void reAddObserver(RemoteView observer) throws RemoteException {
        int index = 0;
        for(Player x : players){
            if(x.getUsername().equals(observer.getUser())) {
                index = players.indexOf(x);
                break;
            }
        }
        list.set(index, observer);
    }
}
