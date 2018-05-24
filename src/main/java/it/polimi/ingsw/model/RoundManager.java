package it.polimi.ingsw.model;

import java.io.Serializable;

public class RoundManager implements Serializable {

    private static RoundManager instance = null;

    private int turn;
    private int counter;
    private int firstMove;//----------------------------MEMORIZZA LA PRIMA MOSSA FATTA DA UN GIOCATORE(1:SELEZIONA DADO 2:SCEGLI TOOLCARD)


    //--------------------------------------COSTRUTTORE(SINGLETON)
    private RoundManager(){
        turn = 1;
        counter = 1;
        firstMove = 0;
    }

    public static RoundManager getInstance(){
        if (instance == null)
            instance = new RoundManager();
        return instance;
    }


    //---------------------------------------GETTER
    public int getTurn() {
        return turn;
    }

    public int getCounter() {
        return counter;
    }

    public int getFirstMove() {
        return firstMove;
    }


    //-----------------------------------------SETTER
    public void setFirstMove(int firstMove){
        this.firstMove = firstMove;
    }


    //----------------------------------------METODI
    public int changeActualPlayer(int actualPlayer, int nPlayers){

        switch(turn) {

            case 1:

                if (counter < nPlayers) {

                    actualPlayer = ChangePlayer.clockwise(actualPlayer, nPlayers);
                    counter++;

                } else if (counter == nPlayers) {
                    counter --;
                    turn = 2;
                }

                break;

            case 2:

                if(counter > 0){

                    counter--;
                    actualPlayer = ChangePlayer.antiClockwise(actualPlayer, nPlayers);

                }else if(counter == 0){

                    counter = 1;
                    turn = 1;
                    actualPlayer = ChangePlayer.clockwise(actualPlayer, nPlayers);

                }
                break;

            default:
                return -1;//---------------------------------------------------------------------ERRORE GESTIONE TURNO
        }
        return actualPlayer;

    }

    public void endRound(Draft draft, RoundTrack roundTrack){

        //DA GESTIRE IL CASO IN CUI I DADI RIMASTI NELLA DRAFT SIANO PIù DI UNO

        while(!draft.getDraft().isEmpty()) {
            roundTrack.setGrid(draft.getDraft().remove(0));
        }

        roundTrack.incrementRound();

    }

    public void setTurn(int turn) { //metodo toolcardtest (glazinghammer)
        this.turn = turn;
    }
}
