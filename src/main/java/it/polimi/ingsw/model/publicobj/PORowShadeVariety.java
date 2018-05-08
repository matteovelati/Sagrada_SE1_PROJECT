package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.PublicObjective;


public class PORowShadeVariety extends Card implements PublicObjective {

    private int score;

    public PORowShadeVariety(int idNumber){
        super(idNumber);
        this.score = 5;
        super.setIdNumber(6);
        super.setName("Row Shade Variety");
        super.setDescription("Rows with no repeated VALUES");
    }

    public int getScore() {
        return score;
    }

    @Override
    public int calculateScore(){
        return score*4;
    }

}
