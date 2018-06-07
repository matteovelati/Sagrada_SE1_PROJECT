package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

public class PODarkShades extends PublicObjective {

    /**
     * creates a public objective card setting score, idnumber, name and description
     */
    public PODarkShades(){
        super.setScore(2);
        super.setIdNumber(3);
        super.setName("Dark Shades");
        super.setDescription("Sets of 5 & 6 anywhere");
    }

    /**
     * scrolls through the matrix to count the number of dice whose value is equal to 5 or 6
     * @param window is the player's window
     * @return the score calculated based on this card
     */
    @Override
    public int calculateScore(Window window) {
        return matrixAnalyzer(window,3);
    }

}
