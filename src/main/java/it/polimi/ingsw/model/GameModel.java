package it.polimi.ingsw.model;

import it.polimi.ingsw.view.cli.RemoteView;
import it.polimi.ingsw.view.cli.ViewObserver;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class GameModel implements RemoteGameModel, Serializable {

    private List<RemoteView> list = new ArrayList<RemoteView>();

    private ArrayList<Player> players;
    private Field field;
    private Bag bag;
    private ArrayList<SchemeCard> schemeCards;
    private States state;
    private Player actualPlayer;
    private RoundManager roundManager;


    public GameModel(ArrayList<Player> players, States state){}
    //COSTRUTTORE
    public GameModel(States state) {
        this.players = new ArrayList<Player>();
        field = Field.getInstance();
        bag = Bag.getInstance();
        schemeCards = new ArrayList<SchemeCard>(2);
        this.state = state;
        roundManager = RoundManager.getInstance();
    }

    public void setPlayers(Player player) throws RemoteException {
        this.players.add(player);
        list.get(list.size()-1).print("YOU HAVE BEEN ADDED TO THIS GAME!");
        if(players.size() == 2) {
            for(RemoteView x : list){
                x.print("ARE YOU READY?! THE GAME IS STARTING...");
            }
            actualPlayer = this.players.get(0);
        }
    }

    //SETTER (stato e actualPlayer)
    public void setState(States state) throws RemoteException {
        this.state = state;
        notifyObservers();
    }

    public void setActualPlayer(int i){
        actualPlayer = players.get(i);
    }


    //GETTER (tutti)
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


    //SETTA L'ARRAY DI 2 SCHEMECARD DA MOSTRARE AL PLAYER PER LA SCELTA
    public void showSchemeCard(){

    }


    //CHIAMA IL METODO DEL GIOCATORE CHE SETTA LA WINDOW SCELTA (i)
    public boolean playerSetWindow(int i){
        return actualPlayer.setWindow(schemeCards.get(0), schemeCards.get(1), i);
    }


    //CHIAMA IL METODO DEL GIOCATORE CHE SELEZIONA IL DADO SCELTO (i)
    public void playerPickDice(int i){
        actualPlayer.pickDice(field.getDraft(), i);
    }


    //CHIAMA IL METODO DEL GIOCATORE CHE METTE IL DADO NELLA POSIZIONE SCELTA (i, j)
    public boolean playerPutDice(int i, int j){
        return actualPlayer.putDice(i, j);
    }


    //CHIAMA IL METODO DEL GIOCATORE CHE SELEZIONA LA TOOLCARD SCELTA (i)
    public boolean playerSelectToolCard(int i){
        return actualPlayer.selectToolCard(field.getToolCards(), i);
    }


    @Override
    public void notifyObservers() throws RemoteException {
        for(RemoteView observer: getObservers()) {
            observer.update(this);
        }
    }

    @Override
    public List<RemoteView> getObservers() throws RemoteException {
        return list;
    }

    @Override
    public void addObserver(RemoteView observer) throws RemoteException {
        list.add(observer);
    }

    @Override
    public void removeObserver(RemoteView observer) throws RemoteException {
        list.remove(observer);
    }
}
