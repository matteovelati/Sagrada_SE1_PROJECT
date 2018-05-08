package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.PublicObjective;


public class POLightShades extends Card implements PublicObjective {

    private int score;

    public POLightShades(int idNumber){
        super(idNumber);
        this.score = 2;
        super.setIdNumber(1);
        super.setName("Light Shades");
        super.setDescription("Sets of 1 & 2 anywhere");
    }

    public int getScore() {
        return score;
    }

    @Override
    public int calculateScore(){
        return score*4;
    }

}
