package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

public class POShadeVariety extends PublicObjective {

    public POShadeVariety(){
        super.setScore(5);
        super.setIdNumber(4);
        super.setName("Shade Variety");
        super.setDescription("Sets of one of each VALUE anywhere");
    }

    @Override
    public int calculateScore(Window window) {
        return matrixAnalyzer(window, 4);
    }
}