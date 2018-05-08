package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.PublicObjective;


public class POColorVariety extends Card implements PublicObjective {

    private int score;

    public POColorVariety(int idNumber){
        super(idNumber);
        this.score = 4;
        super.setIdNumber(5);
        super.setName("Color Variety");
        super.setDescription("Sets of one of each COLOR anywhere");
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
