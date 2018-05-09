package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;


public class TCLathekin extends Card implements ToolCard   {

    private boolean isUsed;
    private Dice dicetmp;

    public TCLathekin(int idNumber){
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(4);
        super.setColor(Colors.Y);
        super.setName("Lathekin");
        super.setDescription("Move exactly TWO dice, obeying all placement restrictions.\n");
    }

    @Override
    public int getNumber(){
        return super.getIdNumber();
    }

    @Override
    public boolean getIsUsed() {
        return isUsed;
    }

    @Override
    public void setIsUsed(boolean isUsed){
        this.isUsed = isUsed;
    }

    @Override
    public boolean useToolCard() {
        return false;
    }


    private void moveDice1(Window window, int i, int j, int x, int y){ //i,j dado da muovere - x,y nuova casella
        dicetmp = window.getWindow()[i][j].getDice();
        if (window.verifyAllRestrictions(dicetmp, x, y)){
            window.getWindow()[x][y].setDice(dicetmp);
            window.getWindow()[i][j].setDice(null);
        }
        //else richiama la scelta
    }

    private void moveDice2(Window window, int i, int j, int x, int y){ //i,j dado da muovere - x,y nuova casella
        dicetmp = window.getWindow()[i][j].getDice();
        if (window.verifyAllRestrictions(dicetmp, x, y)){
            window.getWindow()[x][y].setDice(dicetmp);
            window.getWindow()[i][j].setDice(null);
        }
        //else richiama la scelta
    }

}
