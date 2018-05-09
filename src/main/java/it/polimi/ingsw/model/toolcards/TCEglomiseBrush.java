package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.*;


public class TCEglomiseBrush extends Card implements ToolCard   {

    private boolean isUsed;
    private Dice dicetmp;

    public TCEglomiseBrush(int idNumber){
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(2);
        super.setColor(Colors.B);
        super.setName("Eglomise Brush");
        super.setDescription("Move any ONE die in your window ignoring color restrictions.\n");
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


    private void moveDice(Window window, int i, int j, int x, int y){ //i,j dado da muovere - x,y nuova casella
        dicetmp = window.getWindow()[i][j].getDice();
        if (window.positionRestriction(dicetmp, x, y) && window.numberRestriction(dicetmp, x, y)){
            window.getWindow()[x][y].setDice(dicetmp);
            window.getWindow()[i][j].setDice(null);
        }
        //else richiama la scelta
    }



}
