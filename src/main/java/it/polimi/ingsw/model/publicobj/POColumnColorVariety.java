package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

import java.util.ArrayList;


public class POColumnColorVariety extends PublicObjective {

    private ArrayList<Colors> colorsBag = new ArrayList<>(5);

    /**
     * creates a public objective card setting score, idnumber, name and description
     */
    public POColumnColorVariety(){
        super.setScore(5);
        super.setIdNumber(9);
        super.setName("Column Color Variety");
        super.setDescription("Column with no repeated COLORS");
    }

    /**
     * scrolls through the matrix to find how many different columns there are with no repeated colors
     * @param window is the player's window
     * @return the score calculated based on this card
     */
    @Override
    public int calculateScore(Window window){
        int diffcolumns = 0;
        int diffcolors;

        for(int j = 0; j < 5; j++) {
            setColorsBag();
            diffcolors = 0;
            for(int i = 0; i < 4; i++) {
                if (!window.getWindow()[i][j].getIsEmpty()) {
                    if (!colorsBag.remove(window.getWindow()[i][j].getDice().getColor())) {
                        setColorsBag();
                        diffcolors = 0;
                        break;
                    } else diffcolors++;
                }
            }
            if(diffcolors == 4) diffcolumns ++;
        }

        return getScore()*diffcolumns;
    }

    /**
     * creates a temporary bag of die's colors
     */
    private void setColorsBag() {
        colorsBag.clear();
        colorsBag.add(Colors.B);
        colorsBag.add(Colors.G);
        colorsBag.add(Colors.P);
        colorsBag.add(Colors.R);
        colorsBag.add(Colors.Y);
    }

}
