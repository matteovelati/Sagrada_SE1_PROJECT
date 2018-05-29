package it.polimi.ingsw.model;

public abstract class PublicObjective extends Card{

    private int score;

    public abstract int calculateScore(Window window);
    public String getTitle(){
        return super.getName();
    }
    public String getDescr(){
        return super.getDescription();
    }
    public int getScore() {
        return score;
    }
    protected void setScore(int score) {
        this.score = score;
    }

}
