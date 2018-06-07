package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

public class POShadeVariety extends PublicObjective {

    /**
     * creates a public objective card setting score, idnumber, name, description
     */
    public POShadeVariety(){
        super.setScore(5);
        super.setIdNumber(4);
        super.setName("Shade Variety");
        super.setDescription("Sets of one of each VALUE anywhere");
    }

    /**
     * scrolls through the matrix to count the number of dice for each value
     * @param window is the player's window
     * @return the score calculated based on this card
     */
    @Override
    public int calculateScore(Window window) {
        return matrixAnalyzer(window, 4);
    }
}