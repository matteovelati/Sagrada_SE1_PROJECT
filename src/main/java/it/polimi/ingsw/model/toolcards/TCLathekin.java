package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;


public class TCLathekin extends ToolCard {

    private int flag = 1;

    public TCLathekin(){
        super.setIdNumber(4);
        super.setColor(Colors.Y);
        super.setName("Lathekin");
        super.setDescription("Move exactly TWO dice, obeying all placement restrictions.\n");
        super.setCalls(2);
    }

    @Override
    public boolean useToolCard(GameModel gameModel, ArrayList<Integer> input) {
        //arraylist: in 0,1 le i,j del dado1; in 2,3 le i,j della new pos dado1; in 4,5 le i,j del dado2; in 6,7 le i,j della new pos dado2
        //IN 0 (-1) PER ANNULLARE
        boolean check;
        if (input.get(0) != -1) {
            if (flag == 1) {
                check = (moveDice(gameModel.getActualPlayer().getWindow(), input.get(0), input.get(1), input.get(2), input.get(3)));
                if (check){
                    flag = 2;
                    return true;
                }
                else
                    return false;
            }
            if (flag == 2) {
                flag = 1;
                check = (moveDice(gameModel.getActualPlayer().getWindow(), input.get(4), input.get(5), input.get(6), input.get(7)));
                if (check) {
                    return true;
                } else {
                    replaceDice(gameModel.getActualPlayer().getWindow(), input.get(2), input.get(3), input.get(0), input.get(1));
                    return false;
                }
            } else
                return false;
        }
        else
        if (flag == 2){
            replaceDice(gameModel.getActualPlayer().getWindow(), input.get(3), input.get(4), input.get(1), input.get(2));
        }
        flag = 1;
        return false; //questo false NON deve richiamare il metodo
    }

    @Override
    public boolean select(GameModel gameModel){
        int i=0;

        for (Space[] matrix : gameModel.getActualPlayer().getWindow().getWindow()){
            for (Space space : matrix){
                if (!space.getIsEmpty()){
                    i++;
                    if(i == 2)
                        return true;
                }
            }
        }

        return false;
    }

    private boolean moveDice(Window window, int i, int j, int x, int y){ //i,j dado da muovere - x,y nuova casella
        Dice dicetmp = window.getWindow()[i][j].getDice();
        window.getWindow()[i][j].setDice(null);
        if ((window.verifyAllRestrictions(dicetmp, x, y)) || (window.getIsEmpty() && window.verifyFirstDiceRestriction(dicetmp, x, y))){
            window.getWindow()[x][y].setDice(dicetmp);
            return true;
        }
        else {
            window.getWindow()[i][j].setDice(dicetmp);
            return false;
        }
    }

    private void replaceDice(Window window, int i, int j, int x, int y){
        Dice dicetmp = window.getWindow()[i][j].getDice();
        window.getWindow()[i][j].setDice(null);
        window.getWindow()[x][y].setDice(dicetmp);
    }

}

