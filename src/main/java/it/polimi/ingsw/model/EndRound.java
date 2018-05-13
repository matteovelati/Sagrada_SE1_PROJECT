package it.polimi.ingsw.model;

public class EndRound {

    public static void refreshDraft(Draft draft, RoundTrack roundTrack){

        //DA GESTIRE IL CASO IN CUI I DADI RIMASTI NELLA DRAFT SIANO PIù DI UNO

         roundTrack.setGrid(draft.getDraft().get(0));

    }
}
