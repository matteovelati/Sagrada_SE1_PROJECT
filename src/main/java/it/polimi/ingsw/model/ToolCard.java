package it.polimi.ingsw.model;

public class ToolCard extends Card {

    private int idNumber;
    private boolean isUsed;
    private Colors color;

    public ToolCard(String name, String description, int idNumber, Colors color){
        super(name, description);
        this.idNumber = idNumber;
        this.isUsed = false;
        this.color = color;
    }

    public void setIsUsed(boolean isUsed){
        this.isUsed = isUsed;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public Colors getColor() {
        return color;
    }
}
