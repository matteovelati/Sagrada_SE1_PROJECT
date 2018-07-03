import it.polimi.ingsw.view.cli.ViewCLI;
import it.polimi.ingsw.view.cli.ViewCLISinglePlayer;
import it.polimi.ingsw.view.gui.ViewGUI;
import javafx.application.Application;

import java.io.IOException;
import java.util.Scanner;

public class Client {

    private static int UI;
    private static int match;
    private static boolean check = false;

    public static void main(String[] args) throws IOException {

        //System.setProperty("java.security.policy", "stupid.policy");
        //System.setSecurityManager(new SecurityManager());

        System.out.println("Choose your UI:\n1) CLI\n2) GUI");
        UI = askInput();

        if (UI == 1) {
            System.out.println("Select the kind of match you wanna play:\n1) SINGLEPLAYER\n2) MULTIPLAYER");
            match = askInput();
            if (match == 1)
                new ViewCLISinglePlayer();
            else if (match == 2) {
                new ViewCLI();
            }
        }
        else if (UI == 2) {
            Application.launch(ViewGUI.class);
        }

    }

    private static int askInput () {
        Scanner input;
        int tmp;
        while(!check) {
            input = new Scanner(System.in);
            while (!input.hasNextInt()) {
                System.out.println("Please insert a number.");
                input = new Scanner(System.in);
            }
            tmp = input.nextInt();
            if (tmp == 1 || tmp == 2) {
                return tmp;
            }
            else{
                System.out.println("Please select 1 or 2");
                check = false;
            }
        }
        return 0;
    }
}
