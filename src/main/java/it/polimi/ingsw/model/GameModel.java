package it.polimi.ingsw.model;

import java.util.ArrayList;

public class GameModel {

    private ArrayList<Player> players;
    private Field field;
    private Bag bag;
    private ArrayList<SchemeCard> schemeCards;
    private States state;
    private Player actualPlayer;
    private RoundManager roundManager;


    //COSTRUTTORE
    public GameModel(ArrayList<Player> players, States state){
        this.players = new ArrayList<Player>();
        this.players = players;
        field = Field.getInstance();
        bag = Bag.getInstance();
        schemeCards = new ArrayList<SchemeCard>(2);
        this.state = state;
        actualPlayer = players.get(0);
        roundManager = RoundManager.getInstance();
    }


    //SETTER (stato e actualPlayer)
    public void setState(States state){
        this.state = state;
    }

    public void setActualPlayer(int i){
        actualPlayer = players.get(i);
    }


    //GETTER (tutti)
    public States getState(){
        return state;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Field getField() {
        return field;
    }

    public Bag getBag() {
        return bag;
    }

    public ArrayList<SchemeCard> getSchemeCards() {
        return schemeCards;
    }

    public Player getActualPlayer(){
        return actualPlayer;
    }

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


}