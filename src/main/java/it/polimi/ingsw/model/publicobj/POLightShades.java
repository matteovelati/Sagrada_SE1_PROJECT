package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

import java.util.ArrayList;


public class POLightShades extends Card implements PublicObjective {

    private int score;

    public POLightShades(int idNumber){
        super(idNumber);
        this.score = 2;
        super.setIdNumber(1);
        super.setName("Light Shades");
        super.setDescription("Sets of 1 & 2 anywhere");
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
        dicelist.add(0);
        dicelist.add(0);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (!window.getWindow()[i][j].getIsEmpty()) {
                    switch (window.getWindow()[i][j].getDice().getValue()) {
                        case 1:
                            dicelist.set(0, dicelist.get(0) + 1);
                            break;
                        case 2:
                            dicelist.set(1, dicelist.get(1) + 1);
                            break;
                        default:    //casella vuota (case 3,4,5,6)
                            break;
                    }
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
