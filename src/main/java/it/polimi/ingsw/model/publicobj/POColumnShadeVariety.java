package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

import java.util.ArrayList;


public class POColumnShadeVariety extends PublicObjective {

    private ArrayList<Integer> valuesBag = new ArrayList<>(6);

    /**
     * creates a public objective card setting score, idnumber, name and description
     */
    public POColumnShadeVariety(){
        super.setScore(4);
        super.setIdNumber(7);
        super.setName("Column Shade Variety");
        super.setDescription("Columns with no repeated VALUES");
    }

    /**
     * scrolls through the matrix to find how many different columns there are with no repeated values
     * @param window is the player's window
     * @return the score calculated based on this card
     */
    @Override
    public int calculateScore(Window window){
        int diffcolumns = 0;
        int diffvalues;

        for(int j = 0; j < 5; j++) {
            setValuesBag();
            diffvalues = 0;
            for(int i = 0; i < 4; i++) {
                if (!window.getWindow()[i][j].getIsEmpty()){
                    if (!valuesBag.remove((Integer) window.getWindow()[i][j].getDice().getValue())) {
                        setValuesBag();
                        diffvalues = 0;
                        break;
                    } else diffvalues++;
                }
            }
            if(diffvalues == 4) diffcolumns ++;
        }

        return getScore()*diffcolumns;
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