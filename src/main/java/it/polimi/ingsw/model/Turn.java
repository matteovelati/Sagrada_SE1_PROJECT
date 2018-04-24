package it.polimi.ingsw.model;

public class Turn {

    private Player player1;
    private Move move;
    private int choice;
    private int threshold;

    public Turn(){
    }

    public void PlayerTurn(Player player, Field field){

        move = new Move();
        threshold = field.getDraft().size()-1;

        choice = move.chooseMove1(player, field);
        if (choice >= 0 && choice <= threshold){
            move.chooseMove3(player, field);
        }
        else if (choice == -1){
        }
        else
            move.chooseMove2(player, field);

    }



}
