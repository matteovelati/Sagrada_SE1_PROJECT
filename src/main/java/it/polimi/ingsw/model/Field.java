package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Field {

    private static Field instance = null;

    private RoundTrack roundTrack;
    private Draft draft;
    private ArrayList<ToolCard> toolCards; //sono 3 carte
    private ArrayList<PublicObjective> publicObjectives; //sono 3 carte

    private Field(){
        this.roundTrack = RoundTrack.getInstance();
        this.draft = Draft.getInstance();
        this.toolCards = new ArrayList<ToolCard>(3);
        this.publicObjectives = new ArrayList<PublicObjective>(3);
    }

    public static Field getInstance(ToolCard[] toolCard, PublicObjective[] publicObjectives){
        if (instance == null)
            instance = new Field();
        return instance;
    }

    public RoundTrack getRoundTrack() {
        return roundTrack;
    }

    public Draft getDraft() {
        return draft;
    }

    public ArrayList<ToolCard> getToolCards() {
        return toolCards;
    }

    public ArrayList<PublicObjective> getPublicObjectives() {
        return publicObjectives;
    }

    public void setToolCards(ArrayList<ToolCard> toolCards){

        if(toolCards.size() == 0)
            this.toolCards = toolCards;

    }

    public void setPublicObjectives(ArrayList<PublicObjective> publicObjectives){

        if(publicObjectives.size() == 0)
            this.publicObjectives = publicObjectives;

    }
}
