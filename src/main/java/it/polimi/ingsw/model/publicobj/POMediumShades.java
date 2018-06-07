package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

public class POMediumShades extends PublicObjective {

    /**
     * creates a public objective card setting score, idnumber, name, description
     */
    public POMediumShades(){
        super.setScore(2);
        super.setIdNumber(2);
        super.setName("Medium Shades");
        super.setDescription("Sets of 3 & 4 anywhere");
    }

    /**
     * scrolls through the matrix to count the number of dice whose value is equal to 3 or 4
     * @param window is the player's window
     * @return the score calculated based on this card
     */
    @Override
    public int calculateScore(Window window) {
        return matrixAnalyzer(window, 2);
    }
}
