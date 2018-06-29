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


    private GameModel(States state, int level){
        updateSocket = true;
        this.players = new ArrayList<>(1);
        this.state = state;
        field = Field.getInstance(level);
        bag = Bag.getInstance();
        roundManager = RoundManager.getInstance();
        setAllColors();
    }

    public static GameModel getInstance(States state, int level){
        if (instance == null)
            instance = new GameModel(state, level);
        return instance;
    }

    @Override
    public boolean getUpdateSocket() {
        return updateSocket;
    }
    @Override
    public void setUpdateSocket(boolean updateSocket) {
        this.updateSocket = updateSocket;
    }

    public static synchronized void reset() {    //TEST METHOD
        instance = null;
    }

    //SETTER
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

    public void setDraft(){
        for (int i = 0; i < (2*players.size())+1; i++)
            field.setDraft();
        if (players.size() == 1)
            field.setDraft();
    }

    public void setPlayers(Player player) throws RemoteException {
        this.players.add(player);
        actualPlayer = this.players.get(0);
    }

    public void setState(States state) throws RemoteException {
        this.state = state;
        notifyObservers(this);
    }

    public void setActualPlayer(int i){
        roundManager.setFirstMove(0);
        actualPlayer = players.get(i);
    }

    private void setAllColors(){
        allColors.add(Colors.B);
        allColors.add(Colors.Y);
        allColors.add(Colors.G);
        allColors.add(Colors.R);
        allColors.add(Colors.P);
    }

    //GETTER
    @Override
    public States getState(){
        return state;
    }
    @Override
    public ArrayList<Player> getPlayers() {
        return players;
    }
    @Override
    public Field getField() {
        return field;
    }
    @Override
    public Bag getBag() {
        return bag;
    }
    @Override
    public ArrayList<SchemeCard> getSchemeCards() {
        return schemeCards;
    }
    @Override
    public Player getActualPlayer(){
        return actualPlayer;
    }
    @Override
    public RoundManager getRoundManager(){
        return roundManager;
    }
    @Override
    public ArrayList<Colors> getAllColors(){
        Collections.shuffle(allColors);
        return allColors;
    }

    //METODI
    public void playerSetWindow(int i){
        actualPlayer.setWindow(schemeCards.get(0), schemeCards.get(1), i);
    }

    public void playerPickDice(int i){
        actualPlayer.pickDice(field.getDraft(), i);
    }

    public boolean playerPutDice(int i, int j){
        return actualPlayer.putDice(i, j);
    }

    public boolean playerSelectToolCardMP(int i){
        return actualPlayer.selectToolCardMP(field.getToolCards(), i);
    }

    public boolean playerSelectToolCardSP(int i){
        return actualPlayer.selectToolCardSP(field.getToolCards(), i);
    }

    public boolean playerUseToolCard(ArrayList<Integer> input){
        return actualPlayer.useToolCard(this, input);
    }

    public int nextPlayer(int actualPlayer){
        actualPlayer = roundManager.changeActualPlayer(actualPlayer, players.size());
        if (getActualPlayer().getSkipNextTurn()) {
            getActualPlayer().setSkipNextTurn(false);
            return roundManager.changeActualPlayer(actualPlayer, players.size());
        }
        else
            return actualPlayer;
    }

    public void endRound(){
        roundManager.endRound(field.getDraft(), field.getRoundTrack());
    }

    public void putDiceInDraft(){
        field.getDraft().addDice(actualPlayer.getDice());
    }

    public void decreaseToken(){
        actualPlayer.decreaseToken();
    }


    public void notifyObservers(GameModel gameModel) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int tmp = 0;
                boolean socketActualPlayer = false;
                try {
                    for(RemoteView observer: getObservers()) {
                        try {
                            if(observer!=null) {
                                if (!actualPlayer.getUsername().equals(observer.getUser())) {
                                    if (observer.getOnline()) {
                                        observer.update(gameModel);
                                    }
                                } else {
                                    tmp = getObservers().indexOf(observer);
                                }
                            }
                        }catch (RemoteException e){
                            //DO NOTHING
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
                        getObservers().get(tmp).update(gameModel); //l'actual player è sempre online!
                }catch (RemoteException e){
                    //DO NOTHING
                }
                catch (IOException e) {
                    //do nothing
                }

            }
        }).start();

    }

    @Override
    public ArrayList<Socket> getObserverSocket(){
        return listSocket;
    }

    public void addObserverSocket(Socket socket){
        listSocket.add(socket);
        list.add(null);
    }

    public void removeObserverSocket(Socket socket){
        listSocket.set(getObserverSocket().indexOf(socket), null);
    }

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

    @Override
    public List<RemoteView> getObservers() throws RemoteException {
        return list;
    }

    @Override
    public void addObserver(RemoteView observer) throws RemoteException {
        list.add(observer);
        listSocket.add(null);
    }

    @Override
    public void removeObserver(RemoteView observer) throws RemoteException {
        list.set(getObservers().indexOf(observer), null);
    }

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
