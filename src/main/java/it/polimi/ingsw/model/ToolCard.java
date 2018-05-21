package it.polimi.ingsw.model;

import java.util.ArrayList;

public interface ToolCard{

    boolean useToolCard(GameModel gameModel, ArrayList<Integer> input);
    boolean getIsUsed();
    void setIsUsed(boolean isUsed);
    int getNumber();
    String getTitle();
    String getDescr();
    Colors getColor();
    int getCalls();

}
