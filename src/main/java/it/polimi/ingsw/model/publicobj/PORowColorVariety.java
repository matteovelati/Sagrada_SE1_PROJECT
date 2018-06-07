package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

import java.util.ArrayList;


public class PORowColorVariety extends PublicObjective {

    private ArrayList<Colors> colorsBag = new ArrayList<>(5);

    /**
     * creates a public objective card setting score, idnumber, name and description
     */
    public PORowColorVariety(){
        super.setScore(6);
        super.setIdNumber(8);
        super.setName("Row Color Variety");
        super.setDescription("Rows with no repeated COLORS");
    }

    /**
     * scrolls through the matrix to find how many different rows there are with no repeated colors
     * @param window is the player's window
     * @return the score calculated based on this card
     */
    @Override
    public int calculateScore(Window window){
        int diffrows = 0;
        int diffcolors;

        for(int i = 0; i < 4; i++) {
            setColorsBag();
            diffcolors = 0;
            for(int j = 0; j < 5; j++) {
                if (!window.getWindow()[i][j].getIsEmpty()) {
                    if (!colorsBag.remove(window.getWindow()[i][j].getDice().getColor())) {
                        setColorsBag();
                        diffcolors = 0;
                        break;
                    } else diffcolors++;
                }
            }
            if(diffcolors == 5) diffrows ++;
        }

        return getScore()*diffrows;
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