package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

public class POLightShades extends PublicObjective {

    /**
     * creates a public objective card setting score, idnumber, name, description
     */
    public POLightShades(){
        super.setScore(2);
        super.setIdNumber(1);
        super.setName("Light Shades");
        super.setDescription("Sets of 1 & 2 anywhere");
    }

    /**
     * scrolls through the matrix to count the number of dice whose value is equal to 1 or 2
     * @param window is the player's window
     * @return the score calculated based on this card
     */
    @Override
    public int calculateScore(Window window) {
        return matrixAnalyzer(window, 1);
    }

}
