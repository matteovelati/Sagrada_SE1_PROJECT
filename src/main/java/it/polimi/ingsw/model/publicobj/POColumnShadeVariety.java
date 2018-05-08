package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.PublicObjective;


public class POColumnShadeVariety extends Card implements PublicObjective {

    private int score;

    public POColumnShadeVariety(int idNumber){
        super(idNumber);
        this.score = 4;
        super.setIdNumber(7);
        super.setName("Column Shade Variety");
        super.setDescription("Columns with no repeated VALUES");
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {//USELESS
    }

    @Override
    public int calculateScore(){
        return score*4;
    }

}
