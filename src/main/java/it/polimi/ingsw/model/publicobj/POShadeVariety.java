package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.PublicObjective;


public class POShadeVariety extends Card implements PublicObjective {

    private int score;

    public POShadeVariety(int idNumber){
        super(idNumber);
        this.score = 5;
        super.setIdNumber(4);
        super.setName("Shade Variety");
        super.setDescription("Sets of one of each VALUE anywhere");
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
