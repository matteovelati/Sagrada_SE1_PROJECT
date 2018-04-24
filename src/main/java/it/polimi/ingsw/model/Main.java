package it.polimi.ingsw.model;
import java.util.Random;

public class Main {

    public static void main(String[] args){
        Player player = new Player("zanga");
        Window window1 = new Window("Bellesguard");
        Window window2 = new Window("Industria");
        Window window3 = new Window("Symphony of Light");
        Window window4 = new Window("Water of Life");
        SchemeCard schemeCard1 = new SchemeCard(window1, window2);
        SchemeCard schemeCard2 = new SchemeCard(window3, window4);
        Random r = new Random();
        ToolCard toolCards[] = new ToolCard[3];
        toolCards[0] = new ToolCard(r.nextInt(12)+1);
        toolCards[1] = new ToolCard(r.nextInt(12)+1);
        toolCards[2] = new ToolCard(r.nextInt(12)+1);
        // può uscire 2 volte la stessa carta
        PublicObjective[] publicObjective = new PublicObjective[3];
        publicObjective[0] = new PublicObjective(r.nextInt(10)+1);
        publicObjective[1] = new PublicObjective(r.nextInt(10)+1);
        publicObjective[2] = new PublicObjective(r.nextInt(10)+1);

        Field field = Field.getInstance(toolCards, publicObjective);


        Turn turn1 = new Turn();

        Dice dice1 = new Dice(Colors.R);
        Dice dice2 = new Dice(Colors.G);
        Dice dice3 = new Dice(Colors.B);
        Dice dice4 = new Dice(Colors.B);
        Dice dice5 = new Dice(Colors.B);
        Draft draft = Draft.getInstance();

        draft.setDraft(dice1);
        draft.setDraft(dice2);
        draft.setDraft(dice3);
        draft.setDraft(dice4);
        draft.setDraft(dice5);



        turn1.PlayerTurn(player, field);


    }
}
