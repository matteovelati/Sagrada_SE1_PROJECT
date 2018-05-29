package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

import java.util.ArrayList;


public class PORowShadeVariety extends PublicObjective {

    private ArrayList<Integer> valuesBag = new ArrayList<>(6);

    public PORowShadeVariety(){
        super.setScore(5);
        super.setIdNumber(6);
        super.setName("Row Shade Variety");
        super.setDescription("Rows with no repeated VALUES");
    }

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
