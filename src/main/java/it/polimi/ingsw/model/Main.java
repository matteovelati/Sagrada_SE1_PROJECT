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

        player.chooseWindow(schemeCard1, schemeCard2);

        System.out.println(player.getWindow().getName());

        player.selectToolCard(toolCards);

        Dice dice = new Dice(Colors.R);
        Draft draft = Draft.getInstance();


        draft.setDraft(dice);
        draft.setDraft(dice);
        draft.setDraft(dice);

        player.pickDice(draft);
        player.putDice();
        player.getWindow().print();

        player.pickDice(draft);
        player.putDice();
        player.getWindow().print();


        System.out.println(player.getDice().getColor());

        player.pickDice(draft);

        System.out.println(player.getDice().getValue());

        window1.print();

    }
}
