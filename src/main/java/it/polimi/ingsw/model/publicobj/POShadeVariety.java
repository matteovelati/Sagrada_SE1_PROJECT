package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

import java.util.ArrayList;


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
    public int calculateScore(Window window){
        ArrayList<Integer> dicelist  = new ArrayList<>(6);
        dicelist.add(0);
        dicelist.add(0);
        dicelist.add(0);
        dicelist.add(0);
        dicelist.add(0);
        dicelist.add(0);


        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                if (!window.getWindow()[i][j].getIsEmpty()) {
                    switch (window.getWindow()[i][j].getDice().getValue()) {
                        case 1:
                            dicelist.set(0, dicelist.get(0) + 1);
                            break;
                        case 2:
                            dicelist.set(1, dicelist.get(1) + 1);
                            break;
                        case 3:
                            dicelist.set(2, dicelist.get(2) + 1);
                            break;
                        case 4:
                            dicelist.set(3, dicelist.get(3) + 1);
                            break;
                        case 5:
                            dicelist.set(4, dicelist.get(4) + 1);
                            break;
                        case 6:
                            dicelist.set(5, dicelist.get(5) + 1);
                            break;
                        default:    //casella vuota (case 0)
                            break;
                    }
                }
            }
        }
        int minsets = dicelist.get(0);
        for(int i = 0; i < dicelist.size(); i++) {
            if(dicelist.get(i) < minsets) minsets = dicelist.get(i);
        }
        return score*minsets;
    }

}
