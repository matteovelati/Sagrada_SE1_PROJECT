package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

public class POLightShades extends PublicObjective {

    public POLightShades(){
        super.setScore(2);
        super.setIdNumber(1);
        super.setName("Light Shades");
        super.setDescription("Sets of 1 & 2 anywhere");
    }

    @Override
    public int calculateScore(Window window) {
        return matrixAnalyzer(window, 1);
    }

}
