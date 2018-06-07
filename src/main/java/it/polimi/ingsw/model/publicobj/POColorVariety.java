package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

import java.util.ArrayList;


public class POColorVariety extends PublicObjective {

    /**
     * creates a public objective card setting score, idnumber, name and description
     */
    public POColorVariety(){
        super.setScore(4);
        super.setIdNumber(5);
        super.setName("Color Variety");
        super.setDescription("Sets of one of each COLOR anywhere");
    }

    /**
     * scrolls through the window's player to count the number of dice of the same color
     * and calculates the score with the minimum sets of same-colored dice
     * @param window is the player's window
     * @return the score calculated based on this card
     */
    @Override
    public int calculateScore(Window window){
        ArrayList<Integer> dicelist  = new ArrayList<>(5);
        dicelist.add(0);
        dicelist.add(0);
        dicelist.add(0);
        dicelist.add(0);
        dicelist.add(0);

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                if (!window.getWindow()[i][j].getIsEmpty()){
                    switch (window.getWindow()[i][j].getDice().getColor()) {
                        case B:
                            dicelist.set(0, dicelist.get(0) + 1);
                            break;
                        case G:
                            dicelist.set(1, dicelist.get(1) + 1);
                            break;
                        case P:
                            dicelist.set(2, dicelist.get(2) + 1);
                            break;
                        case R:
                            dicelist.set(3, dicelist.get(3) + 1);
                            break;
                        case Y:
                            dicelist.set(4, dicelist.get(4) + 1);
                            break;
                        default:    //casella vuota (case W)
                            break;
                    }
                }
            }
        }
        int minsets = dicelist.get(0);
        for(int i : dicelist) {
            if(i < minsets) minsets = i;
        }
        return getScore()*minsets;
    }
}
