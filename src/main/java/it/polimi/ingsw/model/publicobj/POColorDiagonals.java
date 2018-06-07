package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;


public class POColorDiagonals extends PublicObjective {

    /**
     * creates a public objective card setting idnumber, name, description
     */
    public POColorDiagonals(){
        super.setIdNumber(10);
        super.setName("Color Diagonals");
        super.setDescription("Count of diagonally adjacent same-color dice");
    }

    /**
     * scrolls through player's window to find if there is at least one adjacent die of the same color in it's diagonals
     * @param window is the player's window
     * @return the number of die diagonally adjacent
     */
    @Override
    public int calculateScore(Window window){
        Colors color;
        for(int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                if (!window.getWindow()[i][j].getIsEmpty()){
                    color = window.getWindow()[i][j].getDice().getColor();
                    setScore(getScore() + (verifyColor(window, i, j, color) ? 1 : 0));
                }
            }
        }
        return getScore();
    }

    /**
     * check if there is at least one die in the diagonals of the i,j position (NW-SW-SE-NE)
     * @param window the player's window
     * @param i the selected row of the matrix
     * @param j the selected column of the matrix
     * @param color the color of the die in i,j position
     * @return true if the die is adjacent to a same-colored die, otherwise
     */
    private boolean verifyColor(Window window, int i, int j, Colors color) {
        for (int x = -1; x < 2; x += 2) {
            for (int y = -1; y < 2; y += 2) {
                try {
                    if (color.equals(window.getWindow()[i + x][j + y].getDice().getColor()))
                        return true;
                } catch (Exception e) {
                    //DO NOTHING
                }
            }
        }
        return !window.neighboursColorRestriction(window.getWindow()[i][j].getDice(), i, j);
    }
}
