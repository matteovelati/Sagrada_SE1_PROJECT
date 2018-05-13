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
        int scoretmp;
        for(int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                if (!window.getWindow()[i][j].getIsEmpty()){
                    color = window.getWindow()[i][j].getDice().getColor();
                    scoretmp = score + verifyColor(window, i, j, color);
                    if (scoretmp > score)
                        score = scoretmp + 1;
                }
            }
        }
        return score;
    }

    private int verifyColor(Window window, int i, int j, Colors color){
        int cont = 1;
        int NE = 0;
        int NW = 0;
        int SW = 0;
        int SE = 0;
        if (i == 0) {
            if (j == 0) {
                if (!window.getWindow()[i+1][j+1].getIsEmpty() && window.getWindow()[i+1][j+1].getDice().getColor().equals(color)) {
                    window.getWindow()[i][j].setIsEmpty(true);
                    return (1 + (verifyColor(window, i+1, j+1, color)));
                }
                else {
                    window.getWindow()[i][j].setIsEmpty(true);
                    return 0;
                }
            }
            else {
                if (j == 4) {
                    if (!window.getWindow()[i+1][j-1].getIsEmpty() && window.getWindow()[i+1][j-1].getDice().getColor().equals(color)) {
                        window.getWindow()[i][j].setIsEmpty(true);
                        return (1 + (verifyColor(window, i+1, j-1, color)));
                    }
                    else {
                        window.getWindow()[i][j].setIsEmpty(true);
                        return 0;
                    }
                }
                else {
                    if (!window.getWindow()[i+1][j+1].getIsEmpty() && window.getWindow()[i+1][j+1].getDice().getColor().equals(color)) {
                        window.getWindow()[i][j].setIsEmpty(true);
                        SE = verifyColor(window, i+1, j+1, color);
                        if (!window.getWindow()[i+1][j-1].getIsEmpty() && window.getWindow()[i+1][j-1].getDice().getColor().equals(color)) {
                            SW = verifyColor(window, i + 1, j - 1, color);
                            cont++;
                        }
                        return cont + SE + SW;
                    }
                    else if (!window.getWindow()[i+1][j-1].getIsEmpty() && window.getWindow()[i+1][j-1].getDice().getColor().equals(color)) {
                        window.getWindow()[i][j].setIsEmpty(true);
                        SW = verifyColor(window, i+1, j-1, color);
                        if (!window.getWindow()[i+1][j+1].getIsEmpty() && window.getWindow()[i+1][j+1].getDice().getColor().equals(color)) {
                            SE = verifyColor(window, i + 1, j + 1, color);
                            cont++;
                        }
                        return cont + SW + SE;
                    }
                    else {
                        window.getWindow()[i][j].setIsEmpty(true);
                        return 0;
                    }
                }
            }
        }
        else {
            if (i == 3){
                if (j == 0){
                    if (!window.getWindow()[i-1][j+1].getIsEmpty() && window.getWindow()[i-1][j+1].getDice().getColor().equals(color)) {
                        window.getWindow()[i][j].setIsEmpty(true);
                        return (1+(verifyColor(window, i-1, j+1, color)));
                    }
                    else {
                        window.getWindow()[i][j].setIsEmpty(true);
                        return 0;
                    }
                }
                else {
                    if (j == 4){
                        if (!window.getWindow()[i-1][j-1].getIsEmpty() && window.getWindow()[i-1][j-1].getDice().getColor().equals(color)) {
                            window.getWindow()[i][j].setIsEmpty(true);
                            return (1+(verifyColor(window, i-1, j-1, color)));
                        }
                        else {
                            window.getWindow()[i][j].setIsEmpty(true);
                            return 0;
                        }
                    }
                    else {
                        if (!window.getWindow()[i-1][j-1].getIsEmpty() && window.getWindow()[i-1][j-1].getDice().getColor().equals(color)) {
                            window.getWindow()[i][j].setIsEmpty(true);
                            NW = verifyColor(window, i-1, j-1, color);
                            if (!window.getWindow()[i-1][j+1].getIsEmpty() && window.getWindow()[i-1][j+1].getDice().getColor().equals(color)) {
                                NE = verifyColor(window, i - 1, j + 1, color);
                                cont++;
                            }
                            return cont + NW + NE;
                        }
                        else if (!window.getWindow()[i-1][j+1].getIsEmpty() && window.getWindow()[i-1][j+1].getDice().getColor().equals(color)) {
                            window.getWindow()[i][j].setIsEmpty(true);
                            NE = verifyColor(window, i-1, j+1, color);
                            if (!window.getWindow()[i-1][j-1].getIsEmpty() && window.getWindow()[i-1][j-1].getDice().getColor().equals(color)) {
                                NW = verifyColor(window, i - 1, j - 1, color);
                                cont++;
                            }
                            return cont + NE + NW;
                        }
                        else {
                            window.getWindow()[i][j].setIsEmpty(true);
                            return 0;
                        }
                    }
                }
            }
            else {
                if (j == 0){
                    if (!window.getWindow()[i+1][j+1].getIsEmpty() && window.getWindow()[i+1][j+1].getDice().getColor().equals(color)) {
                        window.getWindow()[i][j].setIsEmpty(true);
                        SE = verifyColor(window, i+1, j+1, color);
                        if (!window.getWindow()[i-1][j+1].getIsEmpty() && window.getWindow()[i-1][j+1].getDice().getColor().equals(color)) {
                            SW = verifyColor(window, i - 1, j + 1, color);
                            cont++;
                        }
                        return cont + SE + SW;
                    }
                    else if (!window.getWindow()[i-1][j+1].getIsEmpty() && window.getWindow()[i-1][j+1].getDice().getColor().equals(color)) {
                        window.getWindow()[i][j].setIsEmpty(true);
                        SW = verifyColor(window, i-1, j+1, color);
                        if (!window.getWindow()[i+1][j+1].getIsEmpty() && window.getWindow()[i+1][j+1].getDice().getColor().equals(color)) {
                            SE = verifyColor(window, i + 1, j + 1, color);
                            cont++;
                        }
                        return cont + SW + SE;
                    }
                    else {
                        window.getWindow()[i][j].setIsEmpty(true);
                        return 0;
                    }
                }
                else {
                    if (j == 4){
                        if (!window.getWindow()[i+1][j-1].getIsEmpty() && window.getWindow()[i+1][j-1].getDice().getColor().equals(color)) {
                            window.getWindow()[i][j].setIsEmpty(true);
                            return (1 + (verifyColor(window, i+1, j-1, color)));
                        } else if (!window.getWindow()[i-1][j-1].getIsEmpty() && window.getWindow()[i-1][j-1].getDice().getColor().equals(color)) {
                            window.getWindow()[i][j].setIsEmpty(true);
                            return (1 + (verifyColor(window, i-1, j-1, color)));
                        } else {
                            window.getWindow()[i][j].setIsEmpty(true);
                            return 0;
                        }
                    }
                    else {
                        if (!window.getWindow()[i+1][j+1].getIsEmpty() && window.getWindow()[i+1][j+1].getDice().getColor().equals(color)) {
                            window.getWindow()[i][j].setIsEmpty(true);
                            SE = verifyColor(window, i+1, j+1, color);
                            if (!window.getWindow()[i+1][j-1].getIsEmpty() && window.getWindow()[i+1][j-1].getDice().getColor().equals(color)){
                                SW = verifyColor(window, i+1, j-1, color);
                                cont++;
                            }
                            if (!window.getWindow()[i-1][j+1].getIsEmpty() && window.getWindow()[i-1][j+1].getDice().getColor().equals(color)){
                                NE = verifyColor(window, i-1, j+1, color);
                                cont++;
                            }
                            if (!window.getWindow()[i-1][j-1].getIsEmpty() && window.getWindow()[i-1][j-1].getDice().getColor().equals(color)) {
                                NW = verifyColor(window, i - 1, j - 1, color);
                                cont++;
                            }
                            return cont + SE + SW + NE + NW;
                        }
                        else if (!window.getWindow()[i-1][j+1].getIsEmpty() && window.getWindow()[i-1][j+1].getDice().getColor().equals(color)) {
                            window.getWindow()[i][j].setIsEmpty(true);
                            NE = verifyColor(window, i-1, j+1, color);
                            if (!window.getWindow()[i+1][j-1].getIsEmpty() && window.getWindow()[i+1][j-1].getDice().getColor().equals(color)){
                                SW = verifyColor(window, i+1, j-1, color);
                                cont++;
                            }
                            if (!window.getWindow()[i+1][j+1].getIsEmpty() && window.getWindow()[i+1][j+1].getDice().getColor().equals(color)){
                                SE = verifyColor(window, i+1, j+1, color);
                                cont++;
                            }
                            if (!window.getWindow()[i-1][j-1].getIsEmpty() && window.getWindow()[i-1][j-1].getDice().getColor().equals(color)) {
                                NW = verifyColor(window, i - 1, j - 1, color);
                                cont++;
                            }
                            return cont + NE + SW + SE + NW;
                        }
                        else if (!window.getWindow()[i-1][j-1].getIsEmpty() && window.getWindow()[i-1][j-1].getDice().getColor().equals(color)) {
                            window.getWindow()[i][j].setIsEmpty(true);
                            NW = verifyColor(window, i - 1, j - 1, color);
                            if (!window.getWindow()[i+1][j-1].getIsEmpty() && window.getWindow()[i+1][j+1].getDice().getColor().equals(color)){
                                SW = verifyColor(window, i+1, j-1, color);
                                cont++;
                            }
                            if (!window.getWindow()[i-1][j+1].getIsEmpty() && window.getWindow()[i-1][j+1].getDice().getColor().equals(color)){
                                NE = verifyColor(window, i-1, j+1, color);
                                cont++;
                            }
                            if (!window.getWindow()[i+1][j+1].getIsEmpty() && window.getWindow()[i+1][j+1].getDice().getColor().equals(color)) {
                                SE = verifyColor(window, i + 1, j + 1, color);
                                cont++;
                            }
                            return cont + NW + SW + SE + NE;
                        }
                        else if (!window.getWindow()[i+1][j-1].getIsEmpty() && window.getWindow()[i+1][j-1].getDice().getColor().equals(color)) {
                            window.getWindow()[i][j].setIsEmpty(true);
                            SW = verifyColor(window, i+1, j-1, color);
                            if (!window.getWindow()[i+1][j+1].getIsEmpty() && window.getWindow()[i+1][j+1].getDice().getColor().equals(color)){
                                SE = verifyColor(window, i+1, j+1, color);
                                cont++;
                            }
                            if (!window.getWindow()[i-1][j+1].getIsEmpty() && window.getWindow()[i-1][j+1].getDice().getColor().equals(color)){
                                NE = verifyColor(window, i-1, j+1, color);
                                cont++;
                            }
                            if (!window.getWindow()[i-1][j-1].getIsEmpty() && window.getWindow()[i-1][j-1].getDice().getColor().equals(color)) {
                                NW = verifyColor(window, i - 1, j - 1, color);
                                cont++;
                            }

                            return cont + SW + SE + NE + NW;
                        }
                        else {
                            window.getWindow()[i][j].setIsEmpty(true);
                            return 0;
                        }
                    }
                }
            }
        }
    }
}
