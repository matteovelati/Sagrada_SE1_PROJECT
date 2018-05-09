package it.polimi.ingsw.model;

import java.util.ArrayList;

public class GameModel {

    private ArrayList<Player> players;
    private Field field;
    private Bag bag;
    private ArrayList<SchemeCard> schemeCards;
    private Turn turn;
    private States state;

    public GameModel(ArrayList<Player> players, States state){
        this.players = new ArrayList<Player>();
        this.players = players;
        field = Field.getInstance();
        bag = Bag.getInstance();
        schemeCards = new ArrayList<SchemeCard>(2);
        turn = new Turn(players.get(0), players.get(1));
        this.state = state;
    }


    //SETTER (stato)
    public void setState(States state){
        this.state = state;
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

    public Turn getTurn() {
        return turn;
    }


    //SETTA L'ARRAY DI 2 SCHEMECARD DA MOSTRARE AL PLAYER PER LA SCELTA
    public void showSchemeCard(){

    }


    //CHIAMA IL METODO DEL GIOCATORE CHE SETTA LA WINDOW SCELTA (i)
    public boolean playerSetWindow(int i){
        return turn.getActualPlayer().setWindow(schemeCards.get(0), schemeCards.get(1), i);
    }


    //CHIAMA IL METODO DEL GIOCATORE CHE SELEZIONA IL DADO SCELTO (i)
    public void playerPickDice(int i){
        turn.getActualPlayer().pickDice(field.getDraft(), i);
    }


    //CHIAMA IL METODO DEL GIOCATORE CHE METTE IL DADO NELLA POSIZIONE SCELTA (i, j)
    public boolean playerPutDice(int i, int j){
        return turn.getActualPlayer().putDice(i, j);
    }


    //CHIAMA IL METODO DEL GIOCATORE CHE SELEZIONA LA TOOLCARD SCELTA (i)
    public boolean playerSelectToolCard(int i){
        return turn.getActualPlayer().selectToolCard(field.getToolCards(), i);
    }


}
