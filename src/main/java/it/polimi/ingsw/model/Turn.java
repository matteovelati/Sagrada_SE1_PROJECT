package it.polimi.ingsw.model;

public class Turn {

    private Player actualPlayer;
    private Player nextPlayer;

    public Turn(Player actualPlayer, Player nextPlayer){
        this.actualPlayer = actualPlayer;
        this.nextPlayer = actualPlayer;
    }

    public Player getActualPlayer(){
        return actualPlayer;
    }

    public void refresh(Player actualPlayer, Player nextPlayer){
        this.actualPlayer = actualPlayer;
        this.nextPlayer = nextPlayer;
    }
}
