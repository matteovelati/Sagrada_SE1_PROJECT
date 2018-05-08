package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.PublicObjective;


public class PORowColorVariety extends Card implements PublicObjective {

    private int score;

    public PORowColorVariety(int idNumber){
        super(idNumber);
        this.score = 6;
        super.setIdNumber(8);
        super.setName("Row Color Variety");
        super.setDescription("Rows with no repeated COLORS");
    }

    public int getScore() {
        return score;
    }

    @Override
    public int calculateScore(){
        return score*4;
    }

}
