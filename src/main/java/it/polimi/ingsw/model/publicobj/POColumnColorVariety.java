package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.PublicObjective;


public class POColumnColorVariety extends Card implements PublicObjective {

    private int score;

    public POColumnColorVariety(int idNumber){
        super(idNumber);
        this.score = 5;
        super.setIdNumber(9);
        super.setName("Column Color Variety");
        super.setDescription("Column with no repeated COLORS");
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
