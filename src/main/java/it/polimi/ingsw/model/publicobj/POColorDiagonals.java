package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;


public class POColorDiagonals extends PublicObjective {

    public POColorDiagonals(){
        super.setIdNumber(10);
        super.setName("Color Diagonals");
        super.setDescription("Count of diagonally adjacent same-color dice");
    }

    @Override
    public int calculateScore(Window window){
        Colors color;
        for(int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                if (!window.getWindow()[i][j].getIsEmpty()){
                    color = window.getWindow()[i][j].getDice().getColor();
                    setScore(getScore() + (verifyColor(window, i, j, color) ? 1 : 0));
                }
            }
        }
        return getScore();
    }

    private boolean verifyColor(Window window, int i, int j, Colors color) {
        for (int x = -1; x < 2; x += 2) {
            for (int y = -1; y < 2; y += 2) {
                try {
                    if (color.equals(window.getWindow()[i + x][j + y].getDice().getColor()))
                        return true;
                } catch (Exception e) {
                    //DO NOTHING
                }
            }
        }
        return !window.neighboursColorRestriction(window.getWindow()[i][j].getDice(), i, j);
    }
}
