package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

import java.util.ArrayList;


public class POLightShades extends PublicObjective {

    public POLightShades(){
        super.setScore(2);
        super.setIdNumber(1);
        super.setName("Light Shades");
        super.setDescription("Sets of 1 & 2 anywhere");
    }

    @Override
    public int calculateScore(Window window) {
        ArrayList<Integer> dicelist = new ArrayList<>(2);
        dicelist.add(0);
        dicelist.add(0);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (!window.getWindow()[i][j].getIsEmpty()) {
                    switch (window.getWindow()[i][j].getDice().getValue()) {
                        case 1:
                            dicelist.set(0, dicelist.get(0) + 1);
                            break;
                        case 2:
                            dicelist.set(1, dicelist.get(1) + 1);
                            break;
                        default:    //casella vuota (case 3,4,5,6)
                            break;
                    }
                }
            }
        }
        if (dicelist.get(0) < dicelist.get(1)) {
            return getScore() * dicelist.get(0);
        }
        else
            return getScore() * dicelist.get(1);
    }

}
