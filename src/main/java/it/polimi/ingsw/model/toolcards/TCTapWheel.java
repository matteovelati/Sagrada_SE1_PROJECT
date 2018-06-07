package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.RoundTrack;
import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.model.Window;

import java.util.ArrayList;


public class TCTapWheel extends ToolCard {

    private int flag = 1;

    /**
     * creates a toolcard setting idnumber, color, name, description and number of calls
     */
    public TCTapWheel(){
        super.setIdNumber(12);
        super.setColor(Colors.B);
        super.setName("Tap Wheel");
        super.setDescription("Move up to TWO dice of the same color that match the color of a die on the Round Track.\nYou must obey all placement restrictions.\n");
        super.setCalls(2);
    }

    /**
     * moves up to two dice in player's window that match the color of a die on the roundtrack
     * @param gameModel the gamemodel of the match
     * @param input a list of integer that represents the client's inputs
     * @return true if the toolcard has been used correctly, false otherwise
     */
    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        //arraylist: in 0 indice dado roundtrack; in 1,2 le i,j del dado1 da muovere; in 3,4 le i,j della new pos dado1; in 5 la volontà della seconda mossa ('1' = YES); in 6,7 le i,j del dado1 da muovere; in 8,9 le i,j della new pos dado1
        //IN 0 (-1) PER ANNULLARE
        boolean check;
        if (input.get(0) != -1) {
            if (flag == 1) {
                check = moveDice(gameModel.getActualPlayer().getWindow(), input.get(1), input.get(2), input.get(3), input.get(4), gameModel.getField().getRoundTrack(), input.get(0));
                if (check){
                    flag = 2;
                    return true;
                }
                else
                    return false;
            }
            if (flag == 2) {
                flag = 1;
                if (input.get(5) == 1) {
                    return (moveDice(gameModel.getActualPlayer().getWindow(), input.get(6), input.get(7), input.get(8), input.get(9), gameModel.getField().getRoundTrack(), input.get(0)));
                }
                else {
                    return true;
                }
            } else
                return false;
        }
        else {
            flag = 1;
            return false;   //questo false NON deve richiamare il metodo
        }

    }

    /**
     * check if the actualplayer's window and the roundtrack are empty or not
     * @param gameModel the gamemodel of the match
     * @return true if they aren't both empty, false otherwise
     */
    @Override
    public boolean select(GameModel gameModel){
        return (checkNotEmptyWindow(gameModel.getActualPlayer().getWindow()) && checkNotEmptyRoundTrack(gameModel.getField().getRoundTrack()));
    }

    /**
     * verifies if the die of player's window at i,j position has the same color of the roundtrack die at index k
     * and if can it be placed in x,y position
     * if true, places the die
     * @param window the player's window
     * @param i the actual row of the die
     * @param j the actual column of the die
     * @param x the row to put the die in
     * @param y the column to put the die in
     * @param grid the roundtrack of the game
     * @param k the index of the die in the roundtrack
     * @return true if the die has been placed, false otherwise
     */
    private boolean moveDice(Window window, int i, int j, int x, int y, RoundTrack grid, int k){ //i,j dado da muovere - x,y nuova casella - k dado roundtrack
        Dice dicetmp = window.getWindow()[i][j].getDice();
        window.getWindow()[i][j].setDice(null);
        if (dicetmp.getColor().equals(grid.getGrid().get(k).getColor()) && ((window.verifyAllRestrictions(dicetmp, x, y)) || (window.getIsEmpty() && window.verifyFirstDiceRestriction(dicetmp, x, y)))){
            window.getWindow()[x][y].setDice(dicetmp);
            return true;
        }
        else {
            window.getWindow()[i][j].setDice(dicetmp);
            return false;
        }
    }
}
