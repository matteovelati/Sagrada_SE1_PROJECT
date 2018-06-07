package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

import java.util.ArrayList;


public class PORowShadeVariety extends PublicObjective {

    private ArrayList<Integer> valuesBag = new ArrayList<>(6);

    /**
     * creates a public objective card setting score, idnumber, name and description
     */
    public PORowShadeVariety(){
        super.setScore(5);
        super.setIdNumber(6);
        super.setName("Row Shade Variety");
        super.setDescription("Rows with no repeated VALUES");
    }

    /**
     * scrolls through the matrix to find how many different rows there are with no repeated values
     * @param window is the player's window
     * @return the score calculated based on this card
     */
    @Override
    public int calculateScore(Window window){
        int diffrows = 0;
        int diffvalues;


        for(int i = 0; i < 4; i++) {
            setValuesBag();
            diffvalues = 0;
            for(int j = 0; j < 5; j++) {
                if (!window.getWindow()[i][j].getIsEmpty()) {
                    if (!valuesBag.remove((Integer) window.getWindow()[i][j].getDice().getValue())) {
                        setValuesBag();
                        diffvalues = 0;
                        break;
                    } else diffvalues++;
                }
            }
            if(diffvalues == 5) diffrows ++;
        }

        return getScore()*diffrows;
    }

    /**
     * creates a temporary bag of die's values
     */
    private void setValuesBag() {
        valuesBag.clear();
        valuesBag.add(1);
        valuesBag.add(2);
        valuesBag.add(3);
        valuesBag.add(4);
        valuesBag.add(5);
        valuesBag.add(6);
    }

}
