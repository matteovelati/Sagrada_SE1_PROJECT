package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Random;

public class Round {

    private Turn turn;
    private int turnNum;

    public Round(){
        turn = new Turn();
    }

    public void manager(ArrayList<Player> players, int actualPlayer, Field field, Bag bag){

        for(int i=0; i<(players.size()*2)+1; i++){//--------------------------estrae i dadi dal sacchetto(2n+1) e li mette nella riserva uno alla volta
            Dice dice;
            Random r = new Random();
            dice = bag.extract(r.nextInt(bag.getBag().size()));
            field.getDraft().setDraft(dice);
        }

        turnNum = 1;

        for(int i=0; i<players.size(); i++){
            //System.out.println("Primo turno giocatore: " + /*actualPlayer*/players.get(actualPlayer).getUsername() + "\n");
            turn.playerTurn(players.get(actualPlayer), field);
            actualPlayer++;
            if(actualPlayer == players.size())
                actualPlayer = 0;
        }

        turnNum = 2;

        for(int i=0; i<players.size(); i++){
            actualPlayer--;
            if(actualPlayer == -1)
                actualPlayer = players.size()-1;
            //System.out.println("Secondo turno giocatore: " + /*actualPlayer*/players.get(actualPlayer).getUsername() + "\n");
            turn.playerTurn(players.get(actualPlayer), field);
        }

    }
}
