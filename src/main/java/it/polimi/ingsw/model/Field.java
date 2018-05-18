package it.polimi.ingsw.model;

import it.polimi.ingsw.model.publicobj.*;
import it.polimi.ingsw.model.toolcards.*;

import javax.tools.Tool;
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
        ArrayList<PublicObjective> allPublicObjecetives = new ArrayList<>(10);
        allPublicObjecetives.add(new POColorDiagonals());
        allPublicObjecetives.add(new POColorVariety());
        allPublicObjecetives.add(new POColumnColorVariety());
        allPublicObjecetives.add(new POColumnShadeVariety());
        allPublicObjecetives.add(new PODarkShades());
        allPublicObjecetives.add(new POLightShades());
        allPublicObjecetives.add(new POMediumShades());
        allPublicObjecetives.add(new PORowColorVariety());
        allPublicObjecetives.add(new PORowShadeVariety());
        allPublicObjecetives.add(new POShadeVariety());
        for (int i = 0; i < 3; i++){
            publicObjectives.add(allPublicObjecetives.remove(r.nextInt(allPublicObjecetives.size())));
        }
    }
}
