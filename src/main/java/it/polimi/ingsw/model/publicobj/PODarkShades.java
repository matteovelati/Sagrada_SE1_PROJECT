package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

public class PODarkShades extends PublicObjective {

    public PODarkShades(){
        super.setScore(2);
        super.setIdNumber(3);
        super.setName("Dark Shades");
        super.setDescription("Sets of 5 & 6 anywhere");
    }

    @Override
    public int calculateScore(Window window) {
        return matrixAnalyzer(window,3);
    }

}
