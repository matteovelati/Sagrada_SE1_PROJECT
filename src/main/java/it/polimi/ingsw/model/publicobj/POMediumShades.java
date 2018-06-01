package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

public class POMediumShades extends PublicObjective {

    public POMediumShades(){
        super.setScore(2);
        super.setIdNumber(2);
        super.setName("Medium Shades");
        super.setDescription("Sets of 3 & 4 anywhere");
    }

    @Override
    public int calculateScore(Window window) {
        return matrixAnalyzer(window, 2);
    }
}
