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

    public void setState(States state){
        this.state = state;
    }

    public States getState(){
        return state;
    }

    public void showSchemeCard(){

    }

    public void playerSetWindow(int i){
        boolean b = turn.getActualPlayer().setWindow(schemeCards.get(0), schemeCards.get(1), i);
    }

    public void playerPickDice(int i){
        turn.getActualPlayer().pickDice(field.getDraft(), i);
    }

    public void playerSelectToolCard(int i){
        boolean b = turn.getActualPlayer().selectToolCard(field.getToolCards(), i);
    }

    public void playerPutDice(int i, int j){
        boolean b = turn.getActualPlayer().putDice(i, j);
    }
}
