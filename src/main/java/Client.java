import it.polimi.ingsw.controller.RemoteGameController;
import it.polimi.ingsw.view.cli.ViewCLI;
import it.polimi.ingsw.view.cli.ViewCLISinglePlayer;
import it.polimi.ingsw.view.gui.ViewGUI;
import javafx.application.Application;

import javax.naming.NamingException;
import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    private static int UI, match, connection;
    private static boolean check = false;

    private static boolean socketConnection;
    private static Socket socket;

    public static void main(String[] args) throws NamingException, IOException, NotBoundException, ClassNotFoundException {

        /*System.setProperty("java.security.policy", "stupid.policy");
        System.setSecurityManager(new SecurityManager());*/

        Registry registry = LocateRegistry.getRegistry(args[0]);

        String remoteObj = "network";

        RemoteGameController centralNetwork = (RemoteGameController) registry.lookup(remoteObj);

        System.out.println("Choose your connection:\n1) RMI\n2) SOCKET");
        connection = askInput();
        if(connection == 1)
            socketConnection = false;
        else
            socketConnection = true;

        System.out.println("Choose your UI:\n1) CLI\n2) GUI");
        UI = askInput();
        System.out.println("Select the kind of match you wanna play:\n1) SINGLEPLAYER\n2) MULTIPLAYER");
        match = askInput();
        /*System.out.println("1) RMI\n2) SOCKET");
        connection = askInput();*/

        if (UI == 1) {
            if (match == 1)
                new ViewCLISinglePlayer(centralNetwork);
            else if (match == 2) {
                if(socketConnection){
                    socket = new Socket(args[0], 1337);
                    new ViewCLI(centralNetwork, true, socket);
                }
                new ViewCLI(centralNetwork, false, null);
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
