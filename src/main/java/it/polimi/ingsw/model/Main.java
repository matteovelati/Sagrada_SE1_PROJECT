package it.polimi.ingsw.model;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args){

        ArrayList<Player> players = new ArrayList<Player>();
        Player player1, player2, player3;
        player1 = new Player("Zanga");
        player2 = new Player("GabriShibbo");
        player3 = new Player("Velatinho");
        players.add(player1);
        players.add(player2);
        players.add(player3);
        Game game = new Game(players);
        /*int actPlayer = 0;
        Bag bag = Bag.getInstance();

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
        Round round = new Round();



        Draft draft = Draft.getInstance();

        round.manager(players, actPlayer, field, bag);*/



        //turn1.playerTurn(player, field);
        game.play();



    }
}
