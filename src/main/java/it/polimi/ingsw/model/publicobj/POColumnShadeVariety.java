package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

import java.util.ArrayList;


public class POColumnShadeVariety extends Card implements PublicObjective {

    private int score;
    private ArrayList<Integer> valuesBag = new ArrayList<>(6);

    public POColumnShadeVariety(){
        this.score = 4;
        super.setIdNumber(7);
        super.setName("Column Shade Variety");
        super.setDescription("Columns with no repeated VALUES");
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
        int diffcolumns = 0;
        int diffvalues = 0;

        for(int j = 0; j < 5; j++) {
            setValuesBag();
            diffvalues = 0;
            for(int i = 0; i < 4; i++) {
                if (!window.getWindow()[i][j].getIsEmpty()){
                    if (!valuesBag.remove((Integer) window.getWindow()[i][j].getDice().getValue())) {
                        setValuesBag();
                        diffvalues = 0;
                        break;
                    } else diffvalues++;
                }
            }
            if(diffvalues == 4) diffcolumns ++;
        }

        return score*diffcolumns;
    }

    private void setValuesBag() {
        valuesBag.clear();
        valuesBag.add(1);
        valuesBag.add(2);
        valuesBag.add(3);
        valuesBag.add(4);
        valuesBag.add(5);
        valuesBag.add(6);
    }

}