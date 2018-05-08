package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.PublicObjective;


public class PODarkShades extends Card implements PublicObjective {

    private int score;

    public PODarkShades(int idNumber){
        super(idNumber);
        this.score = 2;
        super.setIdNumber(3);
        super.setName("Dark Shades");
        super.setDescription("Sets of 5 & 6 anywhere");
    }

    public int getScore() {
        return score;
    }

    @Override
    public int calculateScore(){
        return score*4;
    }

}
