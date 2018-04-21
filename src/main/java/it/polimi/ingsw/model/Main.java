package it.polimi.ingsw.model;

public class Main {

    public static void main(String[] args){
        Player player = new Player("zanga");
        /*Window window1 = new Window("Bellesguard");
        Window window2 = new Window("Industria");
        Window window3 = new Window("Symphony of Light");
        Window window4 = new Window("Water of Life");
        SchemeCard schemeCard1 = new SchemeCard(window1, window2);
        SchemeCard schemeCard2 = new SchemeCard(window3, window4);

        player.chooseWindow(schemeCard1, schemeCard2);

        System.out.println(player.getWindow().getName());*/

        Dice dice = new Dice(Colors.RED);
        Draft draft = new Draft();

        draft.setDraft(dice);
        draft.setDraft(dice);
        draft.setDraft(dice);

        player.pickDice(draft);

        System.out.println(player.getDice().getColor());

        player.pickDice(draft);

        System.out.println(player.getDice().getValue());

    }
}
