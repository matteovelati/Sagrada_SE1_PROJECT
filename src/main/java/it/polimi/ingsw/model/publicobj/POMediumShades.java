package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

import java.util.ArrayList;


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
    public String getTitle(){
        return super.getName();
    }

    @Override
    public String getDescr(){
        return super.getDescription();
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {//USELESS
    }

    @Override
    public int calculateScore(Window window) {
        ArrayList<Integer> dicelist = new ArrayList<>(2);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                switch (window.getWindow()[i][j].getDice().getValue()) {
                    case 3:
                        dicelist.set(0, dicelist.get(0) + 1);
                        break;
                    case 4:
                        dicelist.set(1, dicelist.get(1) + 1);
                        break;
                    default:    //casella vuota (case 1,2,5,6)
                        break;
                }
            }
        }
        if (dicelist.get(0) < dicelist.get(1)) {
            return score * dicelist.get(0);
        }
        else
            return score * dicelist.get(1);
    }

}
