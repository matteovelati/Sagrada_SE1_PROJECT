package it.polimi.ingsw.model;

public class Field {

    private static Field instance = null;

    private RoundTrack roundTrack;
    private Draft draft;
    private ToolCard[] toolCard; //sono 3 carte
    private PublicObjective[] publicObjectives; //sono 3 carte

    private Field(ToolCard[] toolCard, PublicObjective[] publicObjectives){
        this.roundTrack = RoundTrack.getInstance();
        this.draft = Draft.getInstance();
        this.toolCard = toolCard;
        this.publicObjectives = publicObjectives;
    }

    public static Field getInstance(ToolCard[] toolCard, PublicObjective[] publicObjectives){
        if (instance == null)
            instance = new Field(toolCard, publicObjectives);
        return instance;
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
