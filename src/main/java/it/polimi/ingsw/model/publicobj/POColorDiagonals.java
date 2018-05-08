package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.PublicObjective;


public class POColorDiagonals extends Card implements PublicObjective {

    private int score;

    public POColorDiagonals(int idNumber){
        super(idNumber);
        this.score = 100;
        super.setIdNumber(10);
        super.setName("Color Diagonals");
        super.setDescription("Count of diagonally adjacent same-color dice");
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {       //va calcolato alla fine!!!
        this.score = score;
    }

    @Override
    public int calculateScore(){
        return score*4;
    }

}
