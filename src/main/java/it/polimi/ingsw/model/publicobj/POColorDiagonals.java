package it.polimi.ingsw.model.publicobj;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.PublicObjective;
import it.polimi.ingsw.model.Window;


public class POColorDiagonals extends Card implements PublicObjective {

    private int score;

    public POColorDiagonals(int idNumber){
        super(idNumber);
        super.setIdNumber(10);
        super.setName("Color Diagonals");
        super.setDescription("Count of diagonally adjacent same-color dice");
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
    public void setScore(int score) {       //va calcolato alla fine!!!
        this.score = score;
    }

    @Override
    public int calculateScore(Window window){
        Colors color;
        for(int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                if (window.getWindow()[i][j].getIsEmpty()){
                    break;
                }
                color = window.getWindow()[i][j].getDice().getColor();
                score = score + verifyColor(window, i, j, color);
            }
        }
        return score;
    }

    private int verifyColor(Window window, int i, int j, Colors color){
        int tmp = 1;
        if (i == 0) {
            if (j == 0) {
                if (window.getWindow()[i+1][j+1].getDice().getColor().equals(color) && !window.getWindow()[i+1][j+1].getIsEmpty()) {
                    window.getWindow()[i+1][j+1].setIsEmpty(true);
                    return (tmp+(verifyColor(window, i+1, j+1, color)));
                } else return 0;
            }
            else {
                if (j == 4) {
                    if (window.getWindow()[i+1][j-1].getDice().getColor().equals(color) && !window.getWindow()[i+1][j-1].getIsEmpty()) {
                        window.getWindow()[i+1][j-1].setIsEmpty(true);
                        return (tmp + (verifyColor(window, i+1, j-1, color)));
                    } else return 0;
                } else {
                    if (window.getWindow()[i+1][j+1].getDice().getColor().equals(color) && !window.getWindow()[i+1][j+1].getIsEmpty()) {
                        window.getWindow()[i+1][j+1].setIsEmpty(true);
                        return (tmp + (verifyColor(window, i+1, j+1, color)));
                    } else if (window.getWindow()[i+1][j-1].getDice().getColor().equals(color) && !window.getWindow()[i+1][j-1].getIsEmpty()) {
                        window.getWindow()[i+1][j-1].setIsEmpty(true);
                        return (tmp + (verifyColor(window, i+1, j-1, color)));
                    } else return 0;
                }
            }
        }
        else {
            if (i == 3){
                if (j == 0){
                    if (window.getWindow()[i-1][j+1].getDice().getColor().equals(color) && !window.getWindow()[i-1][j+1].getIsEmpty()) {
                        window.getWindow()[i-1][j+1].setIsEmpty(true);
                        return (tmp+(verifyColor(window, i-1, j+1, color)));
                    } else return 0;
                }
                else {
                    if (j == 4){
                        if (window.getWindow()[i-1][j-1].getDice().getColor().equals(color) && !window.getWindow()[i-1][j-1].getIsEmpty()) {
                            window.getWindow()[i-1][j-1].setIsEmpty(true);
                            return (tmp+(verifyColor(window, i-1, j-1, color)));
                        } else return 0;
                    }
                    else {
                        if (window.getWindow()[i-1][j-1].getDice().getColor().equals(color) && !window.getWindow()[i-1][j-1].getIsEmpty()) {
                            window.getWindow()[i-1][j-1].setIsEmpty(true);
                            return (tmp + (verifyColor(window, i-1, j-1, color)));
                        } else if (window.getWindow()[i-1][j+1].getDice().getColor().equals(color) && !window.getWindow()[i-1][j+1].getIsEmpty()) {
                            window.getWindow()[i-1][j+1].setIsEmpty(true);
                            return (tmp + (verifyColor(window, i-1, j+1, color)));
                        } else return 0;
                    }
                }
            }
            else {
                if (j == 0){
                    if (window.getWindow()[i+1][j+1].getDice().getColor().equals(color) && !window.getWindow()[i+1][j+1].getIsEmpty()) {
                        window.getWindow()[i+1][j+1].setIsEmpty(true);
                        return (tmp + (verifyColor(window, i+1, j+1, color)));
                    } else if (window.getWindow()[i-1][j+1].getDice().getColor().equals(color) && !window.getWindow()[i-1][j+1].getIsEmpty()) {
                        window.getWindow()[i-1][j+1].setIsEmpty(true);
                        return (tmp + (verifyColor(window, i-1, j+1, color)));
                    } else return 0;
                }
                else {
                    if (j == 4){
                        if (window.getWindow()[i+1][j-1].getDice().getColor().equals(color) && !window.getWindow()[i+1][j-1].getIsEmpty()) {
                            window.getWindow()[i+1][j-1].setIsEmpty(true);
                            return (tmp + (verifyColor(window, i+1, j-1, color)));
                        } else if (window.getWindow()[i-1][j-1].getDice().getColor().equals(color) && !window.getWindow()[i-1][j-1].getIsEmpty()) {
                            window.getWindow()[i-1][j-1].setIsEmpty(true);
                            return (tmp + (verifyColor(window, i-1, j-1, color)));
                        } else return 0;
                    }
                    else {
                        if (window.getWindow()[i+1][j+1].getDice().getColor().equals(color) && !window.getWindow()[i+1][j+1].getIsEmpty()) {
                            window.getWindow()[i+1][j+1].setIsEmpty(true);
                            return (tmp + (verifyColor(window, i+1, j+1, color)));
                        } else if (window.getWindow()[i-1][j+1].getDice().getColor().equals(color) && !window.getWindow()[i-1][j+1].getIsEmpty()) {
                            window.getWindow()[i-1][j+1].setIsEmpty(true);
                            return (tmp + (verifyColor(window, i-1, j+1, color)));
                        }
                        else if (window.getWindow()[i-1][j-1].getDice().getColor().equals(color) && !window.getWindow()[i-1][j-1].getIsEmpty()) {
                            window.getWindow()[i-1][j-1].setIsEmpty(true);
                            return (tmp + (verifyColor(window, i - 1, j - 1, color)));
                        }
                        else if (window.getWindow()[i+1][j-1].getDice().getColor().equals(color) && !window.getWindow()[i+1][j-1].getIsEmpty()) {
                            window.getWindow()[i+1][j-1].setIsEmpty(true);
                            return (tmp + (verifyColor(window, i+1, j-1, color)));}
                        else return 0;
                    }
                }
            }
        }
    }
}
