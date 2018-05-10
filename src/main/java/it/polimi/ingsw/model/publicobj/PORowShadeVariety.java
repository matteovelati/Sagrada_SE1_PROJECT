package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

import java.util.ArrayList;


public class PORowShadeVariety extends Card implements PublicObjective {

    private int score;
    private ArrayList<Integer> valuesBag = new ArrayList<>(6);

    public PORowShadeVariety(int idNumber){
        super(idNumber);
        this.score = 5;
        super.setIdNumber(6);
        super.setName("Row Shade Variety");
        super.setDescription("Rows with no repeated VALUES");
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {//USELESS
    }

    @Override
    public int calculateScore(Window window){
        int diffrows = 0;
        int diffvalues = 0;

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 5; j++) {
                if (!valuesBag.remove((Integer)window.getWindow()[i][j].getDice().getValue())) {
                    setColorsBag();
                    diffvalues = 0;
                    break;
                }
                else    diffvalues ++;
            }
            if(diffvalues == 5) diffrows ++;
        }

        return score*diffrows;
    }

    private void setColorsBag() {
        valuesBag.clear();
        valuesBag.add(1);
        valuesBag.add(2);
        valuesBag.add(3);
        valuesBag.add(4);
        valuesBag.add(5);
        valuesBag.add(6);
    }

}
