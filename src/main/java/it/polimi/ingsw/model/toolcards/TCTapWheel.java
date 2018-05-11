package it.polimi.ingsw.model.toolcards;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Colors;
import it.polimi.ingsw.model.Dice;
import it.polimi.ingsw.model.RoundTrack;
import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.model.Window;


public class  TCTapWheel extends Card implements ToolCard   {

    private boolean isUsed;
    private Dice dicetmp;

    public TCTapWheel(int idNumber){
        super(idNumber);
        this.isUsed = false;
        super.setIdNumber(12);
        super.setColor(Colors.B);
        super.setName("Tap Wheel");
        super.setDescription("Move up to TWO dice of the same color that match the color of a die on the Round Track.\nYou must obey all placement restrictions.\n");
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

    private void moveDice1(Window window, int i, int j, int x, int y, RoundTrack grid, int k){ //i,j dado da muovere - x,y nuova casella - k dado roundtrack
        dicetmp = window.getWindow()[i][j].getDice();
        if (window.verifyAllRestrictions(dicetmp, x, y) && dicetmp.getColor().equals(grid.getGrid().get(k).getColor())){
            window.getWindow()[x][y].setDice(dicetmp);
            window.getWindow()[i][j].setDice(null);
        }
        //else richiama la scelta
    }

    private void moveDice2(Window window, int i, int j, int x, int y, RoundTrack grid, int k){ //i,j dado da muovere - x,y nuova casella - k dado roundtrack
        dicetmp = window.getWindow()[i][j].getDice();
        if (window.verifyAllRestrictions(dicetmp, x, y) && dicetmp.getColor().equals(grid.getGrid().get(k).getColor())){
            window.getWindow()[x][y].setDice(dicetmp);
            window.getWindow()[i][j].setDice(null);
        }
        //else richiama la scelta
    }
}
