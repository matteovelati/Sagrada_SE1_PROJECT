package it.polimi.ingsw.model;

public interface PublicObjective{

    int calculateScore(Window window);
    int getScore();
    void setScore(int score);
    String getTitle();
    String getDescr();

}
