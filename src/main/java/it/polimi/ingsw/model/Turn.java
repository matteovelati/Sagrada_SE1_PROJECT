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


    // AGGIORNA L'ACTUAL PLAYER COL NEXT PLAYER DEL TURNO PRECEDENTE E AGGIORNA IL NEXT PLAYER CHE GLI VIENE PASSATO IN INPUT
    public void refresh(Player nextPlayer){
        this.actualPlayer = this.nextPlayer;
        this.nextPlayer = nextPlayer;
    }
}
