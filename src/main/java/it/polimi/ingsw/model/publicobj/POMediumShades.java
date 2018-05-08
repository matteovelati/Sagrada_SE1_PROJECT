package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.PublicObjective;


public class POMediumShades extends Card implements PublicObjective {

    private int score;

    public POMediumShades(int idNumber){
        super(idNumber);
        this.score = 2;
        super.setIdNumber(2);
        super.setName("Medium Shades");
        super.setDescription("Sets of 3 & 4 anywhere");
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
