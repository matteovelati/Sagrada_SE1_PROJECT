package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Draft;
import it.polimi.ingsw.model.RoundTrack;

public class EndRound {

    public static void refreshDraft(Draft draft, RoundTrack roundTrack){

        //DA GESTIRE IL CASO IN CUI I DADI RIMASTI NELLA DRAFT SIANO PIù DI UNO

         roundTrack.setGrid(draft.getDraft().get(0));

    }
}
