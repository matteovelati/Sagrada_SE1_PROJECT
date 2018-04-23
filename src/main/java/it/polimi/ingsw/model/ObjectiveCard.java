package it.polimi.ingsw.model;

public abstract class ObjectiveCard extends Card {

    private int score;

    public ObjectiveCard(Colors color){
        super(color);
    }

    public ObjectiveCard(int idNumber){
        super(idNumber);
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

}
