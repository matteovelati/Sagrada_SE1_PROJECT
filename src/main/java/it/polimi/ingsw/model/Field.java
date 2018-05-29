package it.polimi.ingsw.model;

import it.polimi.ingsw.model.publicobj.*;
import it.polimi.ingsw.model.toolcards.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Field implements Serializable {

    private static Field instance = null;

    private RoundTrack roundTrack;
    private Draft draft;
    private ArrayList<ToolCard> toolCards; //sono 3 carte
    private ArrayList<PublicObjective> publicObjectives; //sono 3 carte

    private Field(){
        this.roundTrack = RoundTrack.getInstance();
        this.draft = Draft.getInstance();
        this.toolCards = new ArrayList<>(3);
        setToolCards();
        this.publicObjectives = new ArrayList<>(3);
        setPublicObjectives();
    }

    public static Field getInstance(){
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

    public void setDraft(){
        draft.setDraft(Bag.getInstance());
    }

    private void setToolCards(){
        Random r = new Random();
        ArrayList<ToolCard> allToolCards = new ArrayList<>(12);
        allToolCards.add(new TCCopperFoilBurnisher());
        allToolCards.add(new TCCorkbackedStraightedge());
        allToolCards.add(new TCEglomiseBrush());
        allToolCards.add(new TCFluxBrush());
        allToolCards.add(new TCFluxRemover());
        allToolCards.add(new TCGlazingHammer());
        allToolCards.add(new TCGrindingStone());
        allToolCards.add(new TCGrozingPliers());
        allToolCards.add(new TCLathekin());
        allToolCards.add(new TCLensCutter());
        allToolCards.add(new TCRunningPliers());
        allToolCards.add(new TCTapWheel());
        for (int i = 0; i < 3; i++){
            toolCards.add(allToolCards.remove(r.nextInt(allToolCards.size())));
        }
    }

    private void setPublicObjectives(){
        Random r = new Random();
        ArrayList<PublicObjective> allPublicObjectives = new ArrayList<>(10);
        allPublicObjectives.add(new POColorDiagonals());
        allPublicObjectives.add(new POColorVariety());
        allPublicObjectives.add(new POColumnColorVariety());
        allPublicObjectives.add(new POColumnShadeVariety());
        allPublicObjectives.add(new PODarkShades());
        allPublicObjectives.add(new POLightShades());
        allPublicObjectives.add(new POMediumShades());
        allPublicObjectives.add(new PORowColorVariety());
        allPublicObjectives.add(new PORowShadeVariety());
        allPublicObjectives.add(new POShadeVariety());
        for (int i = 0; i < 3; i++){
            publicObjectives.add(allPublicObjectives.remove(r.nextInt(allPublicObjectives.size())));
        }
    }
}
