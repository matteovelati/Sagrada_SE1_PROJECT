package it.polimi.ingsw.model;

public class Field {

    private RoundTrack roundTrack;
    private Draft draft;
    private ToolCard[] toolCard; //sono 3 carte
    private PublicObjective[] publicObjectives; //sono 3 carte

    public Field(RoundTrack roundTrack, Draft draft, ToolCard[] toolCard, PublicObjective[] publicObjectives){
        this.roundTrack = roundTrack;
        this.draft = draft;
        this.toolCard = toolCard;
        this.publicObjectives = publicObjectives;
    }

    public RoundTrack getRoundTrack() {
        return roundTrack;
    }

    public Draft getDraft() {
        return draft;
    }

    public ToolCard[] getToolCard() {
        return toolCard;
    }

    public PublicObjective[] getPublicObjectives() {
        return publicObjectives;
    }
}
