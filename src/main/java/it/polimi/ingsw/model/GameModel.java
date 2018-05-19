package it.polimi.ingsw.model;

import it.polimi.ingsw.view.cli.RemoteView;
import it.polimi.ingsw.view.cli.ViewObserver;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel implements RemoteGameModel, Serializable {

    private List<RemoteView> list = new ArrayList<>();
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
        this.players = new ArrayList<>();
        this.state = state;
        field = Field.getInstance();
        bag = Bag.getInstance();
        roundManager = RoundManager.getInstance();
        schemeCards = new ArrayList<>();
    }

    public void setSchemeCards(){
        ArrayList<Integer> allSchemeCards = new ArrayList<>();
        Random r = new Random();
        for (int x=1; x<13; x++){
            allSchemeCards.add(x);
        }
        for (int i=0; i<2*players.size(); i++){
            schemeCards.add(new SchemeCard(allSchemeCards.remove(r.nextInt(allSchemeCards.size()))));
        }
    }

    public void setPlayers(Player player) throws RemoteException {
        this.players.add(player);
        if (players.size() == 2) {        //da mettere poi a 4 o quando scade il timer
            setSchemeCards();
            for (int i = 0; i < 2*players.size()+1; i++)
                field.setDraft();
        }
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


    //CHIAMA IL METODO DEL GIOCATORE CHE USA LA TOOLCARD GIà SELEZIONATA
    public boolean playerUseToolCard(ArrayList<Integer> input){
        return actualPlayer.useToolCard(this, input);
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
