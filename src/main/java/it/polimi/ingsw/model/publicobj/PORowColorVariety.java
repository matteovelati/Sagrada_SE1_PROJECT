package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;

import java.util.ArrayList;


public class PORowColorVariety extends Card implements PublicObjective {

    private int score;
    private ArrayList<Colors> colorsBag = new ArrayList<>(5);

    public PORowColorVariety(int idNumber){
        super(idNumber);
        this.score = 6;
        super.setIdNumber(8);
        super.setName("Row Color Variety");
        super.setDescription("Rows with no repeated COLORS");
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
        int diffrows = 0;
        int diffcolors = 0;

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 5; j++) {
                if (!colorsBag.remove(window.getWindow()[i][j].getDice().getColor())) {
                    setColorsBag();
                    diffcolors = 0;
                    break;
                }
                else    diffcolors ++;
            }
            if(diffcolors == 5) diffrows ++;
        }

        return score*diffrows;
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
