package it.polimi.ingsw.model;

public interface ToolCard{

    boolean useToolCard();
    boolean getIsUsed();
    void setIsUsed(boolean isUsed);
    int getNumber();
    String getTitle();
    String getDescr();

}
