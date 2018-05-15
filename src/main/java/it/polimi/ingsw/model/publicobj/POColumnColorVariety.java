package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

import java.util.ArrayList;


public class POColumnColorVariety extends Card implements PublicObjective {

    private int score;
    private ArrayList<Colors> colorsBag = new ArrayList<>(5);

    public POColumnColorVariety(){
        this.score = 5;
        super.setIdNumber(9);
        super.setName("Column Color Variety");
        super.setDescription("Column with no repeated COLORS");
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
        int diffcolors = 0;

        for(int j = 0; j < 5; j++) {
            setColorsBag();
            diffcolors = 0;
            for(int i = 0; i < 4; i++) {
                if (!window.getWindow()[i][j].getIsEmpty()) {
                    if (!colorsBag.remove(window.getWindow()[i][j].getDice().getColor())) {
                        setColorsBag();
                        diffcolors = 0;
                        break;
                    } else diffcolors++;
                }
            }
            if(diffcolors == 4) diffcolumns ++;
        }

        return score*diffcolumns;
    }

    private void setColorsBag() {
        colorsBag.clear();
        colorsBag.add(Colors.B);
        colorsBag.add(Colors.G);
        colorsBag.add(Colors.P);
        colorsBag.add(Colors.R);
        colorsBag.add(Colors.Y);
    }

}
