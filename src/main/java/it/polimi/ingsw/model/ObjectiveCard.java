package it.polimi.ingsw.model;

public abstract class ObjectiveCard extends Card {

    private int score;

    public ObjectiveCard(String name, String description, int score){
        super(name, description);
        this.score = score;
    }

    public ObjectiveCard(String name, String description){
        super(name, description);
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

}
