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
    private ArrayList<ToolCard> toolCards;
    private ArrayList<PublicObjective> publicObjectives;

    /**
     * creates a Field object which contains instance of Roundtrack and Draft
     * initializes and sets an arraylist of ToolCard objects and of PublicObjective objects
     */
    private Field(int level){
        this.roundTrack = RoundTrack.getInstance();
        this.draft = Draft.getInstance();
        this.toolCards = new ArrayList<>(3);
        this.publicObjectives = new ArrayList<>(3);
        if (level == 0) {
            setToolCards(0);
            setPublicObjectives(false);
        }
        else {
            setToolCards(level);
            setPublicObjectives(true);
        }
    }

    /**
     * if the field already exists, the method returns the Field object for singleplayer match,
     * otherwise it creates a new Field.
     * @return the instance of the Field class
     */
    public static Field getInstance(int level){
        if (instance == null)
            instance = new Field(level);
        return instance;
    }

    /**
     * gets the roundtrack
     * @return a roundtrack object
     */
    public RoundTrack getRoundTrack() {
        return roundTrack;
    }

    /**
     * gets the draft
     * @return a draft object
     */
    public Draft getDraft() {
        return draft;
    }

    /**
     * gets the list of toolcards of the game, that are shared among all the players
     * @return a list which contains 3 ToolCard objects
     */
    public ArrayList<ToolCard> getToolCards() {
        return toolCards;
    }

    /**
     * gets the list of publicobjectives of the game, that are shared among all the players
     * @return a list which contains 3 PublicObjective objects
     */
    public ArrayList<PublicObjective> getPublicObjectives() {
        return publicObjectives;
    }

    /**
     * randomly extracts a die from the bag
     * randomly sets a value for the die
     * finally adds the die to the draft as the last element of the list
     */
    public void setDraft(){
        draft.setDraft(Bag.getInstance());
    }

    /**
     * creates an arraylist which contains 12 ToolCards (one each)
     * randomly extract 3 and put them in the list
     * @param level an int to know how many toolcards has to be set for single player mode
     */
    private void setToolCards(int level){
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
        if (level == 0) {
            for (int i = 0; i < 3; i++) {
                toolCards.add(allToolCards.remove(r.nextInt(allToolCards.size())));
            }
        }
        else {
            for (int i = 0; i < (6-level); i++) {
                toolCards.add(allToolCards.remove(r.nextInt(allToolCards.size())));
            }
        }
    }

    /**
     * creates an arraylist which contains 10 PublicObjectives (one each)
     * randomly extract 3 and put them in the list
     * @param SP a boolean to know if is playing a in single player mode or not
     */
    private void setPublicObjectives(boolean SP){
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
        for (int i = 0; i < ((SP)?2:3); i++){
            publicObjectives.add(allPublicObjectives.remove(r.nextInt(allPublicObjectives.size())));
        }
    }
}
