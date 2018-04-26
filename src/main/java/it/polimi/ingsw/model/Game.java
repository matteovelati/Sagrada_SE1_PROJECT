package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Random;

public class Game {

    private ArrayList<Player> players;
    private Field field;
    private Bag bag;
    private ArrayList<SchemeCard> schemeCards;
    private Round round;
    private int firstPlayer;

    public Game(ArrayList<Player> players){
        firstPlayer = 0;
        this.players = players;
        schemeCards = new ArrayList<SchemeCard>();
        for(int i=1; i<24; i++){
            schemeCards.add(new SchemeCard(new Window(i), new Window(i+1)));
        }
        Random r = new Random();
        ToolCard toolCards[] = new ToolCard[3];
        toolCards[0] = new ToolCard(r.nextInt(12)+1);
        toolCards[1] = new ToolCard(r.nextInt(12)+1);
        toolCards[2] = new ToolCard(r.nextInt(12)+1);
        PublicObjective[] publicObjectives = new PublicObjective[3];
        publicObjectives[0] = new PublicObjective(r.nextInt(10)+1);
        publicObjectives[1] = new PublicObjective(r.nextInt(10)+1);
        publicObjectives[2] = new PublicObjective(r.nextInt(10)+1);
        field = Field.getInstance(toolCards, publicObjectives);
        bag = Bag.getInstance();
        round = new Round();
    }

    public void play(){
        Random r = new Random();
        for(int i=0; i<players.size(); i++){
            System.out.println("PLAYER "+players.get(i).getUsername());
            players.get(i).chooseWindow(schemeCards.get(r.nextInt(schemeCards.size())), schemeCards.get(r.nextInt(schemeCards.size())));
        }
        for(int i=0; i<10; i++){
            round.manager(players, firstPlayer, field, bag);
            firstPlayer ++;
            if(firstPlayer == players.size()){
                firstPlayer = 0;
            }
            field.getRoundTrack().setGrid(field.getDraft().extract(0));
            field.getDraft().getDraft().clear();
        }
    }
}
